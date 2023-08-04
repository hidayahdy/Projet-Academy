import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatSort, Sort, SortDirection } from '@angular/material/sort';
import { ToastrService } from 'ngx-toastr';
import { MatDialog } from '@angular/material/dialog';
import {
  ApplicationData,
  ApplicationsService,
} from 'src/app/services/applications.service';
import { Router } from '@angular/router';
import { map } from 'rxjs';

import { ApplicationModalComponent } from '../application-modal/application-modal.component';
import { Application } from 'src/app/models/application.model';

@Component({
  selector: 'app-applications',
  templateUrl: './applications.component.html',
  styleUrls: ['./applications.component.scss'],
})
export class ApplicationsComponent implements OnInit {
  sizeNumber: number = 6;
  file: any;
  filterValue: any = null;

  status: any;

  pageEvent!: PageEvent;
  displayedColumns: string[] = [
    'user',
    'session',
    'date',
    'diplome',
    'status',
    'action',
  ];
  dataSource!: ApplicationData;
  applications!: any;
  SortByField!: any;
  SortByOrder!: any;

  @ViewChild(MatPaginator, { static: true }) paginator!: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort!: MatSort;

  constructor(
    private applicationService: ApplicationsService,
    private router: Router,
    private toastr: ToastrService,
    public dialog: MatDialog
  ) {
    this.initDataSource();
  }

  ngOnInit(): void {
    this.initDataSource();
  }

  sortData(sortState: Sort) {
    console.log(sortState.direction);
  }

  initDataSource() {
    this.applicationService
      .getAllApplicationsPagination(0, 6)
      .subscribe((appData: ApplicationData) => {
        this.dataSource = appData;
        console.log(this.dataSource);
      });
  }

  onPaginateChange(event: PageEvent) {
    let page = event.pageIndex;

    let size = event.pageSize;
    this.sizeNumber = size;
    if (this.filterValue == null) {
      this.applicationService
        .getAllApplicationsPagination(page, size)
        .subscribe((appdata: ApplicationData) => {
          this.dataSource = appdata;
        });
    } else {
      this.applicationService
        .paginateByQuery(this.filterValue, page, size)
        .subscribe((appdata: ApplicationData) => {
          this.dataSource = appdata;
        });
    }
  }

  findByField(username: string) {
    this.applicationService
      .paginateByQuery(username, 0, this.sizeNumber)
      .pipe(
        map((userData: any) => {
          this.dataSource = userData;
        })
      )
      .subscribe();
  }
  openDialog(application: any) {
    const dialogRef = this.dialog.open(ApplicationModalComponent, {
      height: '410px',
      width: '700px',
      data: {
        experience: application.experience,
        diploma: application.diploma,
        speciality: application.speciality,
        itKnowledge: application.itKnowledge,
        situation: application.situation,
        motivation: application.motivation,
      },
    });
  }
  downloadPDF(event: any, cv: string) {
    this.applicationService.getCV(cv).subscribe(
      (data: any) => {
        if (data == null)
          this.toastr.error(
            'Une erreur est survenue. Merci de rééssayer ultérieurement'
          );
        // let blob=JSON.parse(data)
        else {
          let file = new Blob([data], { type: 'application/pdf' });

          let fileURL = URL.createObjectURL(file);

          window.open(fileURL);
        }
      },
      (err) =>
        this.toastr.error(
          'Une erreur est survenue. Merci de rééssayer ultérieurement'
        )
    );
  }
  checkApp(element: Application) {
    element.status = 'ACCEPTED';

    console.log(element);
    this.applicationService.changeStatusById(element).subscribe(
      (data: any) => {},
      (err) =>
        this.toastr.error(
          'Une erreur est survenue. Merci de rééssayer ultérieurement'
        )
    );
    console.log(element);
  }
  cancelApp(element: Application) {
    element.status = 'REFUSED';
    this.applicationService.changeStatusById(element).subscribe(
      (data: any) => {},
      (err) =>
        this.toastr.error(
          'Une erreur est survenue. Merci de rééssayer ultérieurement'
        )
    );
  }
}
