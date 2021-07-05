import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { QuizComponent } from './quiz/quiz.component';
import { QuizAssignComponent } from './quiz-assign/quiz-assign.component';

const routes: Routes = [
  { path: '', component: QuizComponent },
  { path: 'quizAssign/:id', component: QuizAssignComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class QuizzesRoutingModule { }
