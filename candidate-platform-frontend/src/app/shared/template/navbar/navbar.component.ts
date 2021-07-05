import { Component, OnInit } from '@angular/core';
import { Candidate } from '../../models/candidate';
import { AuthenticationService } from 'src/app/core/services/authentication.service';
import { GenericService } from 'src/app/core/services/generic.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  currentCandidate:Candidate =new Candidate();
  username:any;
  detailsCandidate:Candidate = new Candidate();
  constructor(private authenticationService:AuthenticationService, private genericService:GenericService, private router:Router) { }

  ngOnInit() {
   this.getCurrentCandidate();
   this.getCandidateByUsername();
  }

  getCurrentCandidate() {
    this.currentCandidate=JSON.parse(localStorage.getItem('currentCandidate'));
    this.username = this.currentCandidate.username;
  }

  getCandidateByUsername() {
    this.genericService.getCandidateByUsername("/candidates/username", this.username).subscribe(data => {
      this.detailsCandidate = data.object;
    })
  }

  logout(){
    this.authenticationService.logout();
    this.router.navigateByUrl("");
  }

}
