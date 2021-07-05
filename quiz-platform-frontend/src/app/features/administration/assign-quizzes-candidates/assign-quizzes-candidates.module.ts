import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AssignQuizzesCandidatesRoutingModule } from './assign-quizzes-candidates-routing.module';
import { AssignQuizCandidateComponent } from './assign-quiz-candidate/assign-quiz-candidate.component';
import { AssignQuizCandidateListComponent } from './assign-quiz-candidate-list/assign-quiz-candidate-list.component';
import { AssignQuizCandidateAddComponent } from './assign-quiz-candidate-add/assign-quiz-candidate-add.component';
import { FormsModule } from '@angular/forms';
import {NgxPaginationModule} from 'ngx-pagination';
import { AssignQuizCandidateUpdateComponent } from './assign-quiz-candidate-update/assign-quiz-candidate-update.component'; 
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { TranslateModule } from '@ngx-translate/core';

@NgModule({
  declarations: [AssignQuizCandidateComponent, AssignQuizCandidateListComponent, AssignQuizCandidateAddComponent, AssignQuizCandidateUpdateComponent],
  imports: [
    NgxPaginationModule,
    CommonModule,
    AssignQuizzesCandidatesRoutingModule,
    FormsModule,
    NgbModule,
    TranslateModule
  ]
})
export class AssignQuizzesCandidatesModule { }
