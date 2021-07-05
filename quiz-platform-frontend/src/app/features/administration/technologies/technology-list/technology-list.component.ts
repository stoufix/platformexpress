import { Component, OnInit } from '@angular/core';
import { GenericService } from '@services/generic.service';
import { Technology } from '@models/technology';
import { PageClient } from '@models/page-client';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-technology-list',
  templateUrl: './technology-list.component.html',
  styleUrls: ['./technology-list.component.css']
})
export class TechnologyListComponent implements OnInit {

  // List of technologies
  listTechnologies: any;

  // Technology to edit
  editTechnology: Technology = new Technology();

  // Pagination params
  pageClient: PageClient = new PageClient();
  total: number;
  selectedPage = 0;
  item = 5;
  searchInput: String = '';

  constructor(private genericService: GenericService) { }

  ngOnInit() {
    this.listTechnologies = [];
    this.reloadData();
  }


  /** Reload data after every action */
  reloadData() {
    this.listTechnologies = this.getTechnologies();
  }

  /** Get all Technologies*/
  getTechnologies() {
    this.genericService.getGenericPage('/technologies', this.selectedPage, this.item)
      .subscribe(
        data => {
          this.pageClient = data;
          this.total = this.pageClient.totalElements;
          this.listTechnologies = this.pageClient.content;
        });
  }

  /** Send Technology object to edit modal */
  openEditModal(technology: any) {
    this.genericService.getGenericById('/technologies', technology.id).subscribe(data => {
      if (data.error === false) {
        this.editTechnology = data.value;
      }
    });
  }

  /** Delete Technology */
  deleteTechnologies(id: number) {
    Swal({
      title: 'Vous êtes Sur ?',
      text: 'Voulez vous vraiment supprimer cette technologie',
      type: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Oui',
      cancelButtonText: 'Non'
    }).then((result) => {
      if (result.dismiss !== Swal.DismissReason.cancel && result.dismiss !== Swal.DismissReason.backdrop) {
        this.genericService.deleteGeneric('/technologies', id)
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
      this.getTechnologies();
    }
  }
  /** Pagination: Change page number */
  getItems(itemsNumber: number) {
    this.item = itemsNumber;
    this.onSelect(1);
  }
  /** Search Technology */
  onSearch(search: String) {
    if (this.searchInput !== '') {
      this.genericService.searchGeneric('/technologies/search', search, this.selectedPage, this.item)
        .subscribe(
          data => {
            if (this.selectedPage > (data.totalPages) - 1) {
              this.onSelect((data.totalPages));
            } else {
              console.log(data);
              this.pageClient = new PageClient();
              this.pageClient = data;
              this.total = this.pageClient.totalElements;
              this.listTechnologies = this.pageClient.content;
            }
          });
    } else {
      this.getTechnologies();
    }
  }

}
