import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { InterviewComponent } from './interview/interview.component';
import { InterviewDetailComponent } from './interview-detail/interview-detail.component';

const routes: Routes = [
  { path: '', component: InterviewComponent },
  { path: 'detail/:id', component: InterviewDetailComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class InterviewsRoutingModule { }
