import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpModule } from '@angular/http';
import {HttpClientModule, HTTP_INTERCEPTORS} from '@angular/common/http'
import { AuthenticationService } from './core/services/authentication.service';
import { GenericService } from './core/services/generic.service';
import { AlertService } from './core/services/alert.service';
import { AuthGuard } from './core/guards/auth.guard';
import { MyHttpInterceptor } from './core/interceptors/my-http-interceptor';



@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpModule,
    HttpClientModule
  ],
  providers: [
    GenericService, 
    AuthenticationService,
    AlertService,
    AuthGuard,
    MyHttpInterceptor, {
      provide : HTTP_INTERCEPTORS,
      useClass: MyHttpInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
