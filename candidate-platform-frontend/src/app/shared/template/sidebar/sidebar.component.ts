import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/core/services/authentication.service';
import { GenericService } from 'src/app/core/services/generic.service';
import { Candidate } from '../../models/candidate';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {

  username:string
  candidate:Candidate = new Candidate();

  constructor(private authenticationService:AuthenticationService,private genericService:GenericService) { }

  ngOnInit() {
    this.getConnectedCandidateUsername();
    this.getCandidateInfo();
  }

  getConnectedCandidateUsername() {
    this.username = this.authenticationService.getUsername();
  }

  getCandidateInfo() {
    this.genericService.getCandidateByUsername("/candidates/username",this.username).subscribe(data => {
      this.candidate = data.object;
    })
  }

}
