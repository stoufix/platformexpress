import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Question } from '@models/question';
import { GenericService } from '@services/generic.service';
import Swal from 'sweetalert2';
import { PageClient } from '@models/page-client';
import { AuthenticationService } from '@services/authentication.service';
import { isNullOrUndefined } from 'util';


@Component({
  selector: 'app-question-list',
  templateUrl: './question-list.component.html',
  styleUrls: ['./question-list.component.css']
})
export class QuestionListComponent implements OnInit {

  // Event to send question object to detail modal
  @Output() redirectToDetailsModal: EventEmitter<any> = new EventEmitter();

  // Event to send question object to edit modal
  @Output() redirectToEditModal: EventEmitter<any> = new EventEmitter();

  // Event for table data
  @Output() reloadEvent = new EventEmitter();

  // List of questions
  listQuestionsWithImage: any = [];
  listQuestions: any = [];

  // Question to edit
  editQuestion: Question = new Question();

  // Question to details
  detailQuestion: Question = new Question();

  // List of all elements for advanced search
  listTechnologies: any = [];
  listDegrees: any = [];
  listActivities: any = [];
  listUsernames: any = [];
  show: boolean;
  // Advanced search Dropdown lists
  dropdownListTechnologies = [];
  dropdownListDegrees = [];
  dropdownListActivities = [];
  dropdownListUsernames = [];
  dropdownSettingsDegrees = {};
  dropdownSettingsTechnologies = {};
  dropdownSettingsActivities = {};
  dropdownSettingsUsernames = {};

  // For advanced search
  selectedTechnologies: any = [];
  selectedDegrees: any = [];
  selectedNames: any = [];
  selectedActivities: any = [];
  selectedItems = [];

  // Pagination params
  pageClient: PageClient = new PageClient();
  total: number;
  selectedPage = 0;
  item = 5;

  // Simple search term
  searchInput: String = '';

  // List of privileges by user
  ListOfPrivilegesByUser: any = [];

  // Authenticated userName
  userName: string;


  constructor(private genericService: GenericService, private authenticationService: AuthenticationService) { }

  ngOnInit() {
    this.reloadData();
    // Advanced search dropdown initialisation
    this.selectedItems = [];
    this.dropdownSettingsTechnologies = {
      singleSelection: false,
      idField: 'id',
      textField: 'title',
      selectAllText: 'Selectionner tout',
      unSelectAllText: 'Déselectionner tout',
      itemsShowLimit: 5,
      allowSearchFilter: true
    };
    this.dropdownSettingsDegrees = {
      singleSelection: false,
      idField: 'id',
      textField: 'title',
      selectAllText: 'Selectionner tout',
      unSelectAllText: 'Déselectionner tout',
      itemsShowLimit: 5,
      allowSearchFilter: true
    };
    this.dropdownSettingsActivities = {
      singleSelection: false,
      idField: 'id',
      textField: 'title',
      selectAllText: 'Selectionner tout',
      unSelectAllText: 'Déselectionner tout',
      itemsShowLimit: 5,
      allowSearchFilter: true
    };
    this.dropdownSettingsUsernames = {
      singleSelection: false,
      idField: 'id',
      textField: 'UserName',
      selectAllText: 'Selectionner tout',
      unSelectAllText: 'Déselectionner tout',
      itemsShowLimit: 5,
      allowSearchFilter: true
    };
  }


  /** Reload data after every action */
  reloadData() {
    this.showListActivities();
    this.listQuestions = this.getQuestions();
    this.getTechnologies();
    this.getDegrees();
    this.getActivities();
    this.getAllUsers();
  }

  /** Gets questions without images  */
  removeImagesFromQuestoions(listWithImage: any, listWithoutImage: any) {
    listWithImage.forEach(question => {
      const description = question.description;
      const re = /<\/p>|<p>/;
      const split = description.split(re);
      split.forEach(word => {
        if (word.includes('<img src=')) {
          const removeData = question.description.replace(word, '');
          question.description = removeData;
        }
      });
      listWithoutImage.push(question);
    });
    return listWithoutImage;
  }
  /* Show activities based on user privileges*/
  showListActivities() {
    function findPrivilegeActivities(privilege: any) {
      return privilege === 'SHOW_ALL_ACTIVITIES';
    }
    this.genericService.getUserByUsername('/users/username', this.authenticationService.getUsername())
      .subscribe(user => {
        // Gets list of privileges of current user and save titles of privileges in a list
        user.value.role.privileges.forEach(element => {
          this.ListOfPrivilegesByUser.push(element.title);
        });

        const AllActivitiesAccess = this.ListOfPrivilegesByUser.find(findPrivilegeActivities);
        if (isNullOrUndefined(AllActivitiesAccess)) {
          this.show = false;
        } else {
          this.show = true;
        }
      });
  }

  /** Get Questions based on privileges and visibility criteria */
  getQuestions() {
    // Test the exist of SHOW_ALL_ACTIVITIE privilege
    function findPrivilegeActivities(privilege: any) {
      return privilege === 'SHOW_ALL_ACTIVITIES';
    }
    // Gets User by username
    this.genericService.getUserByUsername('/users/username', this.authenticationService.getUsername())
      .subscribe(user => {
        // Gets list of privileges of current user and save titles of privileges in a list
        user.value.role.privileges.forEach(element => {
          this.ListOfPrivilegesByUser.push(element.title);
        });
        // return the first item respecting condition of findPrivilegeActivities function
        const AllActivitiesAccess = this.ListOfPrivilegesByUser.find(findPrivilegeActivities);
        if (isNullOrUndefined(AllActivitiesAccess)) {
          // gets visible Questions of activity related to authenticated user
          this.genericService.getGenericByAuthPage('/questions/allVisibleQuestionsByAuthActivity',
            user.value.username, this.selectedPage, this.item)
            .subscribe(
              data => {
                this.listQuestionsWithImage = data.content;
                this.listQuestions = [];
                // gets list of questions without image
                this.removeImagesFromQuestoions(this.listQuestionsWithImage, this.listQuestions);
                this.pageClient = data;
                this.total = this.pageClient.totalElements;
              });
        } else {
          // gets all visible questions without activity criteria
          this.genericService.getGenericByAuthPage('/questions/allVisibleQuestions', user.value.username, this.selectedPage, this.item)
            .subscribe(
              data => {
                this.listQuestionsWithImage = data.content;
                this.listQuestions = [];
                // get list of questions without image
                this.removeImagesFromQuestoions(this.listQuestionsWithImage, this.listQuestions);
                this.pageClient = data;
                this.total = this.pageClient.totalElements;
              });
        }
      });
  }

  /** Send Question object to edit component */
  openEditModal(question: any) {
    this.genericService.getGenericById('/questions', question.id).subscribe(data => {
      if (data.error === false) {
        this.redirectToEditModal.emit(data.value);
      } else {
        this.redirectToEditModal.emit(data.value);
      }
    });
  }

  /** Send Question object to detail modal */
  openDetailsModal(question: any) {
    this.genericService.getGenericById('/questions', question.id).subscribe(data => {
      if (data.error === false) {
        this.redirectToDetailsModal.emit(data.value);
      } else {
        this.redirectToDetailsModal.emit(data.value);
      }
    });
  }

  /** Delete Question */
  deleteQuestions(id: number) {
    Swal({
      title: 'Vous êtes Sur ?',
      text: 'Voulez vous vraiment supprimer cette question',
      type: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Oui',
      cancelButtonText: 'Non'
    }).then((result) => {
      if (result.dismiss !== Swal.DismissReason.cancel && result.dismiss !== Swal.DismissReason.backdrop) {
        this.genericService.deleteGeneric('/questions', id)
          .subscribe(data => {
            const obj = JSON.parse(data);
            if (obj.error === false) {
              this.ngOnInit();
              Swal({
                position: 'top-end',
                type: 'success',
                title: obj.value,
                showConfirmButton: false,
                timer: 1500
              });
              // Pagination control while deleting an object in a page who contain one element
              if (this.total % this.item === 1) {
                this.selectedPage = this.selectedPage - 1;
              }
              // reload table data
              this.reloadEvent.emit(null);
            } else {
              Swal({
                title: 'Erreur!',
                text: obj.value,
                type: 'error',
                confirmButtonText: 'Ok'
              });
            }
          });
      }
    });
  }

  /*Get all Technologies for select form*/
  getTechnologies() {
    this.genericService.getGenericList('/technologies/all')
      .subscribe(
        data => {
          this.listTechnologies = data;
          this.dropdownListTechnologies = [];
          this.listTechnologies.map(element => {
            this.dropdownListTechnologies.push({ id: element.id, title: element.title });
          });
        });
  }

  /*Get all degrees for select form*/
  getDegrees() {
    this.genericService.getGenericList('/degrees/all')
      .subscribe(
        data => {
          this.listDegrees = data;
          this.dropdownListDegrees = [];
          this.listDegrees.map(element => {
            this.dropdownListDegrees.push({ id: element.id, title: element.title });
          });
        });
  }

  /*Get all activities for select form*/
  getActivities() {
    this.genericService.getGenericList('/activities/all')
      .subscribe(
        data => {
          this.listActivities = data;
          this.dropdownListActivities = [];
          this.listActivities.map(element => {
            this.dropdownListActivities.push({ id: element.id, title: element.title });
          });
        });
  }

  /*Get all users for select form*/
  getAllUsers() {
    this.genericService.getGenericList('/users/all')
      .subscribe(
        data => {
          this.listUsernames = data;
          this.dropdownListUsernames = [];
          this.listUsernames.map(element => {
            this.dropdownListUsernames.push({ id: element.id, UserName: element.firstName.concat(' ').concat(element.lastName) });
          });
        });
  }

  /** Pagination: Change number of elements in the table */
  onSelect(itemsNumber: number) {
    this.selectedPage = itemsNumber - 1;
    if (this.searchInput !== '') {
      this.onSearch(this.searchInput);
    } else if (this.selectedTechnologies.length !== 0 || this.selectedDegrees.length !== 0 ||
      this.selectedNames.length !== 0 || this.selectedActivities.length !== 0) {
      this.advancedSearch(this.selectedTechnologies, this.selectedDegrees, this.selectedNames, this.selectedActivities);
    } else {
      this.getQuestions();
    }
  }

  /** Pagination: Change page number */
  getItems(pageNumber: number) {
    this.item = pageNumber;
    this.onSelect(1);
  }

  /** Simple search question */
  onSearch(search: String) {
    // Test the exist of SHOW_ALL_ACTIVITIE privilege
    function findPrivilegeActivities(privilege) {
      return privilege === 'SHOW_ALL_ACTIVITIES';
    }
    if (this.searchInput !== '') {
      // Gets User by username
      this.genericService.getUserByUsername('/users/username', this.authenticationService.getUsername())
        .subscribe(user => {
          // Gets list of privileges of current user and save titles of privileges in a list
          user.value.role.privileges.forEach(element => {
            this.ListOfPrivilegesByUser.push(element.title);
          });
          // return the first item respecting condition of findPrivilegeActivities function
          const AllActivitiesAccess = this.ListOfPrivilegesByUser.find(findPrivilegeActivities);
          if (isNullOrUndefined(AllActivitiesAccess)) {
            // gets visible Questions of activity related to authenticated user
            this.genericService.searchGenericByAuthPage('/questions/searchWithActivity',
              search, user.value.username, this.selectedPage, this.item)
              .subscribe(
                data => {
                  if (this.selectedPage > (data.totalPages) - 1) {
                    this.onSelect((data.value.totalPages));
                  } else {
                    this.listQuestionsWithImage = data.content;
                    this.listQuestions = [];
                    // get list of questions without image
                    this.removeImagesFromQuestoions(this.listQuestionsWithImage, this.listQuestions);
                    this.pageClient = new PageClient();
                    this.pageClient = data;
                    this.total = this.pageClient.totalElements;
                  }
                });
          } else {
            // gets all visible questions without activity criteria
            this.genericService.searchGenericByAuthPage('/questions/searchWithoutActivity',
              search, user.value.username, this.selectedPage, this.item)
              .subscribe(
                data => {
                  if (this.selectedPage > (data.totalPages) - 1) {
                    this.onSelect((data.totalPages));
                  } else {
                    this.listQuestionsWithImage = data.content;
                    this.listQuestions = [];
                    // get list of questions without image
                    this.removeImagesFromQuestoions(this.listQuestionsWithImage, this.listQuestions);
                    this.pageClient = new PageClient();
                    this.pageClient = data;
                    this.total = this.pageClient.totalElements;
                  }
                });
          }
        });
    } else {
      this.getQuestions();
    }
  }

  /** Advanced search question with filter */
  advancedSearch(selectedTechnologies: any, selectedDegrees: any, selectedNames: any, selectedActivities: any) {
    const filter = {
      technologies: selectedTechnologies == null ? null : selectedTechnologies,
      degrees: selectedDegrees == null ? null : selectedDegrees,
      names: selectedNames == null ? null : selectedNames,
      activities: selectedActivities == null ? null : selectedActivities,
    };
    function findPrivilegeActivities(privilege) {
      return privilege === 'SHOW_ALL_ACTIVITIES';
    }
    // Gets User by username
    this.genericService.getUserByUsername('/users/username', this.authenticationService.getUsername())
      .subscribe(user => {
        // Gets list of privileges of current user and save titles of privileges in a list
        user.value.role.privileges.forEach(element => {
          this.ListOfPrivilegesByUser.push(element.title);
        });
        // return the first item respecting condition of findPrivilegeActivities function
        const AllActivitiesAccess = this.ListOfPrivilegesByUser.find(findPrivilegeActivities);
        if (isNullOrUndefined(AllActivitiesAccess)) {
          this.genericService.getGenericPageByFilterByAuthPage('/questions/search/advancedSearch',
            this.authenticationService.getUsername(), this.selectedPage, this.item, filter)
            .subscribe(
              data => {
                this.listQuestionsWithImage = data.content;
                this.listQuestions = [];
                // get list of questions without image
                this.removeImagesFromQuestoions(this.listQuestionsWithImage, this.listQuestions);
                this.pageClient = new PageClient();
                this.pageClient = data;
                this.total = this.pageClient.totalElements;
              }
            );
        } else {
          this.genericService.getGenericPageByFilter('/questions/search/advancedSearch', this.selectedPage, this.item, filter)
            .subscribe(
              data => {
                this.listQuestionsWithImage = data.content;
                this.listQuestions = [];
                // get list of questions without image
                this.removeImagesFromQuestoions(this.listQuestionsWithImage, this.listQuestions);
                this.pageClient = new PageClient();
                this.pageClient = data;
                this.total = this.pageClient.totalElements;
              }
            );
        }
      });
  }

  /** NgPrime Advanced Search method to solve no method found erreur */
  onItemSelect(item: any) { }
  onSelectAll(items: any) { }

}
