import { Component, OnInit, ViewChild } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

import { ErrorStateMatcher } from '@angular/material/core';
import { AuthenticationService } from '../../services/authentication.service';
import { MatDialog } from '@angular/material/dialog';
import { AccountValidationModalComponent } from 'src/app/admin/account-validation-modal/account-validation-modal.component';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent implements OnInit {
  show: boolean = false;
  submitted = false;
  registerForm!: FormGroup;
  registerErrorMsg!: string;
  isPicture!: false;
  control!: any;
  formDisable = true;

  activationMsg!: String;
  @ViewChild('formRef') formRef;
  constructor(
    private authenticationService: AuthenticationService,
    private formBuilder: FormBuilder,
    private router: Router,
    private toastr: ToastrService,
    public dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.registerForm = this.formBuilder.group(
      {
        firstName: new FormControl('', Validators.required),
        lastName: new FormControl('', Validators.required),
        email: new FormControl('', [
          Validators.required,
          Validators.email,
          Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$'),
        ]),
        password: new FormControl('', [
          (c: AbstractControl) => Validators.required(c),
          Validators.pattern(
            /(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*#?&^_-]).{8,}/
          ),
        ]),
        confirmPass: new FormControl('', Validators.required),
      },
      { validator: this.checkIfMatchingPasswords('password', 'confirmPass') }
    );
  }
  get f() {
    return this.registerForm.controls;
  }
  checkIfMatchingPasswords(
    passwordKey: string,
    passwordConfirmationKey: string
  ) {
    return (group: FormGroup) => {
      let passwordInput = group.controls[passwordKey],
        passwordConfirmationInput = group.controls[passwordConfirmationKey];
      if (passwordInput.value !== passwordConfirmationInput.value) {
        return passwordConfirmationInput.setErrors({ notEquivalent: true });
      } else {
        return passwordConfirmationInput.setErrors(null);
      }
    };
  }
  register(formDirective): void {
    this.registerForm.disabled;
    this.authenticationService.register(this.registerForm.value).subscribe(
      (data) => {
        if (data != null)
          // this.registerForm = this.formBuilder.group({
          //   firstName: '',
          //   lastName: '',
          //   email: '',
          //   password: '',
          //   confirmPass: '',
          // });
          this.registerForm.enabled;

        this.toastr.info('Consulter votre boîte mail !!');
        this.reset();
        formDirective.resetForm();
      },
      (error) => {
        this.registerForm = this.formBuilder.group({
          firstName: '',
          lastName: '',
          email: '',
          password: '',
          confirmPass: '',
        });
        if (error != null) {
          if (
            error.error ===
            'Cette adresse e-mail est déjà utilisée , voulez vous activer votre compte ?'
          ) {
            this.activationMsg = error.error;
          } else if (error.status === 406) {
            this.toastr.error(error.error);
          } else {
            this.toastr.error('Réessayer plus tard!!');
          }
        }
      }
    );
  }

  reset() {
    this.ngOnInit();
    this.registerForm.updateValueAndValidity();
  }

  openDialog(msg: any) {
    const dialogRef = this.dialog.open(AccountValidationModalComponent, {
      height: '200px',
      width: '700px',
      data: { msg },
    });
  }
}
