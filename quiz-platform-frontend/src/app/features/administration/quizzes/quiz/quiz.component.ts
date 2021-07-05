import { Component, OnInit } from '@angular/core';
import { Quiz } from '@models/quiz';

@Component({
  selector: 'app-quiz',
  templateUrl: './quiz.component.html',
  styleUrls: ['./quiz.component.css']
})
export class QuizComponent implements OnInit {

  quizDetails: Quiz;

  // View type(list= true & grid = false)
  listView: any = true;

  constructor() { }

  ngOnInit() {
  }

  /** Change view type between list and grid */
  changeView() {
    if (this.listView) {
      this.listView = false;
    } else {
      this.listView = true;
    }
  }

}
