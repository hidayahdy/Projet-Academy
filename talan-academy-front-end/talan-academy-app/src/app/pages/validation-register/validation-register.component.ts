import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthenticationService } from '../../services/authentication.service';

@Component({
  selector: 'app-validation-register',
  templateUrl: './validation-register.component.html',
  styleUrls: ['./validation-register.component.scss'],
})
export class ValidationRegisterComponent implements OnInit {
  isActivated = false;
  constructor(
    private authenticationService: AuthenticationService,
    private route: ActivatedRoute,
    private router: Router,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe((res: any) => {
      console.log(res);

      this.authenticationService
        .validationCode(res.code)
        .subscribe((res) => res);
      this.isActivated = true;
      this.router.navigateByUrl('/login');
      this.toastr.success('Bienvenue');
    });
  }
}
