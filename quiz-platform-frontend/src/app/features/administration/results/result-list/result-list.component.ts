import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import Swal from 'sweetalert2';
import { PageClient } from '@models/page-client';
import { GenericService } from '@services/generic.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-result-list',
  templateUrl: './result-list.component.html',
  styleUrls: ['./result-list.component.css']
})
export class ResultListComponent implements OnInit {

  // List of results
  listResults: any;

  // Event to send ResultS object to details modal
  @Output() redirect: EventEmitter<any> = new EventEmitter();


  // Pagination params
  pageClient: PageClient = new PageClient();
  total: number;
  selectedPage = 0;
  item = 5;
  searchInput: String = '';

  constructor(private genericService: GenericService, private router: Router) { }

  ngOnInit() {
    this.listResults = [];
    this.reloadData();
  }

  /** Reload data after every action */
  reloadData() {
    this.listResults = this.getResults();
  }

  /** Get all Results */
  getResults() {
    this.genericService.getGenericPage('/onlineInterviewQuizResult', this.selectedPage, this.item)
      .subscribe(
        data => {
          this.pageClient = data;
          this.total = this.pageClient.totalElements;
          this.listResults = data.content;
        });
  }

  /** Delete Resultat */
  deleteResult(id: number) {
    Swal({
      title: 'Vous êtes Sur ?',
      text: 'voulez vous vraiment supprimer cette resultat',
      type: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Oui',
      cancelButtonText: 'Non'
    }).then((result) => {
      if (result.dismiss !== Swal.DismissReason.cancel && result.dismiss !== Swal.DismissReason.backdrop) {
        this.genericService.deleteGeneric('/onlineInterviewQuizResult', id)
          .subscribe(data => {
            this.ngOnInit();
            Swal({
              position: 'top-end',
              type: 'success',
              title: 'Resultat supprimé avec succés',
              showConfirmButton: false,
              timer: 1500
            });
          });
      }
    });
  }

  /** Pagination: Change number of elements in the table */
  onSelect(pageNumber: number) {
    this.selectedPage = pageNumber - 1;
    if (this.searchInput !== '') {
      this.onSearch(this.searchInput);
    } else {
      this.getResults();
    }
  }

  /** Pagination: Change page number */
  getItems(itemsNumber: number) {
    this.item = itemsNumber;
    this.onSelect(1);
  }

  /** Search Result */
  onSearch(search: String) {
    if (this.searchInput !== '') {
      this.genericService.searchGeneric('/onlineInterviewQuizResult/search', search, this.selectedPage, this.item)
        .subscribe(
          data => {
            if (this.selectedPage > (data.totalPages) - 1) {
              this.onSelect((data.totalPages));
            } else {
              this.pageClient = new PageClient();
              this.pageClient = data;
              this.total = this.pageClient.totalElements;
              this.listResults = this.pageClient.content;
            }
          });
    } else {
      this.getResults();
    }
  }

  /** Open Result details modal */
  openResultDetail(onlineInterviewQuizResultId: number) {
    this.genericService.getGenericByBody('/onlineInterviewQuizResult/id', onlineInterviewQuizResultId).subscribe(data => {
      if (data.error === false) {
        console.log(data.value);
        this.redirect.emit(data.value);
      }
    });
  }

}
