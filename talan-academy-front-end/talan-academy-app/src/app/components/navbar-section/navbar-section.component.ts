import {
  BreakpointObserver,
  BreakpointState,
  MediaMatcher,
} from '@angular/cdk/layout';
import {
  ChangeDetectorRef,
  Component,
  OnInit,
  Output,
  EventEmitter,
  OnDestroy,
} from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { User } from 'src/app/models/user.model';
import { ApplicationsService } from 'src/app/services/applications.service';
import { AuthenticationService } from 'src/app/services/authentication.service';
@Component({
  selector: 'app-navbar-section',
  templateUrl: './navbar-section.component.html',
  styleUrls: ['./navbar-section.component.scss'],
})
export class NavbarSectionComponent implements OnInit {
  @Output() public sidenavToggle = new EventEmitter();
  show!: boolean;
  id!: any;
  mobileQuery: MediaQueryList;
  isLoggedIn = false;
  role!: string;
  start!: boolean;
  user !: User ; 
  nbrJour !: number ;
  verifStatut: boolean;
  private _mobileQueryListener: () => void;
  constructor(
    private router: Router,
    private breakpointObserver: BreakpointObserver,
    changeDetectorRef: ChangeDetectorRef,
    media: MediaMatcher,
    private authenticationService: AuthenticationService,
    private applicationService: ApplicationsService,
    private toastr: ToastrService
  ) {
    this.mobileQuery = media.matchMedia('(max-width: 600px)');
    this._mobileQueryListener = () => changeDetectorRef.detectChanges();
    this.mobileQuery.addListener(this._mobileQueryListener);
  }
  ngOnInit(): void {
    this.id = localStorage.getItem('user_id');

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
    }
    this.applicationService
      .verifStatutApplication(this.id)
      .subscribe((data) => {
        this.verifStatut = data;
      });
      this.authenticationService.getUserById(this.id).subscribe((data) => {
        let dates: Date = new Date(data.session.startDate);
        this.nbrJour = this.date(dates) ;
        this.nbrJour < 0 ? (this.start = true) : (this.start = false);
       this.user = data;
       console.log(this.user);
       
      });
  }
  public onToggleSidenav = () => {
    this.sidenavToggle.emit();
  };

  ngOnDestroy(): void {
    this.mobileQuery.removeListener(this._mobileQueryListener);
  }
  logout(): void {
    this.authenticationService.logOut();
  }

  redirection(): void {
    this.router.navigateByUrl('/');
  }

  verifStartLesson(): void {
    this.start
    ? this.router.navigateByUrl(`/apprenti/cursus/${this.user.session.cursus.id}`)
    : this.toastr.info(`Votre session commence dans ${this.nbrJour} jours`);
           
  }

  date(date1: Date): number {
    var date2 = Date.now();
    var time_diff = date1.getTime() -date2  ;
    var days_Diff = time_diff / (1000 * 3600 * 24);
    return Math.round(days_Diff);
  }

  verifStatutRegistred(): void {
    this.applicationService
      .verifStatutApplication(this.id)
      .subscribe((data) => {
        data
          ? this.router.navigateByUrl('/candidat/add')
          : this.toastr.error('Vous avez déjà une candidature en cours !');
      });
  }
}
