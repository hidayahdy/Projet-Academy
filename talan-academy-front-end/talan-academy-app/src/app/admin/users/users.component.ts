import { Component, OnInit, ViewChild } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { ApplicationsService } from 'src/app/services/applications.service';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { MatDialog } from '@angular/material/dialog';
import { EmailUpdateModalComponent } from '../email-update-modal/email-update-modal.component';
import { MatTable } from '@angular/material/table';
@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss'],
})
export class UsersComponent implements OnInit {
  @ViewChild(MatTable) table: MatTable<any>;
  displayedColumns: string[] = [
    'utilisateur',
    'email',
    'statut',
    'candidature',
    'téléphone',
    'linkedin',
    'compte',
  ];
  pageEvent!: PageEvent;
  users: any;
  toCheck: number[];
  filterValue: any = null;
  sizeNumber: number = 6;
  constructor(
    private authService: AuthenticationService,
    private appService: ApplicationsService,
    public dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.usersWithApplications();
    this.getUsers();
  }

  getUsers() {
    this.authService.getUsers(0, 6).subscribe((res) => (this.users = res));
  }

  onPaginateChange(event: PageEvent) {
    event.pageIndex;
    event.pageSize;
    if (this.filterValue == null || this.filterValue.length == 0) {
      this.authService
        .getUsers(event.pageIndex, event.pageSize)
        .subscribe((appdata) => (this.users = appdata));
    } else {
      this.authService
        .searchUsersByKeyWord(this.filterValue, event.pageIndex, event.pageSize)
        .subscribe((res) => {
          this.users = res;
        });
    }
  }

  usersWithApplications() {
    this.appService.usersHasApp().subscribe((res) => (this.toCheck = res));
  }

  checkApplication(id: number): String {
    let found = 'Non';
    this.toCheck.forEach((element) => {
      if (element === id) found = 'Oui';
    });
    return found;
  }

  findByKeyWord(keyWord: string) {
    if (keyWord.length == 0) this.getUsers();
    else
      this.authService
        .searchUsersByKeyWord(keyWord, 0, 6)
        .subscribe((res) => (this.users = res));
  }

  changeAccountStatus(id: number) {
    this.authService.changeAccountStatus(id).subscribe((res) => {
      this.users.content.forEach((element) => {
        if (element.id === id) element.activated = res;
      });
    });
  }

  openDialog(email: string, id: number) {
    const dialogRef = this.dialog
      .open(EmailUpdateModalComponent, {
        height: '310px',
        width: '500px',
        data: {
          email,
          id,
        },
      })
      .afterClosed()

      .subscribe((val) => {
        if (val === 'save') {
          this.getUsers();
        }
      });
  }
}
