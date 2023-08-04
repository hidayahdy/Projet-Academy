import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CursusAdminService } from 'src/app/services/cursus-admin.service';
@Component({
  selector: 'app-cursus-details',
  templateUrl: './cursus-details.component.html',
  styleUrls: ['./cursus-details.component.scss'],
})
export class CursusDetailsComponent implements OnInit {
  cursus: any;
  constructor(
    private cursusService: CursusAdminService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    const cursusId = +this.route.snapshot.params['id'];
    this.getCursusById(cursusId);
  }

  public getCursusById(id: number) {
    this.cursusService.getCursusById(id).subscribe((data) => {
      this.cursus = data;
    });
  }
}
