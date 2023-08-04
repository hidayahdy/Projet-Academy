import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { User } from 'src/app/models/user.model';
import { AuthenticationService } from 'src/app/services/authentication.service';
interface Speciality {
  value: string;
  viewValue: string;
}
@Component({
  selector: 'app-form-identity',
  templateUrl: './form-identity.component.html',
  styleUrls: ['./form-identity.component.scss'],
})
export class FormIdentityComponent implements OnInit {
  @Output() newItemEvent = new EventEmitter<any>();
  @Output() ItemEvent = new EventEmitter<any>();
  @Input() angForm: FormGroup | any;
  @Input() saveClicked: boolean = false;
  email: string;
  user: User = new User();
  specialities: Speciality[] = [
    { value: 'SFAX', viewValue: 'Sfax' },
    { value: 'SOUSSE', viewValue: 'Sousse' },
    { value: 'TUNIS', viewValue: 'Tunis' },
    { value: 'MONASTIR', viewValue: 'Monastir' },
    { value: 'BIZERTE', viewValue: 'Bizerte' },
  ];
  constructor(private authenticationService: AuthenticationService) {}

  ngOnInit(): void {
    this.email = localStorage.getItem('user_email');
    this.authenticationService.getUserByEmail(this.email).subscribe((data) => {
      this.user = data;
      this.angForm.patchValue({
        phone: this.user.phone,
        linkedin: this.user.linkedin,
        address: this.user.adress,
      });
      return data;
    });
  }
}
