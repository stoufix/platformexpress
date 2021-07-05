import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AssignQuizCandidateComponent } from './assign-quiz-candidate/assign-quiz-candidate.component';
import { AssignQuizCandidateAddComponent } from './assign-quiz-candidate-add/assign-quiz-candidate-add.component';
import { AssignQuizCandidateUpdateComponent } from './assign-quiz-candidate-update/assign-quiz-candidate-update.component';

const routes: Routes = [
  {path:'',component:AssignQuizCandidateComponent},
  {path:'add',component:AssignQuizCandidateAddComponent},
  {path:'update/:id',component:AssignQuizCandidateUpdateComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AssignQuizzesCandidatesRoutingModule { }
