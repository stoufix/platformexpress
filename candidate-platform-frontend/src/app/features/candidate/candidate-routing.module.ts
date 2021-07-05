import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CandidateComponent } from './candidate.component';
import { QuizzesToCompleteComponent } from './quizzes-to-complete/quizzes-to-complete.component';
import { QuizRulesComponent } from './quiz-rules/quiz-rules.component';
import { ReclamationComponent } from './reclamation/reclamation.component';
import { StartQuizComponent } from './start-quiz/start-quiz.component';

const routes: Routes = [
  {
    path:'',
    component: CandidateComponent,
    children: [
      {path:'quizzes-to-complete', component:QuizzesToCompleteComponent},
      {path:'rules', component:QuizRulesComponent},
      {path:'reclamation',component:ReclamationComponent},
    ]
  }, 
  {path:'start-quiz/:id1/:id2', component:StartQuizComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CandidateRoutingModule { }
