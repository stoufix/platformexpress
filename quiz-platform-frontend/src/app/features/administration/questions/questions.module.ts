import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { QuestionsRoutingModule } from './questions-routing.module';
import { QuestionComponent } from './question/question.component';
import { QuestionListComponent } from './question-list/question-list.component';
import { QuestionAddComponent } from './question-add/question-add.component';
import { QuestionUpdateComponent } from './question-update/question-update.component';
import { QuestionDetailComponent } from './question-detail/question-detail.component';
import { FormsModule } from '@angular/forms';
import { EditorModule } from 'primeng/editor';
import { QuestionCloneComponent } from './question-clone/question-clone.component';
import { NgxPaginationModule } from 'ngx-pagination';
import { NgMultiSelectDropDownModule } from 'ng-multiselect-dropdown';
import { QuestionListByUserComponent } from './question-list-by-user/question-list-by-user.component';
import { TranslateModule } from '@ngx-translate/core';



@NgModule({
  declarations: [QuestionComponent, QuestionListComponent, QuestionAddComponent, QuestionUpdateComponent,
    QuestionDetailComponent, QuestionCloneComponent, QuestionListByUserComponent],
  imports: [
    NgxPaginationModule,
    NgMultiSelectDropDownModule.forRoot(),
    CommonModule,
    QuestionsRoutingModule,
    FormsModule,
    EditorModule,
    TranslateModule
  ],
  exports: [QuestionDetailComponent, QuestionCloneComponent]
})
export class QuestionsModule { }
