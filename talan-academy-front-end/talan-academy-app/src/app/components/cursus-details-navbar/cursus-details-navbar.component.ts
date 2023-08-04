import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/services/authentication.service';
@Component({
  selector: 'app-cursus-details-navbar',
  templateUrl: './cursus-details-navbar.component.html',
  styleUrls: ['./cursus-details-navbar.component.scss'],
})
export class CursusDetailsNavbarComponent implements OnInit {
  isLoggedIn = false;
  url!: string;
  email?: string;
  role!: string;
  userId!: string;

  constructor(private authenticationService: AuthenticationService) {}

  ngOnInit(): void {
    this.isLoggedIn = !!this.authenticationService.getUser();
    this.role = this.authenticationService.getRoles();
  }
  logout(): void {
    this.authenticationService.logOut();
  }
}
