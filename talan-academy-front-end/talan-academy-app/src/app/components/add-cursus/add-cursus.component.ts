import { Component, OnInit } from '@angular/core';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AddCursusFormComponent } from '../add-cursus-form/add-cursus-form.component';

@Component({
  selector: 'app-add-cursus',
  templateUrl: './add-cursus.component.html',
  styleUrls: ['./add-cursus.component.scss'],
})
export class AddCursusComponent implements OnInit {
  constructor(public dialog: MatDialog) {}

  ngOnInit(): void {}
  openDialog() {
    this.dialog.open(AddCursusFormComponent, {
      data: {
        animal: 'panda',
      },
    }).afterClosed().subscribe();
  }
}
