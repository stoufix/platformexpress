import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthenticationComponent } from './authentication/authentication/authentication.component';
import { CandidateModule } from './candidate/candidate.module';
import { PageNotFoundComponent } from '../core/errors/page-not-found/page-not-found.component';
import { AuthGuard } from '../core/guards/auth.guard';

const routes: Routes = [
  {path:'', component: AuthenticationComponent},
  {path:'candidate', loadChildren: () => CandidateModule, canActivate: [AuthGuard]},
  {path:'**',component:PageNotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FeaturesRoutingModule { }
