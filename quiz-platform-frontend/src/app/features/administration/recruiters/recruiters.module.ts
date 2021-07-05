import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RecruitersRoutingModule } from './recruiters-routing.module';
import { RecruiterComponent } from './recruiter/recruiter.component';
import { RecruiterListComponent } from './recruiter-list/recruiter-list.component';
import { RecruiterAddComponent } from './recruiter-add/recruiter-add.component';
import { RecruiterUpdateComponent } from './recruiter-update/recruiter-update.component';
import { FormsModule } from '@angular/forms';
import { NgxPaginationModule } from 'ngx-pagination';
import { SharedModule } from 'src/app/shared/shared.module';
import { TranslateModule } from '@ngx-translate/core';

@NgModule({
  declarations: [RecruiterComponent, RecruiterListComponent, RecruiterAddComponent, RecruiterUpdateComponent],
  imports: [
    CommonModule,
    RecruitersRoutingModule,
    FormsModule,
    NgxPaginationModule,
    SharedModule,
    TranslateModule
  ]
})
export class RecruitersModule { }
