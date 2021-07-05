import { Component, OnInit } from '@angular/core';
import { GenericService } from '@services/generic.service';
import { ActivatedRoute } from '@angular/router';
import { OnlineInterview } from '@models/online-interview';

@Component({
  selector: 'app-interview-detail',
  templateUrl: './interview-detail.component.html',
  styleUrls: ['./interview-detail.component.css']
})
export class InterviewDetailComponent implements OnInit {

  // Initial onlineInterview id
  idOnlineInterview: number;

  onlineInterview: OnlineInterview = new OnlineInterview();

  constructor(private genericService: GenericService, private route: ActivatedRoute) { }

  ngOnInit() {
    this.getInterviewDetail();
  }

  getInterviewDetail() {
    this.idOnlineInterview = this.route.snapshot.params.id;
    this.genericService.getGenericById('/interviews', this.idOnlineInterview).subscribe(data => {
      if (data.error === false) {
        this.onlineInterview = data.value;
      }
    });
  }

}
