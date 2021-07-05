import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { GenericService } from '@services/generic.service';
import { Technology } from '@models/technology';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-technology-update',
  templateUrl: './technology-update.component.html',
  styleUrls: ['./technology-update.component.css']
})
export class TechnologyUpdateComponent implements OnInit {

  // Get Technology to edit form technologies list component
  @Input() technologyToEdit: Technology;

  // Event for reload table content after update
  @Output() reloadEvent = new EventEmitter();

  constructor(private genericService: GenericService) { }

  ngOnInit() {
  }

  /** Update Technology */
  updateTechnologies() {
    this.genericService.updateGeneric('/technologies', this.technologyToEdit.id, this.technologyToEdit)
      .subscribe(
        data => {
          this.technologyToEdit = data as Technology;
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
