import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import Swal from 'sweetalert2';
import { Degree } from '@models/degree';
import { GenericService } from '@services/generic.service';

@Component({
  selector: 'app-degree-add',
  templateUrl: './degree-add.component.html',
  styleUrls: ['./degree-add.component.css']
})
export class DegreeAddComponent implements OnInit {

  // Declare object for Degree create
  degreeToAdd: Degree = new Degree();

  // Event for table data
  @Output() reloadEvent = new EventEmitter();

  constructor(private genericService: GenericService) { }

  ngOnInit() {
  }


  /** Create Degree */
  createDegrees() {
    this.genericService.createGeneric('/degrees', this.degreeToAdd)
      .subscribe(data => {
        if (data.error === false) {
          Swal({
            position: 'top-end',
            type: 'success',
            title: data.value,
            showConfirmButton: false,
            timer: 1500
          }),
            // reload table data
            this.reloadEvent.emit(null);
          // empty add form
          this.emptyObject();
        } else {
          Swal({
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
    this.degreeToAdd = new Degree();
  }

}
