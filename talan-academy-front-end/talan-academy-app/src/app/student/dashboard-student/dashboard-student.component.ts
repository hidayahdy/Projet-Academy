import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/user.model';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-dashboard-student',
  templateUrl: './dashboard-student.component.html',
  styleUrls: ['./dashboard-student.component.scss'],
})
export class DashboardStudentComponent implements OnInit {
  id!: number;
  start!: boolean;
  user !: User ; 
  nbrJour !: number ;
  constructor(private authenticationService: AuthenticationService) {}

  ngOnInit(): void {
    this.id = +localStorage.getItem('user_id');
    this.authenticationService.getUserById(this.id).subscribe((data) => {
      
      let dates: Date = new Date(data.session.startDate);
      this.nbrJour = this.date(dates) ;
      this.nbrJour < 0 ? (this.start = true) : (this.start = false);
      this.user = data ; 
    });
  }
  date(date1: Date): number {
    var date2 = Date.now();
    var time_diff = date1.getTime() -date2  ;
    var days_Diff = time_diff / (1000 * 3600 * 24);
    return Math.round(days_Diff);
  }
}