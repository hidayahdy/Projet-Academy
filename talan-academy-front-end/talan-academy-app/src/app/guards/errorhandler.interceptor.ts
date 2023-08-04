import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
} from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Injectable()
export class ErrorhandlerInterceptor implements HttpInterceptor {
  constructor(private router: Router, private toastr: ToastrService) {}

  intercept(
    request: HttpRequest<unknown>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    return next.handle(request).pipe(
      catchError((err) => {
        console.log(err.status);
        if ([0, 400, 403, 404, 500].indexOf(err.status) !== -1) {
          this.toastr.error(
            'Un problème est survenu. Veuillez réessayer ultérieurement !'
          );
          //this.router.navigateByUrl('/');
        }

        return throwError(err);
      })
    );
  }
}
