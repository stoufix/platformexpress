import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MessagesRoutingModule } from './messages-routing.module';
import { MessageComponent } from './message/message.component';
import { MessageListComponent } from './message-list/message-list.component';
import { NgxPaginationModule } from 'ngx-pagination';
import { MessageDetailComponent } from './message-detail/message-detail.component';
import { FormsModule } from '@angular/forms';
import { TranslateModule } from '@ngx-translate/core';

@NgModule({
  declarations: [MessageComponent, MessageListComponent, MessageDetailComponent],
  imports: [
    CommonModule,
    MessagesRoutingModule,
    NgxPaginationModule,
    FormsModule,
    TranslateModule
  ]
})
export class MessagesModule { }
