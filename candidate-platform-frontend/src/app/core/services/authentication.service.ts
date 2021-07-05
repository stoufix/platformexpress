import { Injectable, EventEmitter } from '@angular/core';
import { Http, Headers,  Response } from "@angular/http";
import { Observable} from 'rxjs';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {AlertService} from './alert.service';



@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private service = 'http://localhost:8090/token/generate-token';
  private emitter = new EventEmitter(false);
  private headers = new Headers({'Content-Type': 'application/json'});
  constructor(private http: Http,private alertService: AlertService) { }
  
  login(username, password){
    return this.http.post(this.service, JSON.stringify({username: username, password: password}), {headers: this.headers})
        .map((response: Response) => {
          if(!(response.json().error)){
                // login successful if there's a jwt token in the response
                let token = response.json() && response.json().message;
                
                    // store username and jwt token in local storage to keep user logged in between page refreshes
                    localStorage.setItem('currentCandidate', JSON.stringify({ username: username, token: token }));
                    return true;
          }
          else{
            this.alertService.error(response.json().message);
            return false;
          }
            
        }
        ).catch((error:any) => Observable.throw(error.json().error || 'Server error'));
}


getToken(): String {
  var currentCandidate = JSON.parse(localStorage.getItem('currentCandidate'));
  var token = currentCandidate && currentCandidate.token;
  return token ? token : "";
}


logout(): void {
    // clear token remove user from local storage to log user out
    localStorage.removeItem('currentCandidate');
}


getTokenLog(): String {
    var currentCandidate = JSON.parse(localStorage.getItem('currentCandidate'));
    var token = currentCandidate && currentCandidate.token;
    return token ? token : "";
  }


isLoggedIn(): boolean {
    var token: String = this.getTokenLog();
    return token && token.length > 0;
  }


getRole():String {
    var currentCandidate = JSON.parse(localStorage.getItem('currentCandidate'));
    let jwtData = currentCandidate.token.split('.')[1];
    let decodedJwtJsonData = window.atob(jwtData);
    let decodedJwtData = JSON.parse(decodedJwtJsonData);
    let role = decodedJwtData.scopes;
    return role;
  }


  getUsername():string{
    var currentCandidate = JSON.parse(localStorage.getItem('currentCandidate'));

    let jwtData = currentCandidate.token.split('.')[1];
    let decodedJwtJsonData = window.atob(jwtData);
    let decodedJwtData = JSON.parse(decodedJwtJsonData);
    let username = decodedJwtData.sub;
    return username;
   
  }

}
