import {
  BreakpointObserver,
  BreakpointState,
  MediaMatcher,
} from '@angular/cdk/layout';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-test',
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.scss'],
})
export class TestComponent implements OnInit {
  mobileQuery: MediaQueryList;
  private _mobileQueryListener: () => void;
  isLoggedIn = false;
  aa: string;
  role!: string;
  options = this._formBuilder.group({
    bottom: 0,
    fixed: false,
    top: 0,
  });
  events: string[] = [];
  opened!: boolean;
  show!: boolean;
  list: boolean = false;
  constructor(
    changeDetectorRef: ChangeDetectorRef,
    media: MediaMatcher,
    private _formBuilder: FormBuilder,
    private authenticationService: AuthenticationService,
    private breakpointObserver: BreakpointObserver,
    private router: Router
  ) {
    this.mobileQuery = media.matchMedia('(max-width: 600px)');
    this._mobileQueryListener = () => changeDetectorRef.detectChanges();
    this.mobileQuery.addListener(this._mobileQueryListener);
  }

  ngOnInit() {
    this.isLoggedIn = !!this.authenticationService.getUser();
    this.role = this.authenticationService.getRoles();
    this.breakpointObserver
      .observe(['(max-width: 959px)'])
      .subscribe((state: BreakpointState) => {
        this.show = false;
        if (state.matches) {
          this.show = true;
        } 
      });
    if (this.router.url.includes('apprenti/cursus')) {
      this.show = true;
      this.list = true;
    }
  }

  ngOnDestroy(): void {
    this.mobileQuery.removeListener(this._mobileQueryListener);
  }
  logout(): void {
    this.authenticationService.logOut();
  }
}
