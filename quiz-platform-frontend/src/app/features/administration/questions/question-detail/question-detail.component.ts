import { Component, OnInit, Input } from '@angular/core';
import { Question } from '@models/question';

@Component({
  selector: 'app-question-detail',
  templateUrl: './question-detail.component.html',
  styleUrls: ['./question-detail.component.css']
})
export class QuestionDetailComponent implements OnInit {

  // Get question to display form question list component
  @Input() questionDetails: Question;

  constructor() { }

  ngOnInit() {
    this.questionDetails = new Question();
  }

}
