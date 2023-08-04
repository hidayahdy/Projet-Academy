import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { Observable, filter } from 'rxjs';
import { MatTableDataSource } from '@angular/material/table';
import { Application } from 'src/app/models/application.model';
import {
  ApplicationData,
  ApplicationsService,
} from 'src/app/services/applications.service';
import { Router } from '@angular/router';
import { map } from 'rxjs';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { ToastrService } from 'ngx-toastr';
import { MatDialog } from '@angular/material/dialog';
import { ApplicationModalComponent } from '../application-modal/application-modal.component';

@Component({
  selector: 'app-user-application',
  templateUrl: './user-application.component.html',
  styleUrls: ['./user-application.component.scss'],
})
export class UserApplicationComponent implements OnInit {

  pageEvent!: PageEvent;
  sizeNumber: number = 5;
  pageNumber: number = 0 ;
  id = this.authenticationService.getUserId();
  displayedColumns = ['Date',  'Cursus', 'Session','Statut', 'Actions'];
  dataSource!: any;
  filterValue: any = null;

  @ViewChild(MatPaginator, { static: true }) paginator!: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort!: MatSort;

  constructor(
    private applicationService: ApplicationsService,
    private authenticationService: AuthenticationService,
    private router: Router,
    private toastr: ToastrService,
    public dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.initDataSource();
  }

  initDataSource() {
    this.applicationService
      .getUserApplicationsPagination(this.id, this.pageNumber, this.sizeNumber)
      .subscribe((appData) => {
        this.dataSource = appData;
        
      });
  }

  onPaginateChange(event: PageEvent) {
    let page = event.pageIndex;

    let size = event.pageSize;
    this.sizeNumber = size;
    if (this.filterValue == null) {
      this.applicationService
      .getUserApplicationsPagination(this.id, page,size)
        .subscribe((appdata: ApplicationData) => {
          this.dataSource = appdata;
        });
    } else {
      this.applicationService
      .getUserApplicationsPaginationfilter(this.id, this.filterValue, page,size)
        .subscribe((appdata: ApplicationData) => {
          this.dataSource = appdata;
        });
    }
  }

  findByField(query: string) {
    this.applicationService
    .getUserApplicationsPaginationfilter(this.id, query, 0, this.sizeNumber)
      .pipe(
        map((userData: any) => {
          this.dataSource = userData;
        })
      )
      .subscribe();
  
}
  public concelApplication(id: number) {

    this.applicationService.cancelApplicationsById(id).subscribe(() => {
      this.applicationService
      .getUserApplicationsPagination(this.id, this.pageNumber, this.sizeNumber)
      .subscribe((appData: ApplicationData) => {
        this.dataSource = appData;
        this.toastr.success(
          'Application annulée avec succès !'
        );
      });
    },
    (error) => {
      if (error != null){
          this.toastr.error('Un problème est survenu. Veuillez réessayer ultérieurement !');
        }
    });
  }

  openDialog(application: any) {
    const dialogRef = this.dialog.open(ApplicationModalComponent, {
      height: '480px',
      width: '700px',
      data: application
    });
  }
}
