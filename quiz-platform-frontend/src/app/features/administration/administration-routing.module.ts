import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RecruitersModule } from './recruiters/recruiters.module';
import { QuizzesModule } from './quizzes/quizzes.module';
import { RolesModule } from './roles/roles.module';
import { QuestionsModule } from './questions/questions.module';
import { DegreesModule } from './degrees/degrees.module';
import { DashboardModule } from './dashboard/dashboard.module';
import { CandidatesModule } from './candidates/candidates.module';
import { ActivitiesModule } from './activities/activities.module';
import { AdministrationComponent } from './administration.component';
import { AssignQuizzesCandidatesModule } from './assign-quizzes-candidates/assign-quizzes-candidates.module';
import { AccountModule } from './account/account.module';
import { MessagesModule } from './messages/messages.module';
import { TechnologiesModule } from './technologies/technologies.module';
import { InterviewsModule } from './interviews/interviews.module';
import { ResultsModule } from './results/results.module';

const routes: Routes = [
  {
    path: '',
    component: AdministrationComponent,
    children: [
      { path: 'dashboard', loadChildren: () => DashboardModule },
      { path: 'candidates', loadChildren: () => CandidatesModule },
      { path: 'interviews', loadChildren: () => InterviewsModule },
      { path: 'messages', loadChildren: () => MessagesModule },
      { path: 'assign-quizzes-candidates', loadChildren: () => AssignQuizzesCandidatesModule },
      { path: 'results', loadChildren: () => ResultsModule },
      { path: 'quizzes', loadChildren: () => QuizzesModule },
      { path: 'questions', loadChildren: () => QuestionsModule },
      { path: 'degrees', loadChildren: () => DegreesModule },
      { path: 'technologies', loadChildren: () => TechnologiesModule },
      { path: 'recruiters', loadChildren: () => RecruitersModule },
      { path: 'roles', loadChildren: () => RolesModule },
      { path: 'activities', loadChildren: () => ActivitiesModule },
      { path: 'account', loadChildren: () => AccountModule }


    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdministrationRoutingModule { }
