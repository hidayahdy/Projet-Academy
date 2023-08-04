import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-dashboad-admin',
  templateUrl: './dashboad-admin.component.html',
  styleUrls: ['./dashboad-admin.component.scss'],
})
export class DashboadAdminComponent implements OnInit {
  constructor(@Inject(MAT_DIALOG_DATA) public data: any) {}

  ngOnInit(): void {}
}
