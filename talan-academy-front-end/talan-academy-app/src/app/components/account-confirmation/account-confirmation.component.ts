import { Component, OnInit } from '@angular/core';
import {
  FormGroup,
  FormBuilder,
  Validators,
  FormControl,
} from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { AuthenticationService } from '../../services/authentication.service';

@Component({
  selector: 'app-account-confirmation',
  templateUrl: './account-confirmation.component.html',
  styleUrls: ['./account-confirmation.component.scss'],
})
export class AccountConfirmationComponent implements OnInit {
  activationForm!: FormGroup;
  constructor(
    private formBuilder: FormBuilder,
    private authenticationService: AuthenticationService,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.activationForm = this.formBuilder.group({
      email: new FormControl('', [Validators.email]),
    });
  }

  sendMail() {
    this.authenticationService
      .sendActionMail(this.activationForm.value.email)
      .subscribe((res) => this.toastr.info('email envoyé avec succés'));
    this.activationForm.reset();
  }
}
