import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FeaturesRoutingModule } from './features-routing.module';
import { AuthenticationComponent } from './authentication/authentication/authentication.component';
import { AuthenticationAlertComponent } from './authentication/authentication-alert/authentication-alert.component';
import { ErrorsModule } from '../core/errors/errors.module';
import { FormsModule, ReactiveFormsModule} from '@angular/forms';
import {StepsModule} from 'primeng/steps';
import {MenuItem} from 'primeng/api';



@NgModule({
  declarations: [AuthenticationComponent, AuthenticationAlertComponent],
  imports: [
    CommonModule,
    FeaturesRoutingModule,
    ErrorsModule, 
    FormsModule,
    ReactiveFormsModule,
    StepsModule
  ]
})
export class FeaturesModule { }
