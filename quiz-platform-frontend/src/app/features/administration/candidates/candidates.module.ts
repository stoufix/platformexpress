import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CandidatesRoutingModule } from './candidates-routing.module';
import { CandidateComponent } from './candidate/candidate.component';
import { CandidateListComponent } from './candidate-list/candidate-list.component';
import { NgxPaginationModule } from 'ngx-pagination';
import { CandidateDetailComponent } from './candidate-detail/candidate-detail.component';
import { TranslateModule } from '@ngx-translate/core';


@NgModule({
  declarations: [CandidateComponent, CandidateListComponent, CandidateDetailComponent],
  imports: [
    CommonModule,
    CandidatesRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    NgxPaginationModule,
    TranslateModule

  ]
})
export class CandidatesModule { }
