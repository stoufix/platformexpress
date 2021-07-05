import { Component, OnInit } from '@angular/core';
import { GenericService } from '@services/generic.service';
import { Degree } from '@models/degree';
import Swal from 'sweetalert2';
import { PageClient } from '@models/page-client';


@Component({
  selector: 'app-degree-list',
  templateUrl: './degree-list.component.html',
  styleUrls: ['./degree-list.component.css']
})
export class DegreeListComponent implements OnInit {

  // List of degrees
  listDegrees: any;

  // Degree to edit
  editDegree: Degree = new Degree();

  // Pagination params
  pageClient: PageClient = new PageClient();
  total: number;
  selectedPage = 0;
  item = 5;
  searchInput: String = '';

  constructor(private genericService: GenericService) { }

  ngOnInit() {
    this.listDegrees = [];
    this.reloadData();
  }


  /** Reload data after every action */
  reloadData() {
    this.listDegrees = this.getDegrees();
  }

  /** Get all Degrees */
  getDegrees() {
    this.genericService.getGenericPage('/degrees', this.selectedPage, this.item).subscribe(data => {
      this.pageClient = data;
      this.total = this.pageClient.totalElements;
      this.listDegrees = data.content;
    });
  }

  /** Send Degree object to edit modal */
  openEditModal(degree: any) {
    this.genericService.getGenericById('/degrees', degree.id).subscribe(data => {
      if (data.error === false) {
        this.editDegree = data.value;
      }
    });
  }

  /** Delete Degree */
  deleteDegrees(id: number) {
    Swal({
      title: 'Vous êtes Sûr ?',
      text: 'Voulez vous vraiment supprimer ce niveau',
      type: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Oui',
      cancelButtonText: 'Non'
    }).then((result) => {
      if (result.dismiss !== Swal.DismissReason.cancel && result.dismiss !== Swal.DismissReason.backdrop) {
        this.genericService.deleteGeneric('/degrees', id)
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
              this.reloadData();
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

  /** Pagination: Change number of elements in the table */
  onSelect(pageNumber: number) {
    this.selectedPage = pageNumber - 1;
    if (this.searchInput !== '') {
      this.onSearch(this.searchInput);
    } else {
      this.getDegrees();
    }
  }

  /** Pagination: Change page number */
  getItems(itemsNumber: number) {
    this.item = itemsNumber;
    this.onSelect(1);
  }

  /** Search Degree */
  onSearch(search: String) {
    if (this.searchInput !== '') {
      this.genericService.searchGeneric('/degrees/search', search, this.selectedPage, this.item)
        .subscribe(
          data => {
            if (this.selectedPage > (data.totalPages) - 1) {
              this.onSelect((data.totalPages));
            } else {
              this.pageClient = new PageClient();
              this.pageClient = data;
              this.total = this.pageClient.totalElements;
              this.listDegrees = this.pageClient.content;
            }
          });
    } else {
      this.getDegrees();
    }
  }

}
