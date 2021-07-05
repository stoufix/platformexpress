import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { GenericService } from 'src/app/core/services/generic.service';
import { AuthenticationService } from 'src/app/core/services/authentication.service';
import { Question } from 'src/app/shared/models/question';
import Swal from 'sweetalert2';
import { isNullOrUndefined } from 'util';
import { Proposition } from 'src/app/shared/models/proposition';


@Component({
  selector: 'app-question-add',
  templateUrl: './question-add.component.html',
  styleUrls: ['./question-add.component.css']
})
export class QuestionAddComponent implements OnInit {

  // Event for reload table content
  @Output() reloadEvent = new EventEmitter();

  // Question to create
  questionToAdd: Question = new Question();

  // Propostion to create
  propositionToAdd: Proposition = new Proposition();

  // Propostion to edit
  propositionToEdit: Proposition = new Proposition();

  // List of technologies
  listTechnologies: any = [];

  // List of degrees
  listDegrees: any = [];

  // Visibility of question
  visibilityValue: boolean;

  constructor(private genericService: GenericService, private authenticationService: AuthenticationService) { }

  ngOnInit() {
    this.listTechnologies = this.getTechnologies();
    this.listDegrees = this.getDegrees();
    this.questionToAdd.shared = false;
  }


  /** Create question */
  createQuestions() {
    this.genericService.getUserByUsername('/users/username', this.authenticationService.getUsername())
      .subscribe(userName => {
        this.questionToAdd.createdBy = userName.value;
        this.questionToAdd.activity = userName.value.activity;
        if (isNullOrUndefined(this.questionToAdd.description)) {
          Swal({
            title: 'Erreur!',
            text: 'Vous ne pouvez pas ajouter une question sans sa description',
            type: 'error',
            confirmButtonText: 'Ok'
          });
        } else if (this.questionToAdd.propositions.length < 2) {
          Swal({
            title: 'Erreur!',
            text: 'Vous ne pouvez pas ajouter moins que deux propositions',
            type: 'error',
            confirmButtonText: 'Ok'
          });
        } else {
          this.genericService.createGeneric('/questions', this.questionToAdd)
            .subscribe(data => {
              this.questionToAdd = new Question();
              this.propositionToAdd.description = null;
              if (data.error === false) {
                Swal({
                  position: 'top-end',
                  type: 'success',
                  title: 'Question ajouté avec succés',
                  showConfirmButton: false,
                  timer: 1500
                });
                // reload table data
                this.reloadEvent.emit(null);
                // empty add form
                this.emptyObject();
              } else {
                Swal({
                  title: 'Erreur!',
                  text: data.value,
                  type: 'error',
                  confirmButtonText: 'Ok'
                });
              }
            });
        }
      });
  }

  /*Get all Technologies for select form*/
  getTechnologies() {
    this.genericService.getGenericList('/technologies/all')
      .subscribe(
        data => {
          this.listTechnologies = data;
        });
  }

  /*Get all degrees for select form*/
  getDegrees() {
    this.genericService.getGenericList('/degrees/all')
      .subscribe(
        data => {
          this.listDegrees = data;
        });
  }

  /**Add proposition */
  addProposition(title: any, description: any, valid: any) {
    const proposition = {
      title: title,
      description: description,
      valid: valid
    };
    this.questionToAdd.propositions.push(proposition);
    this.propositionToAdd.description = null;
  }

  /**Edit proposition */
  editProposition(proposition: any) {
    proposition.editMode = false;
    this.propositionToEdit = new Proposition();
    this.propositionToEdit.description = proposition.description;
    this.propositionToEdit.title = proposition.title;
    this.propositionToEdit.valid = proposition.valid;
    const index = this.questionToAdd.propositions.indexOf(proposition);
    this.questionToAdd.propositions.splice(index, 1, this.propositionToEdit);
  }

  /**Delete proposition */
  deleteProposition(proposition: any) {
    this.questionToAdd.propositions.forEach((element, index) => {
      if (proposition.description === element.description) {
        this.questionToAdd.propositions.splice(index, 1);
      }
    });
  }

  /** Empty add form fields */
  emptyObject() {
    this.questionToAdd = new Question();
    this.visibilityValue = false;
  }

  /**Check box action for visibility of the question */
  checkVisibility() {
    if (this.visibilityValue) {
      this.questionToAdd.shared = true;
    } else {
      this.questionToAdd.shared = false;
    }
  }

}
