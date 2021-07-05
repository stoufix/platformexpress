import { Component, OnInit, ViewChild } from '@angular/core';
import { GenericService } from '@services/generic.service';
import swal from 'sweetalert2';
import { PageClient } from '@models/page-client';
import { Router } from '@angular/router';


@Component({
  selector: 'app-candidate-list',
  templateUrl: './candidate-list.component.html',
  styleUrls: ['./candidate-list.component.css']
})
export class CandidateListComponent implements OnInit {

  // List of candidates
  listCandidates: any;

  // Pagination params
  pageClient: PageClient = new PageClient();
  total: number;
  selectedPage = 0;
  item = 5;
  searchInput: String = '';

  constructor(private genericService: GenericService, private router: Router) { }

  ngOnInit() {
    this.listCandidates = [];
    this.reloadData();
  }

  /** Reload data after every action */
  reloadData() {
    this.listCandidates = this.getCandidates();
  }

  /** Get all Candidates */
  getCandidates() {
    this.genericService.getGenericPage('/candidates', this.selectedPage, this.item)
      .subscribe(
        data => {
          this.pageClient = data;
          this.total = this.pageClient.totalElements;
          this.listCandidates = data.content;
        });
  }

  /** Open new mail message with candidate's mail adresse as destination **/
  mailTo(adresse: any) {
    const mail = document.createElement('a');
    mail.href = 'mailto:' + adresse;
    mail.click();
  }

  /** Change state for candidates's account */
  changeStateUserAccount(id, username) {
    this.genericService.updateStateUserAccount('/candidates', id, '/account', null).subscribe(data => {
      if (data.error === false) {
        swal({
          position: 'top-end',
          type: 'success',
          title: data.value,
          showConfirmButton: false,
          timer: 1500
        });
      } else {
        swal({
          title: 'Erreur!',
          text: data.value,
          type: 'error',
          confirmButtonText: 'ok'
        });
      }
      this.reloadData();
    });
  }

  /** Delete Candidate */
  deleteCandidate(id: number) {
    swal({
      title: 'Vous êtes Sur ?',
      text: 'voulez vous vraiment supprimer ce candidat',
      type: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Oui',
      cancelButtonText: 'Non'
    }).then((result) => {
      if (result.dismiss !== swal.DismissReason.cancel && result.dismiss !== swal.DismissReason.backdrop) {
        this.genericService.deleteGeneric('/candidates', id)
          .subscribe(data => {
            this.ngOnInit();
            swal({
              position: 'top-end',
              type: 'success',
              title: 'Candidat supprimé avec succés',
              showConfirmButton: false,
              timer: 1500
            });
          });
      }
    });
  }

  /** Open candidate detail component */
  openDetails(id: number) {
    this.router.navigate(['administration/candidates/candidate-detail', id]);
  }

  /** Pagination: Change number of elements in the table */
  onSelect(pageNumber: number) {
    this.selectedPage = pageNumber - 1;
    if (this.searchInput !== '') {
      this.onSearch(this.searchInput);
    } else {
      this.getCandidates();
    }
  }

  /** Pagination: Change page number */
  getItems(itemsNumber: number) {
    this.item = itemsNumber;
    this.onSelect(1);
  }

  /** Search Candidate */
  onSearch(search: String) {
    if (this.searchInput !== '') {
      this.genericService.searchGeneric('/candidates/search', search, this.selectedPage, this.item)
        .subscribe(
          data => {
            if (this.selectedPage > (data.totalPages) - 1) {
              this.onSelect((data.totalPages));
            } else {
              this.pageClient = new PageClient();
              this.pageClient = data;
              this.total = this.pageClient.totalElements;
              this.listCandidates = this.pageClient.content;
            }
          });
    } else {
      this.getCandidates();
    }
  }

}
