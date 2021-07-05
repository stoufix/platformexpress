import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AlertService } from 'src/app/core/services/alert.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';
import { AuthenticationService } from 'src/app/core/services/authentication.service';
import { GenericService } from 'src/app/core/services/generic.service';

@Component({
  selector: 'app-authentication',
  templateUrl: './authentication.component.html',
  styleUrls: ['./authentication.component.css']
})
export class AuthenticationComponent implements OnInit {
  //Attributes for authentication form
  public username: string = '';
  public password: string = '';
  public message: string = '';
  loading = false;
  error = '';
  loginForm: FormGroup;
  submitted = false;

  constructor(private router: Router, private alertService: AlertService, private loginService: AuthenticationService, private genericService: GenericService, private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });

  }
  // convenience getter for easy access to form fields
  get f() { return this.loginForm.controls; }
  /** Authenticate Candidate */
  login() {
    this.submitted = true;

    // stop here if form is invalid
    if (this.loginForm.invalid) {
      return;
    }
    this.loading = true;

    this.loginService.login(this.f.username.value, this.f.password.value)
      .pipe(first())
      .subscribe(result => {
        if (result) {
          this.router.navigate(['/candidate/rules']);
        }
        else
          this.loading = false;
      }, error => {
        this.loading = false;
      });
  }

}
