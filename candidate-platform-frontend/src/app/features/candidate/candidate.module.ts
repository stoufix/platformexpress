import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CandidateRoutingModule } from './candidate-routing.module';
import { ReclamationComponent } from './reclamation/reclamation.component';
import { QuizRulesComponent } from './quiz-rules/quiz-rules.component';
import { QuizzesToCompleteComponent } from './quizzes-to-complete/quizzes-to-complete.component';
import { StartQuizComponent } from './start-quiz/start-quiz.component';
import { CandidateComponent } from './candidate.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { FormsModule } from '@angular/forms';
import {StepsModule} from 'primeng/steps';
import {ToastModule} from 'primeng/toast';
import {MessageService} from 'primeng/api';
import {AccordionModule} from 'primeng/accordion'; 
@NgModule({
  declarations: [ReclamationComponent, QuizRulesComponent, QuizzesToCompleteComponent, StartQuizComponent, CandidateComponent],
  providers: [MessageService],
  imports: [
    CommonModule,
    CandidateRoutingModule,
    SharedModule,
    FormsModule,
    ToastModule,
    StepsModule,
    AccordionModule,  
 

  ]
})
export class CandidateModule { }
