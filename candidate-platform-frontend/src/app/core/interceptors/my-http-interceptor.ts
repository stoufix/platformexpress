import { Injectable } from "@angular/core";
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class MyHttpInterceptor {
    constructor() { }
    getTokenInterceptor(): String {
        var currentCandidate = JSON.parse(localStorage.getItem('currentCandidate'));
        var token = currentCandidate.token;
        return token ? token : "";
    }
    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        req = req.clone({
            setHeaders: {
                'Content-Type': 'application/json; charset=utf-8',
                'Accept': 'application/json',
                'Authorization': `Bearer ${this.getTokenInterceptor()}`,
            },
        });
        return next.handle(req);
    }
}

