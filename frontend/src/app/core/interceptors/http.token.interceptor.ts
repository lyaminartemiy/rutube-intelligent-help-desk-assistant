import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor, HttpErrorResponse
} from '@angular/common/http';
import {catchError, Observable, throwError} from 'rxjs';
import {RutubeService} from "../services/rutube.service";

@Injectable()
export class HttpTokenInterceptor implements HttpInterceptor {

  constructor(private rutubeService: RutubeService) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (sessionStorage.getItem('token') !== null) {
      req = req.clone({
        setHeaders:{
          Authorization: 'Bearer ' + sessionStorage.getItem('token') as string
        }
      })
    }
    return next.handle(req).pipe(catchError((e: HttpErrorResponse) => {
      if(e.error && e.error.error_code == 1 && (e.status === 401 || e.status === 403)) {
        this.rutubeService.logout();
      }
      return throwError(() => e.message);
    }));
  }
}
