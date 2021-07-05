

import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Question } from '@models/question';
import { Proposition } from '@models/proposition';
import { GenericService } from '@services/generic.service';
import { isNullOrUndefined } from 'util';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-question-update',
  templateUrl: './question-update.component.html',
  styleUrls: ['./question-update.component.css']
})
export class QuestionUpdateComponent implements OnInit {

  // Get Question to edit form actvities list component
  @Input() questionToEdit: Question;

  // Event for reload table content after update
  @Output() reloadEvent = new EventEmitter();

  // Propostion to create
  propositionToAdd: Proposition = new Proposition();

  // Propostion to edit
  propositionToEdit: Proposition = new Proposition();

  // Visibility of question
  visibilityValue: boolean;

  // number of propositions
  propositionsLength: number;

  constructor(private genericService: GenericService) { }

  ngOnInit() {
    this.questionToEdit = new Question;
  }

  /** Update Question */
  updateQuestions() {
    let propositionU = {
      title: '',
      description: '',
      valid: false
    };
    const arr2 = this.questionToEdit.propositions;
    this.propositionsLength = this.questionToEdit.propositions.length;
    this.questionToEdit.propositions = [];
    arr2.forEach(p => {
      propositionU.title = p.title;
      propositionU.description = p.description;
      propositionU.valid = p.valid;
      this.questionToEdit.propositions.push(propositionU);
      propositionU = new Proposition;
    });
    if (isNullOrUndefined(this.questionToEdit.description)) {
      Swal({
        title: 'Erreur!',
        text: 'Vous ne pouvez modifier une question sans ajouter une description',
        type: 'error',
        confirmButtonText: 'Ok'
      });
    } else if (this.questionToEdit.propositions.length < 2) {
      Swal({
        title: 'Erreur!',
        text: 'Vous ne pouvez pas ajouter moins que deux propositions',
        type: 'error',
        confirmButtonText: 'Ok'
      });
    } else {
      this.genericService.updateGeneric('/questions', this.questionToEdit.id, this.questionToEdit)
        .subscribe(
          data => {
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
          },
          error => console.log(error));
    }
  }

  /**Add proposition */
  addPropositionU(title: any, description: any, valid: any) {
    const proposition = {
      title: title,
      description: description,
      valid: valid
    };
    this.questionToEdit.propositions.push(proposition);
    this.propositionToAdd.description = null;
  }

  /**Edit proposition */
  editPropositionEditQuestion(proposition: any) {
    proposition.editMode = false;
    this.propositionToEdit = new Proposition();
    this.propositionToEdit.description = proposition.description;
    this.propositionToEdit.title = proposition.title;
    this.propositionToEdit.valid = proposition.valid;
    const index = this.questionToEdit.propositions.indexOf(proposition);
    this.questionToEdit.propositions.splice(index, 1, this.propositionToEdit);
  }

  /**Delete proposition */
  deletePropositionsEditQuestion(proposition: any) {
    this.questionToEdit.propositions.forEach((element, index) => {
      if (proposition.description === element.description) {
        this.questionToEdit.propositions.splice(index, 1);
      }
    });
  }

  /**Check box action for visibility of the question */
  checkVisibility() {
    if (this.questionToEdit.shared) {
      this.questionToEdit.shared = true;
    } else {
      this.questionToEdit.shared = false;
    }
  }

}
