import { Component, OnInit, Input } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Session } from 'src/app/models/session.model';
import { SessionServiceService } from 'src/app/services/session-service.service';

@Component({
  selector: 'app-form-choix',
  templateUrl: './form-choix.component.html',
  styleUrls: ['./form-choix.component.scss'],
})
export class FormChoixComponent implements OnInit {
  @Input() angForm: FormGroup | any;
  sessions!: [Session];
  cursusName!: [string];
  constructor(private sessionService: SessionServiceService) {}

  ngOnInit(): void {
    this.sessionService.getAllSession().subscribe((data) => {
      this.sessions = data;
      console.log(data);
    });
  }
}
