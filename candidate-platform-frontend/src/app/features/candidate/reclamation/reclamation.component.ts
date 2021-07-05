import { Component, OnInit } from '@angular/core';
import { Reclamation } from 'src/app/shared/models/reclamation';
import { Candidate } from 'src/app/shared/models/candidate';
import { GenericService } from 'src/app/core/services/generic.service';
import Swal from 'sweetalert2';
import { AuthenticationService } from 'src/app/core/services/authentication.service';


@Component({
  selector: 'app-reclamation',
  templateUrl: './reclamation.component.html',
  styleUrls: ['./reclamation.component.css']
})
export class ReclamationComponent implements OnInit {

//declare object for Activity create
reclamationToAdd: Reclamation = new Reclamation();
username:string;
candidate:Candidate = new Candidate();

  constructor(private genericService: GenericService, private authenticationService:AuthenticationService) { }

  ngOnInit() {
    this.getUsernameConnectedCandidate();
    this.getConnectedCandidate();
  }


  getUsernameConnectedCandidate() {
    this.username = this.authenticationService.getUsername();
  }

  getConnectedCandidate() {
    this.genericService.getCandidateByUsername("/candidates/username",this.username).subscribe(data => {
      this.candidate = data.object;
    })  
  }

  createReclamation() {
    this.reclamationToAdd.candidate=this.candidate;
    this.genericService.createGeneric("/reclamations",this.reclamationToAdd)
    .subscribe(data=>{
      if(data.error===false){
      Swal({
        position: 'top-end',
        type: 'success',
        title: data.message,
        showConfirmButton: false,
        timer: 1500
      }),
      //empty add form
      this.emptyObject(); 
    }
    else{
      Swal({
        title: 'Erreur!',
        text: data.message,
        type: 'error',
        confirmButtonText: 'ok'
      })
    }
  
    }, err => {console.log("create activity erreur: "+err); }) ;
  }

  emptyObject(){
    this.reclamationToAdd = new Reclamation();
  }

}
