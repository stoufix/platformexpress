import { Component } from '@angular/core';
import { User } from '@models/user';

@Component({
  selector: 'app-recruiter',
  templateUrl: './recruiter.component.html',
  styleUrls: ['./recruiter.component.css']
})
export class RecruiterComponent {

  parentData: User;

}
