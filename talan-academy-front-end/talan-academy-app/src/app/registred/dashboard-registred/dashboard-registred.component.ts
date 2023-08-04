import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Session } from 'src/app/models/session.model';
import { Cursus } from 'src/app/models/cursus.model';
import { SessionServiceService } from 'src/app/services/session-service.service';
import { Observable } from 'rxjs';
import { CursusPublicService } from 'src/app/services/cursus-public.service';

@Component({
  selector: 'app-dashboard-registred',
  templateUrl: './dashboard-registred.component.html',
  styleUrls: ['./dashboard-registred.component.scss'],
})
export class DashboardRegistredComponent implements OnInit {
  Session!: Session[];
  Cursus!: Cursus[];
  constructor(
    private sessionService: SessionServiceService,
    private cursusPublicService: CursusPublicService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.getCursus().subscribe(
      (data) => ((this.Cursus = data), console.log('cursus:' + data))
    );
    this.getSession().subscribe(
      (data) => ((this.Session = data), console.log('session' + data))
    );
  }

  getCursus(): Observable<Cursus[]> {
    return this.cursusPublicService.getAllCursus();
  }
  getSession(): Observable<Session[]> {
    return this.sessionService.getAllSession();
  }
}
