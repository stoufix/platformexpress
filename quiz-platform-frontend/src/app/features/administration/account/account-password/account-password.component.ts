import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '@services/authentication.service';
import { GenericService } from '@services/generic.service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { User } from '@models/user';
import { LoginUser } from '@models/login-user';

@Component({
  selector: 'app-account-password',
  templateUrl: './account-password.component.html',
  styleUrls: ['./account-password.component.css']
})
export class AccountPasswordComponent implements OnInit {

  userName: string;
  connectedUserInfo: User = new User();
  loginUser: LoginUser = new LoginUser();

  constructor(private authenticationService: AuthenticationService, private genericService: GenericService, private router: Router) { }

  ngOnInit() {
    this.getUsername();
    this.getConnectedUserInfo();
  }


  /** Recover userName form the connected user to send it with getUserInformation request */
  getUsername() {
    this.userName = this.authenticationService.getUsername();
  }

  /** Recover user connected all information(with password) */
  getConnectedUserInfo() {
    this.genericService.getUserByUsername('/users/username', this.userName).subscribe(data => {
      this.connectedUserInfo = data.value;
    });
  }

  /** Update user connected password */
  changeLoginUserPassword() {
    this.loginUser.username = this.connectedUserInfo.username;
    this.genericService.updateLoginUser('/users/password', this.connectedUserInfo.id, this.loginUser).subscribe(data => {
      this.connectedUserInfo = data as User;
      Swal({
        position: 'top-end',
        type: 'success',
        title: 'Information confidentielle modifié avec succés',
        showConfirmButton: false,
        timer: 1500
      }),
        this.authenticationService.logout();
      this.router.navigate(['']);
    });
  }

}
