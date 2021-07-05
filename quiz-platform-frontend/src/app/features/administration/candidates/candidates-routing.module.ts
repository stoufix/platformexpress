import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CandidateComponent } from './candidate/candidate.component';
import { CandidateDetailComponent } from './candidate-detail/candidate-detail.component';


const routes: Routes = [
  { path: '', component: CandidateComponent },
  { path: 'candidate-detail/:id', component: CandidateDetailComponent }
];



@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CandidatesRoutingModule { }
