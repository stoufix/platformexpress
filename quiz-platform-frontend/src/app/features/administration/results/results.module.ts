import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ResultsRoutingModule } from './results-routing.module';
import { ResultComponent } from './result/result.component';
import { ResultListComponent } from './result-list/result-list.component';
import { ResultDetailComponent } from './result-detail/result-detail.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgxPaginationModule } from 'ngx-pagination';
import { TranslateModule } from '@ngx-translate/core';

@NgModule({
  declarations: [ResultComponent, ResultListComponent, ResultDetailComponent],
  imports: [
    CommonModule,
    ResultsRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    NgxPaginationModule,
    TranslateModule
  ]
})
export class ResultsModule { }
