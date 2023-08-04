import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import {
  FormGroup,
  FormBuilder,
  Validators,
  FormControl,
} from '@angular/forms';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-email-update-modal',
  templateUrl: './email-update-modal.component.html',
  styleUrls: ['./email-update-modal.component.scss'],
})
export class EmailUpdateModalComponent implements OnInit {
  email!: string;
  id!: number;
  resetEmailForm!: FormGroup;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private formBuilder: FormBuilder,
    private authService: AuthenticationService,
    private dialogRef: MatDialogRef<EmailUpdateModalComponent>
  ) {}

  ngOnInit(): void {
    this.email = this.data.email;
    this.id = this.data.id;
    this.resetEmailForm = this.formBuilder.group({
      newEmail: new FormControl(this.email, [Validators.email]),
    });
  }

  resetEmail() {
    this.authService
      .resetEMail(this.resetEmailForm.value.newEmail, this.id)
      .subscribe((res) => {
        this.dialogRef.close('save');
      });
  }
}
