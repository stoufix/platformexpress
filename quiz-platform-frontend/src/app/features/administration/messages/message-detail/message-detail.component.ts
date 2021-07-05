import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Reclamation } from '@models/reclamation';

@Component({
  selector: 'app-message-detail',
  templateUrl: './message-detail.component.html',
  styleUrls: ['./message-detail.component.css']
})
export class MessageDetailComponent implements OnInit {

  @Input() messageDetails: Reclamation;

  // event for table data
  @Output() reloadEvent = new EventEmitter();

  constructor() { }

  ngOnInit() {
    this.messageDetails = new Reclamation();
  }

  /** Open new mail message with candidate's mail adresse as destination **/
  mailTo(adresse: any) {
    const mail = document.createElement('a');
    mail.href = 'mailto:' + adresse;
    mail.click();
  }


  /** Exit modal event to change message display as readed */
  exitModal() {
    if (this.messageDetails.consulted === false) {
      // reload table data
      this.reloadEvent.emit(null);
    }
  }

}
