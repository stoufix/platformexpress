import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { GenericService } from '@services/generic.service';
import { Technology } from '@models/technology';
import swal from 'sweetalert2';

@Component({
  selector: 'app-technology-add',
  templateUrl: './technology-add.component.html',
  styleUrls: ['./technology-add.component.css']
})
export class TechnologyAddComponent implements OnInit {

  // Technology object for create action
  technologyToAdd: Technology = new Technology();

  // Event for reload table content
  @Output() reloadEvent = new EventEmitter();

  constructor(private genericService: GenericService) { }

  ngOnInit() {
  }


  /** Create Thechnology */
  createTechnologies() {
    this.genericService.createGeneric('/technologies', this.technologyToAdd)
      .subscribe(data => {
        if (data.error === false) {
          swal({
            position: 'top-end',
            type: 'success',
            title: data.value,
            showConfirmButton: false,
            timer: 1500
          });
          // reload table data
          this.reloadEvent.emit(null);
          // empty add form fields
          this.emptyObject();
        } else {
          swal({
            title: 'Erreur!',
            text: data.value,
            type: 'error',
            confirmButtonText: 'ok'
          });
          this.emptyObject();
        }
      });
  }

  /** Empty add form fields */
  emptyObject() {
    this.technologyToAdd = new Technology();
  }

}
