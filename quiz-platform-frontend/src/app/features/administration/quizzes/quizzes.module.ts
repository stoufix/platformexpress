import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { QuizzesRoutingModule } from './quizzes-routing.module';
import { QuizComponent } from './quiz/quiz.component';
import { QuizListComponent } from './quiz-list/quiz-list.component';
import { QuizAddComponent } from './quiz-add/quiz-add.component';
import { QuizDetailComponent } from './quiz-detail/quiz-detail.component';
import { QuizAssignComponent } from './quiz-assign/quiz-assign.component';
import { FormsModule } from '@angular/forms';
import { EditorModule } from 'primeng/editor';
import { QuestionsModule } from '../questions/questions.module';
import { QuizAddMarkComponent } from './quiz-add-mark/quiz-add-mark.component';
import { NgxPaginationModule } from 'ngx-pagination';
import { NgMultiSelectDropDownModule } from 'ng-multiselect-dropdown';
import { QuizListByUserComponent } from './quiz-list-by-user/quiz-list-by-user.component';
import { NgbTooltipModule, NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { TranslateModule } from '@ngx-translate/core';


@NgModule({
  declarations: [QuizComponent, QuizListComponent, QuizAddComponent, QuizDetailComponent, QuizAssignComponent,
    QuizAddMarkComponent, QuizListByUserComponent],
  imports: [
    CommonModule,
    QuizzesRoutingModule,
    FormsModule,
    EditorModule,
    QuestionsModule,
    NgxPaginationModule,
    NgMultiSelectDropDownModule,
    NgbModule,
    NgbTooltipModule,
    TranslateModule
  ]
})
export class QuizzesModule { }
