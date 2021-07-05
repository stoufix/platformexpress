import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedRoutingModule } from './shared-routing.module';
import { FooterComponent } from './template/footer/footer.component';
import { LoaderV1Component } from './loaders/loader-v1/loader-v1.component';
import { NavbarComponent } from './template/navbar/navbar.component';
import { SidebarComponent } from './template/sidebar/sidebar.component';
import { TranslateModule } from '@ngx-translate/core';

@NgModule({
  declarations: [NavbarComponent, SidebarComponent, FooterComponent, LoaderV1Component],
  imports: [
    CommonModule,
    SharedRoutingModule,
    TranslateModule
  ],
  exports: [NavbarComponent, SidebarComponent, FooterComponent, LoaderV1Component]
})
export class SharedModule { }
