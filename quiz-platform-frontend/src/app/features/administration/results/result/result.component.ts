import { Component, OnInit } from '@angular/core';
import { OnlineInterviewQuizResult } from '@models/online-interview-quiz-result';

@Component({
  selector: 'app-result',
  templateUrl: './result.component.html',
  styleUrls: ['./result.component.css']
})
export class ResultComponent implements OnInit {

  resultDetails: OnlineInterviewQuizResult;

  constructor() { }

  ngOnInit() {
  }

}
