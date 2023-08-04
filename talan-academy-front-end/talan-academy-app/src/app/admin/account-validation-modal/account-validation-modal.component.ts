import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
@Component({
  selector: 'app-account-validation-modal',
  templateUrl: './account-validation-modal.component.html',
  styleUrls: ['./account-validation-modal.component.scss'],
})
export class AccountValidationModalComponent implements OnInit {
  msg!: any;
  constructor(@Inject(MAT_DIALOG_DATA) public data: any) {}

  ngOnInit(): void {
    this.msg = this.data.msg;
  }
}
