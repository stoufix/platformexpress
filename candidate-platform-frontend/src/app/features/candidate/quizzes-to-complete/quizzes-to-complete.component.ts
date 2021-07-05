import { Component, OnInit } from '@angular/core';
import { Candidate } from 'src/app/shared/models/candidate';
import { GenericService } from 'src/app/core/services/generic.service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-quizzes-to-complete',
  templateUrl: './quizzes-to-complete.component.html',
  styleUrls: ['./quizzes-to-complete.component.css']
})
export class QuizzesToCompleteComponent implements OnInit {

  currentCandidate:Candidate =new Candidate();
  username:any;
  idCandidate:any;
  listOfAssignedQuizzes:any = [];
  listOfOnlineInterviews:any = [];
  constructor(private genericService: GenericService, private router:Router) { }

  ngOnInit() {
    this.getListOfAssignedQuizzes();
  }

  getListOfAssignedQuizzes() {
    this.currentCandidate=JSON.parse(localStorage.getItem('currentCandidate'));
    this.username = this.currentCandidate.username;
    this.genericService.getCandidateByUsername("/candidates/username", this.username).subscribe(data => {
    this.idCandidate = data.object.id;  
    this.genericService.getGenericById("/candidates/onlineInterviews",this.idCandidate).subscribe(data1 => {
    console.log("display data listOfAssignedQuizzes");
    console.log(data1);
    this.listOfOnlineInterviews = data1;
    this.listOfOnlineInterviews.forEach(interview => {
      interview.assignedQuizzes.forEach(assignedQuizz => {
        this.listOfAssignedQuizzes.push(assignedQuizz);
        console.log("display listOfAssignedQuizzes test h");
        console.log(this.listOfAssignedQuizzes);
      }); 
    });
    



    })
  })
  }
  
  startQuiz(id1,id2:number) {
    Swal({
      title: 'Voulez vous commencer ce QCM ?',
      type: 'question',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonText: 'Non',
      confirmButtonText: 'Oui'
    }).then((result) => {
      if (result.value) {
        this.router.navigate(['candidate/start-quiz',id1,id2]);
      }
      else {
      }
    })
  }

}
