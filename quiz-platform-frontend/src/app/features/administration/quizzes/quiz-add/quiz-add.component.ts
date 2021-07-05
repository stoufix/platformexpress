import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { GenericService } from '@services/generic.service';
import { AuthenticationService } from '@services/authentication.service';
import { Degree } from '@models/degree';
import { Technology } from '@models/technology';
import { Quiz } from '@models/quiz';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-quiz-add',
  templateUrl: './quiz-add.component.html',
  styleUrls: ['./quiz-add.component.css']
})
export class QuizAddComponent implements OnInit {

  // Event for reload table content
  @Output() reloadEvent = new EventEmitter();

  // Quiz to create
  quizToAdd: Quiz = new Quiz();

  // Visibility of quiz
  sharedValue: boolean;

  // List of technologies
  listTechnologies: [Technology];

  // List of degrees
  listDegrees: [Degree];

  constructor(private genericService: GenericService, private authenticationService: AuthenticationService, private router: Router) { }

  ngOnInit() {
    this.getTechnologies();
    this.getDegrees();
    this.quizToAdd.shared = false;
  }


  /** Get all degrees */
  getDegrees() {
    this.genericService.getGenericList('/degrees/all')
      .subscribe(
        data => {
          this.listDegrees = data;
        });
  }

  /** Get all technologies */
  getTechnologies() {
    this.genericService.getGenericList('/technologies/all')
      .subscribe(
        data => {
          this.listTechnologies = data;
        });
  }

  /** Create Quiz */
  createQuiz() {
    this.genericService.getUserByUsername('/users/username', this.authenticationService.getUsername())
      .subscribe(data1 => {
        this.quizToAdd.createdBy = data1.value;
        this.quizToAdd.activity = data1.value.activity;
        this.genericService.createGeneric('/quizzes', this.quizToAdd)
          .subscribe(data => {
            if (data.error === false) {
              // reload table data
              this.reloadEvent.emit(null);
              this.router.navigate(['administration/quizzes/quizAssign', data.value.id]);
            } else {
              Swal({
                title: 'Erreur!',
                text: data.value,
                type: 'error',
                confirmButtonText: 'Ok'
              });
            }
          });
      });
  }

  /** Check if the quiz is shared for other user or not (Check box value) */
  checkShared() {
    if (this.sharedValue) {
      this.quizToAdd.shared = true;
    } else {
      this.quizToAdd.shared = false;
    }
  }

  /** Empty add form fields */
  emptyObject() {
    this.quizToAdd = new Quiz();
    this.sharedValue = false;
  }

}
