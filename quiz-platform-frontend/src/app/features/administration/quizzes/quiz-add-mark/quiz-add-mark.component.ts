import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { QuizQuestion } from '@models/quiz-question';

@Component({
  selector: 'app-quiz-add-mark',
  templateUrl: './quiz-add-mark.component.html',
  styleUrls: ['./quiz-add-mark.component.css']
})
export class QuizAddMarkComponent implements OnInit {

  // Get quizQuestion object from assign-quiz component
  @Input() quizQuestion: QuizQuestion;
  // Event for adding question to list
  @Output() addQuestionEvent = new EventEmitter();
  // Initial mark value of question
  mark: number;

  constructor() { }

  ngOnInit() {
    this.mark = 0.25;
  }


  /**Add question after adding a mark */
  addQuestion() {
    // Call add question to list
    this.quizQuestion.mark = this.mark;
    this.addQuestionEvent.emit(this.quizQuestion);
    this.mark = 0.25;
  }

  /** Empty mark form */
  emptyObject() {
    this.quizQuestion.mark = null;
    this.mark = 0.25;
  }

}
