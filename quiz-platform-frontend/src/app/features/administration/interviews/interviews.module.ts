import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { InterviewsRoutingModule } from './interviews-routing.module';
import { InterviewComponent } from './interview/interview.component';
import { InterviewListComponent } from './interview-list/interview-list.component';
import { NgxPaginationModule } from 'ngx-pagination';
import { FormsModule } from '@angular/forms';
import { InterviewDetailComponent } from './interview-detail/interview-detail.component';
import { TranslateModule } from '@ngx-translate/core';

@NgModule({
  declarations: [InterviewComponent, InterviewListComponent, InterviewDetailComponent],
  imports: [
    CommonModule,
    InterviewsRoutingModule,
    NgxPaginationModule,
    FormsModule,
    TranslateModule
  ]
})
export class InterviewsModule { }
