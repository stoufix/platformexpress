import { Router, ActivatedRoute } from '@angular/router';
import { Component, OnInit, ViewChild } from '@angular/core';
import { Candidate } from '@models/candidate';
import { GenericService } from '@services/generic.service';

@Component({
  selector: 'app-candidate-detail',
  templateUrl: './candidate-detail.component.html',
  styleUrls: ['./candidate-detail.component.css']
})
export class CandidateDetailComponent implements OnInit {

  // Initial candidate id
  idCandidate: number;

  // Initial candidate detail
  candidate: Candidate = new Candidate();

  constructor(private route: ActivatedRoute, private genericService: GenericService) { }

  ngOnInit() {
    this.getCandidate();
  }

  /** Get candidate detail */
  getCandidate() {
    this.idCandidate = this.route.snapshot.params.id;
    this.genericService.getGenericById('/candidates', this.idCandidate).subscribe(data => {
      this.candidate = data.value;
    });
  }
}
