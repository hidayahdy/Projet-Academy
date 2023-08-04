// import { Injectable } from '@angular/core';
// import {
//   ActivatedRouteSnapshot,
//   CanActivate,
//   RouterStateSnapshot,
//   UrlTree,
//   Router,
// } from '@angular/router';
// import { Observable } from 'rxjs';
// import { AuthenticationService } from '../services/authentication.service';

// @Injectable({
//   providedIn: 'root',
// })
// export class CondidatGuard implements CanActivate {
//   constructor(private auth: AuthenticationService, private router: Router) {}
//   canActivate(
//     route: ActivatedRouteSnapshot,
//     state: RouterStateSnapshot
//   ):
//     | Observable<boolean | UrlTree>
//     | Promise<boolean | UrlTree>
//     | boolean
//     | UrlTree {
//     const isCondidat = this.auth.isCondidat();
//     if (isCondidat) {
//       return true;
//     }
//     this.router.navigateByUrl('/');

//     return false;
//   }
// }
