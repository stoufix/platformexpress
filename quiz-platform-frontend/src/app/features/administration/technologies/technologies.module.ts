import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TechnologiesRoutingModule } from './technologies-routing.module';
import { TechnologyComponent } from './technology/technology.component';
import { TechnologyAddComponent } from './technology-add/technology-add.component';
import { TechnologyListComponent } from './technology-list/technology-list.component';
import { TechnologyUpdateComponent } from './technology-update/technology-update.component';
import { NgxPaginationModule } from 'ngx-pagination';
import { FormsModule } from '@angular/forms';
import { TranslateModule } from '@ngx-translate/core';

@NgModule({
  declarations: [TechnologyComponent, TechnologyAddComponent, TechnologyListComponent, TechnologyUpdateComponent],
  imports: [
    NgxPaginationModule,
    CommonModule,
    TechnologiesRoutingModule,
    FormsModule,
    TranslateModule
  ]
})
export class TechnologiesModule { }
