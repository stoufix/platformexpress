import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Degree } from '@models/degree';
import { GenericService } from '@services/generic.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-degree-update',
  templateUrl: './degree-update.component.html',
  styleUrls: ['./degree-update.component.css']
})
export class DegreeUpdateComponent implements OnInit {

  // Get Degree to edit form actvities list component
  @Input() degreeToEdit: Degree;

  // Event for reload table content after update
  @Output() reloadEvent = new EventEmitter();

  constructor(private genericService: GenericService) { }

  ngOnInit() {
  }


  /** Update Degree */
  updateDegrees() {
    this.genericService.updateGeneric('/degrees', this.degreeToEdit.id, this.degreeToEdit)
      .subscribe(
        data => {
          this.degreeToEdit = data as Degree;
          if (data.error === false) {
            Swal({
              position: 'top-end',
              type: 'success',
              title: data.value,
              showConfirmButton: false,
              timer: 1500
            });
            // reload table data
            this.reloadEvent.emit(null);
          } else {
            Swal({
              title: 'Erreur!',
              text: data.value,
              type: 'error',
              confirmButtonText: 'ok'
            });
          }
        });
  }

}
