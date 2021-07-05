import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { GenericService } from '@services/generic.service';
import { AuthenticationService } from '@services/authentication.service';
import { Router } from '@angular/router';
import { Question } from '@models/question';
import { Proposition } from '@models/proposition';
import { QuizQuestion } from '@models/quiz-question';

@Component({
  selector: 'app-question-clone',
  templateUrl: './question-clone.component.html',
  styleUrls: ['./question-clone.component.css']
})
export class QuestionCloneComponent implements OnInit {

  // Get Question to clone from other components
  @Input() questionToClone: Question = new Question();

  // Event for clone question
  @Output() cloneQuestionEvent = new EventEmitter();

  // Proposition to clone
  propositionClone: Proposition = new Proposition();

  // Question to create
  questionToAdd: Question = new Question();

  // Proposition of question to clone
  proposition: Proposition = new Proposition();

  // QuizQuestion object to clone
  quizQuestionToClone: QuizQuestion;

  // Visibility of the question
  visibilityValue: boolean;

  constructor(private genericService: GenericService, private authenticationService: AuthenticationService) { }

  ngOnInit() {
    this.quizQuestionToClone = new QuizQuestion();
  }


  /** Clone question */
  cloneQuestion() {
    this.genericService.getUserByUsername('/users/username', this.authenticationService.getUsername())
      .subscribe(data1 => {
        this.questionToClone.createdBy = data1.value;
        this.questionToAdd.activity = data1.value.activity;
        const arr2 = this.questionToClone.propositions;
        this.questionToClone.propositions = [];
        arr2.forEach(p => {
          this.propositionClone.title = p.title;
          this.propositionClone.description = p.description;
          this.propositionClone.valid = p.valid;
          this.questionToClone.propositions.push(this.propositionClone);
          this.propositionClone = new Proposition;
        });
        this.questionToAdd = this.questionToClone;
        this.questionToAdd.id = null;
        this.quizQuestionToClone.question = this.questionToAdd;
        // create question to clone
        this.cloneQuestionEvent.emit(this.quizQuestionToClone);
      });
  }

  /**Create proposition */
  addPropositionClone(title, description, valid) {
    const propositionClone = {
      title: title,
      description: description,
      valid: valid
    };
    this.questionToClone.propositions.push(propositionClone);
    this.propositionClone.description = null;
  }

  /**Edit proposition */
  editPropositionCloneQuestion(proposition) {
    proposition.editMode = false;
    this.proposition = new Proposition();
    this.proposition.description = proposition.description;
    this.proposition.title = proposition.title;
    this.proposition.valid = proposition.valid;
    const index = this.questionToClone.propositions.indexOf(proposition);
    this.questionToClone.propositions.splice(index, 1, this.proposition);
  }

  /**Delete proposition */
  deletePropositionsCloneQuestion(proposition) {
    this.questionToClone.propositions.forEach((element, index) => {
      if (proposition.description === element.description) {
        this.questionToClone.propositions.splice(index, 1);
      }
    });
  }

  /**Check box action for visibility of the question when creating a new one */
  checkVisibility() {
    if (this.visibilityValue) {
      this.questionToClone.shared = true;
    } else {
      this.questionToClone.shared = false;
    }
  }

  /**Empty clone form */
  emptyObjectClone() {
    this.quizQuestionToClone.mark = null;
  }

}
