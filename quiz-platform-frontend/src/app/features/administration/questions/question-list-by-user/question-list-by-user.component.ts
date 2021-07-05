import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Question } from '@models/question';
import { PageClient } from '@models/page-client';
import { GenericService } from '@services/generic.service';
import swal from 'sweetalert2';
import { AuthenticationService } from '@services/authentication.service';

@Component({
  selector: 'app-question-list-by-user',
  templateUrl: './question-list-by-user.component.html',
  styleUrls: ['./question-list-by-user.component.css']
})
export class QuestionListByUserComponent implements OnInit {

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

  // Advanced search Dropdown lists
  dropdownListTechnologies = [];
  dropdownListDegrees = [];
  dropdownSettingsDegrees = {};
  dropdownSettingsTechnologies = {};
  // For advanced search params
  selectedTechnologies: any = [];
  selectedDegrees: any = [];
  selectedItems = [];

  // Pagination params
  pageClient: PageClient = new PageClient();
  total: number;
  selectedPage = 0;
  item = 5;

  // Simple search term
  searchInput: String = '';

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
  }


  /** Reload data after every action */
  reloadData() {
    this.listQuestions = this.getQuestionsByUserName();
    this.getTechnologies();
    this.getDegrees();
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

  /** Get all Questions by userName */
  getQuestionsByUserName() {
    this.genericService.getUserByUsername('/users/username', this.authenticationService.getUsername())
      .subscribe(data1 => {
        this.genericService.getGenericPagesById('/users', '/createdquestions', data1.value.id, this.selectedPage, this.item)
          .subscribe(
            data => {
              this.listQuestionsWithImage = data.content;
              this.listQuestions = [];
              // get list of questions without image
              this.removeImagesFromQuestoions(this.listQuestionsWithImage, this.listQuestions);
              this.pageClient = data;
              this.total = this.pageClient.totalElements;
            });
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
    swal({
      title: 'Vous êtes Sur ?',
      text: 'Voulez vous vraiment supprimer cette question',
      type: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Oui',
      cancelButtonText: 'Non'
    }).then((result) => {
      if (result.dismiss !== swal.DismissReason.cancel && result.dismiss !== swal.DismissReason.backdrop) {
        this.genericService.deleteGeneric('/questions', id)
          .subscribe(data => {
            const obj = JSON.parse(data);
            if (obj.error === false) {
              this.ngOnInit();
              swal({
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
              swal({
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


  /** Change question sharebility */
  changeSharedOfQuestion(id: number) {
    this.genericService.updateShared('/questions', id, '/shared', null).subscribe(data => {
      if (data.value['shared'] === false) {
        swal({
          position: 'top-end',
          type: 'success',
          title: data.value,
          showConfirmButton: false,
          timer: 1500
        });
      } else {
        swal({
          position: 'top-end',
          type: 'success',
          title: data.value,
          showConfirmButton: false,
          timer: 1500
        });
      }
      this.reloadEvent.emit(null);
      this.reloadData();
    });
  }

  /** Pagination: Change page number */
  onSelect(pageNumber: number) {
    this.selectedPage = pageNumber - 1;
    if (this.searchInput !== '') {
      this.onSearch(this.searchInput);
    } else if (this.selectedTechnologies.length !== 0 || this.selectedDegrees.length !== 0) {
      this.advancedSearch(this.selectedTechnologies, this.selectedDegrees);
    } else {
      this.getQuestionsByUserName();
    }
  }

  /** Pagination: Change number of elements in the table */
  getItems(itemsNumber: number) {
    this.item = itemsNumber;
    this.onSelect(1);
  }

  /** Simple search question */
  onSearch(search: String) {
    if (this.searchInput !== '') {
      this.genericService.searchGenericByAuthPage('/questions/searchByUser',
        search, this.authenticationService.getUsername(), this.selectedPage, this.item)
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
    } else {
      this.getQuestionsByUserName();
    }
  }

  /** Advanced search question with filter */
  advancedSearch(selectedTechnologies: any, selectedDegrees: any) {
    const filter = {
      technologies: selectedTechnologies == null ? null : selectedTechnologies,
      degrees: selectedDegrees == null ? null : selectedDegrees,
    };
    this.genericService.getGenericPageByFilterByAuthPage('/questions/search/advancedSearchByUser',
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
  }

  /** NgPrime Advanced Search method to solve no method found erreur */
  onItemSelect(item: any) { }
  onSelectAll(items: any) { }

}
