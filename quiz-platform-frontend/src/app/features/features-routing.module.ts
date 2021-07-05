import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthenticationComponent } from './authentication/authentication/authentication.component';
import { AdministrationModule } from './administration/administration.module';
import { AuthGuard } from '../core/guards/auth.guard';
import { NotFoundComponent } from '../core/errors/not-found/not-found.component';


const routes: Routes = [
  { path: '', component: AuthenticationComponent },
  { path: 'administration', loadChildren: () => AdministrationModule, canActivate: [AuthGuard] },
  { path: '**', component: NotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FeaturesRoutingModule { }
