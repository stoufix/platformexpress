import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '@services/authentication.service';
import { User } from '@models/user';
import { LoginUser } from '@models/login-user';
import { GenericService } from '@services/generic.service';
import Swal from 'sweetalert2';
import { isNullOrUndefined } from 'util';


@Component({
  selector: 'app-account-info',
  templateUrl: './account-info.component.html',
  styleUrls: ['./account-info.component.css']
})
export class AccountInfoComponent implements OnInit {

  userName: string;
  connectedUserInfo: User = new User();
  loginUser: LoginUser = new LoginUser();

  // Declaration of variables of unicity control of email and username
  userEmail: String;
  errorMail = false;
  emails: any = [];


  constructor(private authenticationService: AuthenticationService, private genericService: GenericService) { }

  ngOnInit() {
    this.getUsername();
    this.getConnectedUserInfo();
  }


  /** Recover userName form the connected user to send it with getUserInformation request */
  getUsername() {
    this.userName = this.authenticationService.getUsername();
  }

  /** Recover user connected all information */
  getConnectedUserInfo() {
    this.genericService.getUserByUsername('/users/username', this.userName).subscribe(data => {
      this.connectedUserInfo = data.value;
    });
  }

  /** Get E-mails list of existing users */
  getListOfMails() {
    this.genericService.getGenericList('/users/emails')
      .subscribe(data2 => {
        this.emails = data2;
      });
  }

  /** Compare existing emails of users with tapped email */
  onSearchChangeEmail(searchValue: string) {
    this.genericService.getGenericList('/users/emails').subscribe(data2 => {
      this.emails = data2;
      function findEmail(email: string) {
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


  /** Update user connected information */
  updateUserInfo() {
    this.genericService.updateGeneric('/users', this.connectedUserInfo.id, this.connectedUserInfo).subscribe(data => {
      this.connectedUserInfo = data.value as User;
      Swal({
        type: 'success',
        title: data.value,
        showConfirmButton: true,
      }).then(function () {
        location.reload();
      });
    });
  }

}
