import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router } from '@angular/router';
import { AuthenticationService } from '../services/authentication.service';

@Injectable({
  providedIn: 'root',
})
export class RoleGuard implements CanActivate {
  constructor(public auth: AuthenticationService, public router: Router) {}
  canActivate(route: ActivatedRouteSnapshot): boolean {
    const isAuth = this.auth.getRoles()?.includes(route.data.role);
    if (!isAuth) {
      this.router.navigateByUrl('/');
    }
    return isAuth;
  }
  getRole() {
    const token = localStorage.getItem('jwt') as string;
    const tokenPayload = JSON.parse(atob(token.split('.')[1]));
    return tokenPayload.roles[0];
  }
}
