import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { GenericService } from '@services/generic.service';
import { User } from '@models/user';
import { isNullOrUndefined } from 'util';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-recruiter-update',
  templateUrl: './recruiter-update.component.html',
  styleUrls: ['./recruiter-update.component.css']
})

export class RecruiterUpdateComponent implements OnInit {

  // Get User to edit form users list component
  @Input() recruiterToEdit: User;

  // Event for reload table content after update
  @Output() reloadEvent = new EventEmitter();

  // Declaration of variables of unicity control of email and username
  userEmail: String;
  errorMail = false;
  emails: any = [];

  // List of roles and activities for user update
  listOfRoles: any = [];
  listOfRolesWithoutAdmin: any = [];
  listOfActivities: any = [];

  constructor(private genericService: GenericService) { }

  ngOnInit() {
    this.recruiterToEdit = new User();
    this.getListOfRoles();
    this.getListOfActivities();
  }


  /** Get E-mails list of existing users */
  getListOfMails() {
    this.genericService.getGenericList('/users/emails')
      .subscribe(data2 => {
        this.emails = data2;
      });
  }

  /** Get all roles **/
  getListOfRoles() {
    this.genericService.getGenericList('/roles/all').subscribe(data => {
      this.listOfRoles = data;
      // Retrieves Admin role
      this.listOfRoles.forEach(role => {
        if (role.title !== 'Admin') {
          this.listOfRolesWithoutAdmin.push(role);
        }
      });
    });
  }

  /** Get all activities **/
  getListOfActivities() {
    this.genericService.getGenericList('/activities/all').subscribe(data => {
      this.listOfActivities = data;
    });
  }

  /** Update User */
  updateRecruiter() {
    this.genericService.getGenericById('/users', this.recruiterToEdit.id).subscribe(data => {
      const indexEmail = this.emails.indexOf(data.value.email);
      this.emails.splice(indexEmail, 1);
      this.genericService.updateGeneric('/users', this.recruiterToEdit.id, this.recruiterToEdit).subscribe(
        updateData => {
          if (updateData.error === false) {
            Swal({
              position: 'top-end',
              type: 'success',
              title: updateData.value,
              showConfirmButton: false,
              timer: 1500
            });
            this.reloadEvent.emit(null);
          } else {
            Swal({
              title: 'Erreur!',
              text: updateData.value,
              type: 'error',
              confirmButtonText: 'ok'
            });
          }
        }
      );
    });
  }

  /** Compare existing emails of users with tapped email */
  onSearchChangeEmail(searchValue: string) {
    this.genericService.getGenericList('/users/emails').subscribe(data2 => {
      this.emails = data2;
      function findEmail(email) {
        return email === searchValue;
      }
      this.userEmail = this.emails.find(findEmail);
      if (isNullOrUndefined(this.userEmail)) {
        this.errorMail = false;
      } else {
        this.errorMail = true;
      }
    });
  }

}
