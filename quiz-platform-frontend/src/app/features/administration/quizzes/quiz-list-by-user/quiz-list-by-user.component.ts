import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { GenericService } from '@services/generic.service';
import { Quiz } from '@models/quiz';
import Swal from 'sweetalert2';
import { PageClient } from '@models/page-client';
import { AuthenticationService } from '@services/authentication.service';

@Component({
  selector: 'app-quiz-list-by-user',
  templateUrl: './quiz-list-by-user.component.html',
  styleUrls: ['./quiz-list-by-user.component.css']
})
export class QuizListByUserComponent implements OnInit {

  // Type of listView to display
  @Input() listView: [Quiz];

  // Event to send Quiz object to detail modal
  @Output() redirect: EventEmitter<any> = new EventEmitter();

  // List of quizzes
  listQuizzes: any = [];
  // For simple search
  searchInput: String = '';
  // Pagination params
  pageClient: PageClient = new PageClient();
  total: number;
  selectedPage: number;
  item = 5;
  public items = [5, 10, 20, 50];

  // Event for reload table content
  @Output() reloadEvent = new EventEmitter();
  // Advanced search Dropdown lists
  dropdownListTechnologies = [];
  dropdownListDegrees = [];
  dropdownSettingsDegrees = {};
  dropdownSettingsTechnologies = {};

  // For advanced search
  selectedTechnologies: any = [];
  selectedDegrees: any = [];
  selectedItems = [];
  listTechnologies: any = [];
  listDegrees: any = [];

  constructor(private genericService: GenericService, private router: Router, private authenticationService: AuthenticationService) { }

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
    this.listQuizzes = this.getQuizzesByUserName();
    this.getTechnologies();
    this.getDegrees();
  }

  /** Get all Quizzes by userName*/
  getQuizzesByUserName() {
    this.genericService.getUserByUsername('/users/username', this.authenticationService.getUsername())
      .subscribe(data1 => {
        this.genericService.getGenericPagesById('/users', '/createdquizzes', data1.value.id, this.selectedPage, this.item)
          .subscribe(
            data => {
              this.pageClient = data;
              this.total = this.pageClient.totalElements;
              this.listQuizzes = this.pageClient.content;
            });
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

  /** Pagination: Change number of elements in the table */
  onSelect(pageNumber: number) {
    this.selectedPage = pageNumber - 1;
    this.getQuizzesByUserName();
  }

  /** Pagination: Change page number */
  getItems(itemsNumber: number) {
    this.item = itemsNumber;
    this.onSelect(1);
    this.getQuizzesByUserName();
  }
  /** Simple search */
  onSearch(search: String) {
    if (this.searchInput !== '') {
      // Gets User by username
      this.genericService.getUserByUsername('/users/username', this.authenticationService.getUsername())
        .subscribe(user => {
          // gets quizzes without by user
          this.genericService.searchGenericByAuthPage('/quizzes/searchByUser', search, user.value.username, this.selectedPage, this.item)
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
        });
    } else {
      this.getQuizzesByUserName();
    }
  }

  /** Change quiz sharebility */
  changeSharedOfQCM(id: any, title: any) {
    this.genericService.updateShared('/quizzes', id, '/shared', null).subscribe(data => {
      if (data.value['shared'] === false) {
        Swal({
          position: 'top-end',
          type: 'success',
          title: data.value,
          showConfirmButton: false,
          timer: 1500
        });
      } else {
        Swal({
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

  /** Get all Technologies for select form*/
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

  /** Get all degrees for select form*/
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

  /** Advanced search question with filter */
  advancedSearch(selectedTechnologies: any, selectedDegrees: any) {
    const filter = {
      technologies: selectedTechnologies == null ? null : selectedTechnologies,
      degrees: selectedDegrees == null ? null : selectedDegrees,
    };
    this.genericService.getGenericPageByFilterByAuthPage('/quizzes/search/advancedSearchByUser',
      this.authenticationService.getUsername(), this.selectedPage, this.item, filter)
      .subscribe(
        data => {
          this.listQuizzes = data.content;
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
