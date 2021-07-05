import { Component, OnInit } from '@angular/core';
import { GenericService } from '@services/generic.service';
import { PageClient } from '@models/page-client';
import { Reclamation } from '@models/reclamation';
import swal from 'sweetalert2';

@Component({
  selector: 'app-message-list',
  templateUrl: './message-list.component.html',
  styleUrls: ['./message-list.component.css']
})
export class MessageListComponent implements OnInit {

  // Messages list
  listMessages: any;

  // Pagination params
  pageClient: PageClient = new PageClient();
  total: number;
  selectedPage = 0;
  item = 5;
  searchInput: String = '';

  // Message to display
  detailMessage: Reclamation = new Reclamation();

  // Selected messages for delete by groupe
  selectedMessage: Array<Reclamation> = [];

  constructor(private messageService: GenericService) { }

  ngOnInit() {
    this.reloadData();
  }


  /** Reload data after every action */
  reloadData() {
    this.listMessages = this.getMessages();
  }

  /** Get all Messages */
  getMessages() {
    this.messageService.getGenericPage('/reclamations', this.selectedPage, this.item).subscribe(data => {
      this.pageClient = data;
      this.total = this.pageClient.totalElements;
      this.listMessages = data.content;
    });
  }

  /** Send Message object to detail modal */
  openDetailsModal(message: any) {
    this.messageService.getGenericById('/reclamations', message.id).subscribe(data => {
      this.detailMessage = data.value;
    });
  }

  /** Delete one message */
  deleteMesage(id: number) {
    swal({
      title: 'Vous êtes Sur ?',
      text: 'voulez vous vraiment supprimer cet message',
      type: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Oui',
      cancelButtonText: 'Non'
    }).then((result) => {
      if (result.dismiss !== swal.DismissReason.cancel && result.dismiss !== swal.DismissReason.backdrop) {
        this.messageService.deleteGeneric('/reclamations', id)
          .subscribe(data => {
            const obj = JSON.parse(data);
            if (obj.error === false) {
              this.ngOnInit();
              swal({
                position: 'top-end',
                type: 'success',
                title: obj.message,
                showConfirmButton: false,
                timer: 1500
              });
            } else {
              swal({
                title: 'Erreur!',
                text: obj.message,
                type: 'error',
                confirmButtonText: 'ok'
              });
            }
          });
      }
    });
  }

  /** Add checked message de delete list */
  AddToDeleteList(event, message) {
    if (event.target.checked) {
      this.selectedMessage.push(message);
    } else {
      const index = this.selectedMessage.indexOf(message);
      this.selectedMessage.splice(index, 1);
    }
  }

  /** Delete selected messages */
  deleteMessagesSelected() {
    swal({
      title: 'Vous êtes Sur ?',
      text: 'voulez vous vraiment supprimer cette liste de message',
      type: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Oui',
      cancelButtonText: 'Non'
    }).then((result) => {
      if (result.dismiss !== swal.DismissReason.cancel && result.dismiss !== swal.DismissReason.backdrop) {
        this.messageService.deleteListOfMessages('/reclamations/deleteMultipleReclamations', this.selectedMessage)
          .subscribe(data => {
            if (data.error === false) {
              this.ngOnInit();
              swal({
                position: 'top-end',
                type: 'success',
                title: data.message,
                showConfirmButton: false,
                timer: 1500
              });
              // Pagination control while deleting an object in a page who contain one element
              if (this.total % this.item === 1) {
                this.selectedPage = this.selectedPage - 1;
              }
              this.reloadData();
            } else {
              swal({
                title: 'Erreur!',
                text: data.message,
                type: 'error',
                confirmButtonText: 'ok'
              });
            }
          });
      }
    });
  }

  /** Search Message */
  onSearch(search: String) {
    if (this.searchInput !== '') {
      this.messageService.searchGeneric('/reclamations/search', search, this.selectedPage, this.item)
        .subscribe(
          data => {
            if (this.selectedPage > (data.totalPages) - 1) {
              this.onSelect((data.totalPages));
            } else {
              this.pageClient = new PageClient();
              this.pageClient = data;
              this.total = this.pageClient.totalElements;
              this.listMessages = this.pageClient.content;
            }
          });
    } else {
      this.getMessages();
    }
  }

  /** Pagination: Change number of elements in the table */
  onSelect(pageNumber: number) {
    this.selectedPage = pageNumber - 1;
    if (this.searchInput !== '') {
      this.onSearch(this.searchInput);
    } else {
      this.getMessages();
    }
  }

  /** Pagination: Change page number */
  getItems(itemsNumber: number) {
    this.item = itemsNumber;
    this.onSelect(1);
  }

}
