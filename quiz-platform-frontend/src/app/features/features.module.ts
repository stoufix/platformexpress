import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FeaturesRoutingModule } from './features-routing.module';
import { AuthenticationComponent } from './authentication/authentication/authentication.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AuthenticationAlertComponent } from './authentication/authentication-alert/authentication-alert.component';
import { NotFoundComponent } from '../core/errors/not-found/not-found.component';

@NgModule({
  declarations: [AuthenticationComponent, AuthenticationAlertComponent, NotFoundComponent],
  imports: [
    CommonModule,
    FeaturesRoutingModule,
    FormsModule,
    ReactiveFormsModule,

  ],
  exports: [AuthenticationAlertComponent]
})
export class FeaturesModule { }
