import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';

import { AuthenticationService } from '../services/authentication.service';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate {
 

  constructor(private auth: AuthenticationService, private router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ):

    | Observable<boolean | UrlTree>

    | Promise<boolean | UrlTree>

    | boolean

    | UrlTree {

    const isconnected = this.auth. isConnected();
    if (isconnected) {
      return true;
    }
    this.router.navigateByUrl('/login');
    return false;

  }
}
