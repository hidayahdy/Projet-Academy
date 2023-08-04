import { Component, OnInit, ViewChild, ApplicationRef } from '@angular/core';
import { SessionServiceService } from '../../services/session-service.service';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { AddSessionFormComponent } from '../add-session-form/add-session-form.component';

@Component({
  selector: 'app-session-admin-table',
  templateUrl: './session-admin-table.component.html',
  styleUrls: ['./session-admin-table.component.scss'],
})
export class SessionAdminTableComponent implements OnInit {
  displayedColumns: string[] = ['cursus', 'startDate', 'status', 'actions'];
  pageEvent!: PageEvent;

  sizeNumber: number = 5;

  pageNumber: number = 0;
  data = new MatTableDataSource();
  dataSource: any = null;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  constructor(
    private sessionService: SessionServiceService,
    public dialog: MatDialog,
    private appRef: ApplicationRef,
    private router: Router,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.initDataSource(0, 5);
  }
  openDialog() {
    this.dialog
      .open(AddSessionFormComponent, {})
      .afterClosed()
      .subscribe((val) => {
        if (val === 'save') {
          this.initDataSource(this.pageNumber, this.sizeNumber);
        }
      });
  }
  initDataSource(page: number, size: number) {
    this.sessionService
      .getAllSessionsWithPagination(page, size)
      .subscribe((appData) => (this.dataSource = appData));
  }
  onPaginateChange(event: PageEvent) {
    this.pageNumber = event.pageIndex;
    this.sizeNumber = event.pageSize;
    this.sessionService
      .getAllSessionsWithPagination(this.pageNumber, this.sizeNumber)
      .subscribe((appdata) => (this.dataSource = appdata));
  }

  deleteSession(id: number): void {
    this.sessionService.deleteSession(id).subscribe((data) => {
      if (data === 'Session deleted successfully') {
        this.toastr.success('Session supprimée avec success');
        this.initDataSource(this.pageNumber,this.sizeNumber);
      }else{
        this.toastr.error("Un erreur est survenue, Veuillez réessayer ultérieurement")
      }
    });
  }
}
