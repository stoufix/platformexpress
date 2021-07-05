import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DegreeComponent } from './degree/degree.component';

const routes: Routes = [
  { path: '', component: DegreeComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DegreesRoutingModule { }
