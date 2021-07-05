import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { GenericService } from '@services/generic.service';
import { Quiz } from '@models/quiz';
import Swal from 'sweetalert2';
import { PageClient } from '@models/page-client';
import { AuthenticationService } from '@services/authentication.service';
import { isNullOrUndefined } from 'util';
import { Technology } from '@models/technology';

@Component({
  selector: 'app-quiz-list',
  templateUrl: './quiz-list.component.html',
  styleUrls: ['./quiz-list.component.css']
})
export class QuizListComponent implements OnInit {

  // Type of listView to display
  @Input() listView: [Quiz];

  // Event to send Quiz object to detail modal
  @Output() redirect: EventEmitter<any> = new EventEmitter();

  // Event for reload table content
  @Output() reloadEvent = new EventEmitter();

  // List of quizzes
  listQuizzes: Array<Quiz> = [];
  show = true;

  // Quiz to details
  quizDetails: any = {};

  // Pagination params
  pageClient: PageClient = new PageClient();
  total: number;
  selectedPage = 0;
  item = 5;

  // For simple search
  searchInput: String = '';

  // List of privileges by user
  ListOfPrivilegesByUser: any = [];
  displayPrivilegeByActivities: Boolean = false;

  // Advanced search Dropdown lists
  dropdownListTechnologies = [];
  dropdownListDegrees = [];
  dropdownListActivities = [];
  dropdownListUsernames = [];
  dropdownSettingsDegrees = {};
  dropdownSettingsTechnologies = {};
  dropdownSettingsActivities = {};
  dropdownSettingsUsernames = {};
  listQuestionsWithImage: any = [];

  // For advanced search
  selectedTechnologies: any = [];
  selectedDegrees: any = [];
  selectedNames: any = [];
  selectedActivities: any = [];
  selectedItems = [];
  listQuestions: any = [];
  listUsernames: any = [];
  listTechnologies: Array<Technology> = [];
  listDegrees: any = [];
  listActivities: any = [];

  constructor(private genericService: GenericService, private authenticationService: AuthenticationService, private router: Router) { }

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
    this.getQuizzes();
    this.getTechnologies();
    this.getDegrees();
    this.getActivities();
    this.getAllUsers();
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
  /** Gets Quizzes based on privileges and visibility criteria */
  getQuizzes() {
    // test the exist of SHOW_ALL_ACTIVITIE privilege
    function findPrivilegeActivities(privilege) {
      return privilege === 'SHOW_ALL_ACTIVITIES';
    }
    // gets user by username
    this.genericService.getUserByUsername('/users/username', this.authenticationService.getUsername())
      .subscribe(user => {
        // list of privileges of current user and save titles of privileges in a list
        user.value.role.privileges.forEach(element => {
          this.ListOfPrivilegesByUser.push(element.title);
        });
        // return the first item respecting condition of findPrivilegeActivities function
        const AllActivitiesAccess = this.ListOfPrivilegesByUser.find(findPrivilegeActivities);
        if (isNullOrUndefined(AllActivitiesAccess)) {
          // gets visible quizzes of activity related to authenticated user
          this.genericService.getGenericByAuthPage('/quizzes/allVisibleQuizzesByAuthActivity',
            user.value.username, this.selectedPage, this.item)
            .subscribe(
              data => {
                this.pageClient = data;
                this.total = this.pageClient.totalElements;
                this.listQuizzes = this.pageClient.content;
              });
        } else {
          // gets all visible quizzes without activity criteria
          this.genericService.getGenericByAuthPage('/quizzes/allVisibleQuizzes', user.value.username, this.selectedPage, this.item)
            .subscribe(
              data => {
                this.pageClient = data;
                this.total = this.pageClient.totalElements;
                this.listQuizzes = this.pageClient.content;
              });
        }
      });
  }

  /** Send Quiz object to detail modal */
  openDetailsModal(quizId: number) {
    this.genericService.getGenericById('/quizzes', quizId).subscribe(data => {
      if (data.error === false) {
        this.redirect.emit(data.value);
      }
    });
  }

  /** Delete Quiz */
  deleteQuiz(id: number) {
    Swal({
      title: 'Vous êtes Sur ?',
      text: 'Voulez vous vraiment supprimer ce QCM',
      type: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Oui',
      cancelButtonText: 'Non'
    }).then((result) => {
      if (result.dismiss !== Swal.DismissReason.cancel && result.dismiss !== Swal.DismissReason.backdrop) {
        this.genericService.deleteGeneric('/quizzes', id)
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
              this.reloadEvent.emit(null);
            } else {
              Swal({
                title: 'Erreur!',
                text: obj.value,
                type: 'error',
                confirmButtonText: 'ok'
              });
            }
          });
      }
    });
  }

  /** Send Quiz object to edit component */
  updateQuiz(id: number) {
    this.router.navigate(['administration/quizzes/quizAssign', id]);
  }

  /** Pagination: Change page number */
  onSelect(pageNumber: number) {
    this.selectedPage = pageNumber - 1;
    if (this.searchInput !== '') {
      this.onSearch(this.searchInput);
    } else {
      this.getQuizzes();
    }
  }

  /** Pagination: Change number of elements in the table */
  getItems(itemsNumber: number) {
    this.item = itemsNumber;
    this.onSelect(1);
  }

  /** Simple search */
  onSearch(search: String) {
    // test the exist of SHOW_ALL_ACTIVITIE privilege
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
            // gets visible Quizzes of activity related to authenticated user
            this.genericService.searchGenericByAuthPage('/quizzes/searchWithActivity',
              search, user.value.username, this.selectedPage, this.item)
              .subscribe(
                data => {
                  if (this.selectedPage > (data.totalPages) - 1) {
                    this.onSelect((data.totalPages));
                  } else {
                    this.pageClient = new PageClient();
                    this.pageClient = data;
                    this.total = this.pageClient.totalElements;
                    this.listQuizzes = this.pageClient.content;
                  }
                });
          } else {
            // gets all visible quizzes without activity criteria
            this.genericService.searchGenericByAuthPage('/quizzes/searchWithoutActivity',
              search, user.value.username, this.selectedPage, this.item)
              .subscribe(
                data => {
                  if (this.selectedPage > (data.totalPages) - 1) {
                    this.onSelect((data.totalPages));
                  } else {
                    this.pageClient = new PageClient();
                    this.pageClient = data;
                    this.total = this.pageClient.totalElements;
                    this.listQuizzes = this.pageClient.content;
                  }
                });
          }
        });
    } else {
      this.getQuizzes();
    }
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

  /** Advanced search quiz with filter */
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
          this.genericService.getGenericPageByFilterByAuthPage('/quizzes/search/advancedSearchWithActivity',
            this.authenticationService.getUsername(), this.selectedPage, this.item, filter)
            .subscribe(
              data => {
                this.listQuizzes = data.content;
                this.pageClient = new PageClient();
                this.pageClient = data;
                this.total = this.pageClient.totalElements;
              }
            );
        } else {
          this.genericService.getGenericPageByFilter('/quizzes/search/advancedQuizSearch', this.selectedPage, this.item, filter)
            .subscribe(
              data => {
                this.listQuizzes = data.content;
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
