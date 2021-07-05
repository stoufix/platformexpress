import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DegreesRoutingModule } from './degrees-routing.module';
import { DegreeComponent } from './degree/degree.component';
import { DegreeListComponent } from './degree-list/degree-list.component';
import { DegreeAddComponent } from './degree-add/degree-add.component';
import { DegreeUpdateComponent } from './degree-update/degree-update.component';
import { FormsModule } from '@angular/forms';
import { NgxPaginationModule } from 'ngx-pagination';
import { TranslateModule } from '@ngx-translate/core';

@NgModule({
  declarations: [DegreeComponent, DegreeListComponent, DegreeAddComponent, DegreeUpdateComponent],
  imports: [
    CommonModule,
    DegreesRoutingModule,
    FormsModule,
    NgxPaginationModule,
    TranslateModule
  ]
})
export class DegreesModule { }
