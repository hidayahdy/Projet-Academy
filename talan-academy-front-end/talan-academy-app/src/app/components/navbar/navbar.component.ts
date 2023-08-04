import { Component, OnInit } from '@angular/core';

import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
})
export class NavbarComponent implements OnInit {
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
