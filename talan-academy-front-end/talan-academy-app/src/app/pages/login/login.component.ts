import { Component, OnInit } from '@angular/core';
import {
  AbstractControl,
  FormGroup,
  FormBuilder,
  Validators,
  FormControl,
  FormGroupDirective,
  NgForm,
} from '@angular/forms';
import { Router } from '@angular/router';
import { AuthenticationService } from '../../services/authentication.service';
import { ErrorStateMatcher } from '@angular/material/core';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
  show: boolean = false;
  loginForm!: FormGroup;
  loginErrorMsg!: string;
  constructor(
    private authenticationService: AuthenticationService,
    private formBuilder: FormBuilder,
    private router: Router,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [
        Validators.required,
        Validators.pattern(
          /(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*#?&^_-]).{8,}/
        ),
      ]),
    });
  }

  login(): void {
    this.authenticationService.login(this.loginForm.value).subscribe(
      (data) => {
        if (this.authenticationService.getRoles() === 'ROLE_REGISTRED') {
          this.router.navigateByUrl('/candidat');
        } else if (this.authenticationService.getRoles() === 'ROLE_STUDENT') {
          this.router.navigateByUrl('/apprenti');
        } else if (this.authenticationService.getRoles() === 'ROLE_ADMIN') {
          this.router.navigateByUrl('/admin');
        }
      },
      (error) => {
        this.loginForm = this.formBuilder.group({
          email: '',
          password: '',
        });
        console.log(error);

        if (error != null)
          if (error.error.status == 401) {
            this.toastr.error('Vérifiez votre e-mail ou mot de passe');
          } else {
            this.toastr.error(
              'Un problème est survenu. Veuillez vous reconnecter ultérieurement'
            );
          }
      }
    );
  }
}
