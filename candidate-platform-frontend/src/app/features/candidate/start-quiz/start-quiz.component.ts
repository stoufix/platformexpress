import { Component, OnInit, HostListener, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import swal from 'sweetalert2';
import { GenericService } from 'src/app/core/services/generic.service';
import { Quiz } from 'src/app/shared/models/quiz';
import { QuizQuestion } from 'src/app/shared/models/quiz-question';
import { Proposition } from 'src/app/shared/models/proposition';
import { AnsweredProposition } from '../../../shared/models/answered-proposition';
import { PassedQuestion } from '../../../shared/models/passed-question';
import { OnlineInterviewQuizResult } from '../../../shared/models/online-interview-quiz-result';

@Component({
  selector: 'app-start-quiz',
  templateUrl: './start-quiz.component.html',
  styleUrls: ['./start-quiz.component.css'],
  encapsulation: ViewEncapsulation.None

})

export class StartQuizComponent implements OnInit {
  idQuiz: number;
  quizToStart: Quiz;
  count: number = 0;
  listOfQuestions: QuizQuestion[];
  listOfPropositions: any = [];
  currentDescriptionQuestion: String;
  lengthOfListQuestions: number;
  minutes: number;
  seconds: number;
  isPaused: boolean;
  buttonLabel: string;
  questionToPass: PassedQuestion;
  onlineInterviewQuizResult: OnlineInterviewQuizResult = new OnlineInterviewQuizResult();
  idOnlineInterview: any;
  checkedValue: boolean = false;
  listPassedQuestions: any = [];
  listOfAnsweredPropositions: any = [];
  countPrev: number;
  listCheckedQuestion: boolean[] = [];
  errorCount: number = 0;
  errorMessage: String;
  validQuestion: boolean;
  lengthAnsweredProposition: number;

  constructor(private route: ActivatedRoute, private genericService: GenericService, private router: Router) { }

  ngOnInit() {
    this.getStartQuiz();
    this.togglePause();

  }


  //to control changing browser tab
  @HostListener('window:focus', ['$event'])
  onFocus(event: FocusEvent): void {
    console.log("good");
  }
  @HostListener('window:blur', ['$event'])
  onBlur(event: FocusEvent): void {
    this.errorCount++;
    if (this.errorCount == 1) {
      swal({
        title: 'Erreur!',
        text: "Hey! un événement inhabituel est détecté",
        type: 'error',
        confirmButtonText: 'ok'
      })
    }
    else if (this.errorCount > 1) {
      this.errorMessage = "Ce candidat a consulté des ressources externes.";
      swal({
        title: 'Erreur!',
        text: "Hey! Suite à vos deux événements inhabituels, vous ne pouvez pas completer ce QCM",
        type: 'error',
        confirmButtonText: 'ok'
      })
      this.saveQuizResult();
    }
  }

  //disable paste action in the page
  @HostListener('paste', ['$event']) blockPaste(e: KeyboardEvent) {
    e.preventDefault();
  }
  //disable copy action in the page
  @HostListener('copy', ['$event']) blockCopy(e: KeyboardEvent) {
    e.preventDefault();
  }
  //disable cut action in the page
  @HostListener('cut', ['$event']) blockCut(e: KeyboardEvent) {
    e.preventDefault();
  }

  //update style of steps after passing question
  getColor(i: number): String {
    if (i == this.count) {
      return 'red';
    }
    else if (this.listCheckedQuestion[i]) {
      return 'green';
    }
  }

  fillCheckedQuestion() {
    let lengthQuiz: number = this.listPassedQuestions.length;
    for (var _i = 0; _i < lengthQuiz; _i++) {
      this.listCheckedQuestion[_i] = false;
    }
  }

  //get current question after clicking "steps"
  changeQuestion(i: number) {
    this.countPrev = this.count;
    this.count = i;
    this.currentDescriptionQuestion = this.listPassedQuestions[this.count].description;
    this.listOfPropositions = this.listPassedQuestions[this.count].answeredProposition;
  }

  // Check if the question is anwsered after clicking "steps"
  ifAnsweredQuestionSteppers() {
    this.checkedValue = false;
    for (let answeredProposition of this.listPassedQuestions[this.countPrev].answeredProposition) {
      if (answeredProposition.checked) {
        this.checkedValue = true;
        break;
      }
    }
    if (this.checkedValue) {
      this.listCheckedQuestion[this.countPrev] = true;
    }
    else {
      this.listCheckedQuestion[this.countPrev] = false;
    }
  }

  // Check if the question is anwsered after clicking "Précédent" and "Suivant"
  ifAnsweredQuestion(i: number) {
    this.checkedValue = false;
    for (let answeredProposition of this.listPassedQuestions[i].answeredProposition) {
      if (answeredProposition.checked) {
        this.checkedValue = true;
        break;
      }
    }
    if (this.checkedValue) {
      this.listCheckedQuestion[i] = true;
    }
    else {
      this.listCheckedQuestion[i] = false;
    }
  }

  // start online interview 
  getStartQuiz() {
    this.idQuiz = this.route.snapshot.params.id1;
    this.idOnlineInterview = this.route.snapshot.params.id2;
    this.genericService.getGenericById("/candidates/start-quiz", this.idQuiz).subscribe(data => {
      if (data.error === false) {
        this.quizToStart = data.object;
        this.startTimer();
        this.listOfQuestions = this.quizToStart.questions;
        this.listOfQuestions.forEach(element => {
          var startPassedQuestion = new PassedQuestion();
          startPassedQuestion.description = element.question.description;
          element.question.propositions.forEach(proposition => {
            var answeredProposition = new AnsweredProposition();
            answeredProposition.description = proposition.description;
            answeredProposition.checked = false;
            answeredProposition.valid = proposition.valid;
            startPassedQuestion.answeredProposition.push(answeredProposition);
          });
          this.listPassedQuestions.push(startPassedQuestion);

        });
        this.onlineInterviewQuizResult = new OnlineInterviewQuizResult();
        this.onlineInterviewQuizResult.note = 0;
        this.onlineInterviewQuizResult.passedQuestions = [];
        this.getQuestion();
        this.fillCheckedQuestion();
      }
      else {
        swal({
          title: 'Erreur!',
          text: data.message,
          type: 'error',
          confirmButtonText: 'ok'
        })
      }
    })
  }

  //get current question after clicking "Précédent" and "Suivant" 
  getQuestion(direction?: string): void {
    this.lengthOfListQuestions = this.listPassedQuestions.length;
    if (direction == 'next') this.count++;
    if (direction == 'prev') this.count--;

    if (this.count > 0) {
      this.questionToPass = this.listPassedQuestions[this.count - 1];
      this.listOfPropositions = this.questionToPass.answeredProposition;
    }
    this.currentDescriptionQuestion = this.listPassedQuestions[this.count].description;
    this.listOfPropositions = this.listPassedQuestions[this.count].answeredProposition;
  }

  // update  status of answered proposition after checking it 
  updateStatusOfAnsweredProposition(e, proposition: Proposition) {
    this.listOfAnsweredPropositions = this.listOfPropositions;
    if (e.target.checked) {
      this.listOfAnsweredPropositions.forEach(answeredProposition => {
        if (proposition.description == answeredProposition.description) {
          answeredProposition.checked = true;
          console.log(this.listOfPropositions);
        }
      });
    }
    else {
      this.listOfAnsweredPropositions.forEach(answeredProposition => {
        if (answeredProposition.description == proposition.description) {
          answeredProposition.checked = false;
        }
      });
    }
    this.listOfPropositions = [];
    this.listOfAnsweredPropositions.forEach(element => {
      this.listOfPropositions.push(element);
    });
  }

  // calculate online interview note by addding passed question mark
  buildPassedQuestion() {
    this.listPassedQuestions.forEach(passedQuestion => {
      for (let answeredProposition of passedQuestion.answeredProposition) {
        if ((answeredProposition.checked == true && answeredProposition.valid == true) || (answeredProposition.checked == false && answeredProposition.valid == false)) {
          this.validQuestion = true;
        }
        else {
          this.validQuestion = false;
          break;
        }
      }
      if (this.validQuestion) {
        this.listOfQuestions.forEach(quizQuestion => {
          if (quizQuestion.question.description == passedQuestion.description) {
            this.onlineInterviewQuizResult.note = this.onlineInterviewQuizResult.note + quizQuestion.mark;
          }
        });
      }
    });
  }

  // save online interview result
  saveQuizResult() {
    this.buildPassedQuestion();
    this.onlineInterviewQuizResult.id.quizId = this.idQuiz;
    this.onlineInterviewQuizResult.id.onlineInterviewId = this.idOnlineInterview;
    this.onlineInterviewQuizResult.type = "technique";
    this.onlineInterviewQuizResult.comments = this.errorMessage;
    this.onlineInterviewQuizResult.duration = (this.quizToStart.duration) - (this.togglePause());
    this.listPassedQuestions.forEach(passedQuestion => {
      this.onlineInterviewQuizResult.passedQuestions.push(passedQuestion);
    });
    console.log(this.onlineInterviewQuizResult);
    this.genericService.createGeneric("/onlineInterviewQuizResult", this.onlineInterviewQuizResult).subscribe(data => {
      if (data.error === false) {
        this.router.navigate(['/candidate/quizzes-to-complete']);
      }
    })
  }

  startTimer() {
    this.resetPomodoro();
    setInterval(() => this.tick(), 1000);
  }

  tick(): void {
    if (!this.isPaused) {
      this.buttonLabel = 'Pause';
      if (--this.seconds < 0) {
        this.seconds = 59;
        if (--this.minutes < 0) {
          this.resetPomodoro();
        }
      }
    }
  }

  resetPomodoro(): void {
    //this.isPaused = false;
    this.minutes = this.quizToStart.duration;
    this.seconds = 0;
    this.buttonLabel = 'Start';
    this.togglePause();
  }

  togglePause() {
    this.isPaused = !this.isPaused;
    if (this.minutes < 59 || this.seconds < 59) {
      this.buttonLabel = this.isPaused ? 'Resume' : 'Pause';
    }
    return (this.minutes);
  }

}