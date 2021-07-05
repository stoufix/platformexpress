import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AccountRoutingModule } from './account-routing.module';
import { AccountInfoComponent } from './account-info/account-info.component';
import { AccountPasswordComponent } from './account-password/account-password.component';
import { AccountComponent } from './account/account.component';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [AccountInfoComponent, AccountPasswordComponent, AccountComponent],
  imports: [
    CommonModule,
    AccountRoutingModule,
    FormsModule
  ]
})
export class AccountModule { }
