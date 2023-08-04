import { Component, OnInit } from '@angular/core';
import { Cursus } from '../../models/cursus.model';
import { CursusPublicService } from '../../services/cursus-public.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-section-cursus-home',
  templateUrl: './section-cursus-home.component.html',
  styleUrls: ['./section-cursus-home.component.scss'],
})
export class SectionCursusHomeComponent implements OnInit {
  cursus!: Cursus[];
  constructor(private cursusPublicService: CursusPublicService) {}

  ngOnInit(): void {
    this.getCursus().subscribe(
      (data) => ((this.cursus = data), console.log(data))
    );
  }

  getCursus(): Observable<Cursus[]> {
    return this.cursusPublicService.getAllCursus();
  }
}
