import { Component, OnInit } from '@angular/core';
import swal from 'sweetalert2';
import { GenericService } from 'src/app/core/services/generic.service';
import { PageClient } from '@models/page-client';
import { OnlineInterview } from '@models/online-interview';
import { Router } from '@angular/router';

@Component({
  selector: 'app-assign-quiz-candidate-list',
  templateUrl: './assign-quiz-candidate-list.component.html',
  styleUrls: ['./assign-quiz-candidate-list.component.css']
})

export class AssignQuizCandidateListComponent implements OnInit {

  // Assigned quiz to details
  detailAssignedQuizzes: any = {};
  // Assigned quiz to update
  onlineInterviewToUpdate: OnlineInterview = new OnlineInterview();
  // Paagination params
  pageClient: PageClient = new PageClient();
  total: number;
  selectedPage: number;
  item = 5;
  // Interview id
  idOnlineInterview: number;
  // List of assigned online interview
  listAssignedQuizzesByOnlineInterview: any = [];
  // List of online interview
  listOnlineInterview: Array<OnlineInterview> = [];
  searchInput: String = '';

  constructor(private genericService: GenericService, private route: Router) { }

  ngOnInit() {
    this.reloadData();
  }

  /**Reload data after every action */
  reloadData() {
    this.getListOfAssignQuizCandidate();
  }

  /** Gets all AssignedQuizUser */
  getListOfAssignQuizCandidate() {
    this.genericService.getGenericPage('/assignquizonlineinterview', this.selectedPage, this.item).subscribe(data => {
      this.pageClient = data;
      this.total = this.pageClient.totalElements;
      this.listOnlineInterview = this.pageClient.content;
    });
  }

  /** Send online interview object to edit modal */
  openEditModal(onlineInterview: any) {
    this.onlineInterviewToUpdate = onlineInterview;
    this.route.navigate(['administration/assign-quizzes-candidates/update', onlineInterview.id]);
  }

  /** Delete all assigned quizzes for one candidate */
  deleteAssignedQuizzes(id: number, candidate: any) {
    const firstName = candidate.application.candidate.firstName;
    const lastName = candidate.application.candidate.lastName;
    swal({
      title: 'Vous êtes Sur ?',
      text: 'Voulez vous vraiment supprimer les QCM affectés à ' + firstName + ' ' + lastName,
      type: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Oui',
      cancelButtonText: 'Non'
    }).then(result => {
      if (result.dismiss !== swal.DismissReason.cancel && result.dismiss !== swal.DismissReason.backdrop) {
        this.genericService.deleteGeneric('/assignquizonlineinterview', id).subscribe(data => {
          if (data.error === false) {
            swal({
              position: 'top-end',
              type: 'success',
              title: data.value,
              showConfirmButton: false,
              timer: 1500
            });
            // Pagination control while deleting an object in a page who contain one element 
            if (this.total % this.item === 1) {
              this.selectedPage = this.selectedPage - 1;
              this.reloadData();
            }
            this.reloadData();
          } else {
            swal({
              title: 'Erreur!',
              text: data.value,
              type: 'error',
              confirmButtonText: 'Ok'
            });
          }
        }
        );
      }
    });
  }

  /** Delete one assigned quizzes for one candidate */
  deleteAssignedQuiz(body: any) {
    swal({
      title: 'Vous êtes Sur ?',
      text: 'voulez vous vraiment supprimer ce QCM',
      type: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Oui',
      cancelButtonText: 'Non'
    }).then(result => {
      if (result.dismiss !== swal.DismissReason.cancel && result.dismiss !== swal.DismissReason.backdrop) {
        this.genericService.deleteWithBody('/assignquizonlineinterview', { body: body }).subscribe(data => {
          if (data.error === false) {
            swal({
              position: 'top-end',
              type: 'success',
              title: data.value,
              showConfirmButton: false,
              timer: 1500
            });
            this.getListOfAssignQuizCandidate();
          } else {
            swal({
              title: 'Erreur!',
              text: data.value,
              type: 'error',
              confirmButtonText: 'Ok'
            });
          }
        });
      }
    });
  }

  /** Open new mail message with candidate's mail adresse as destination **/
  mailTo(adresse: any) {
    const mail = document.createElement('a');
    mail.href = 'mailto:' + adresse;
    mail.click();
  }

  /** Pagination: Change number of elements in the table */
  onSelect(pageNumber: number) {
    this.selectedPage = pageNumber - 1;
    this.getListOfAssignQuizCandidate();
  }

  /** Pagination: Change page number */
  getItems(itemsNumber: number) {
    this.item = itemsNumber;
    this.onSelect(1);
    this.getListOfAssignQuizCandidate();
  }

  /** Search Assigned Quiz Online Interview */
  onSearch(search: String) {
    if (this.searchInput !== '') {
      this.genericService.searchGeneric('/assignquizonlineinterview/search', search, this.selectedPage, this.item)
        .subscribe(
          data => {
            if (this.selectedPage > (data.totalPages) - 1) {
              this.onSelect((data.totalPages));
            } else {
              this.pageClient = new PageClient();
              this.pageClient = data;
              this.total = this.pageClient.totalElements;
              this.listOnlineInterview = this.pageClient.content;
            }
          });
    } else {
      this.getListOfAssignQuizCandidate();
    }
  }
}
