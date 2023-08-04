import { Component, Input, OnInit } from '@angular/core';
import { Cursus } from '../../models/cursus.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-card-cursus',
  templateUrl: './card-cursus.component.html',
  styleUrls: ['./card-cursus.component.scss'],
})
export class CardCursusComponent implements OnInit {
  @Input() cursus!: Cursus;
  constructor(private router: Router) {}

  ngOnInit(): void {}

  details() {
    this.router.navigateByUrl(`cursus/details/${this.cursus.id}`);
  }
}
