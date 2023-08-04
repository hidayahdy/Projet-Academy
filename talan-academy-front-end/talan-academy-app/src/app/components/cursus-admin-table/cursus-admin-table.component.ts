import {
  AfterViewInit,
  Component,
  OnInit,
  ApplicationRef,
  ViewChild,
} from '@angular/core';
import { MatPaginator, MatPaginatorIntl,PageEvent  } from '@angular/material/paginator';
import { CursusAdminService } from '../../services/cursus-admin.service';
import { Observable } from 'rxjs';
import { CursusAdmin } from '../../models/CursusAdmin.model';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AddCursusFormComponent } from '../add-cursus-form/add-cursus-form.component';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { MatTab } from '@angular/material/tabs';
import { MatTableDataSource } from '@angular/material/table';
import { CursusPagination } from 'src/app/models/CursusPagination.model';

@Component({
  selector: 'app-cursus-admin-table',
  templateUrl: './cursus-admin-table.component.html',
  styleUrls: ['./cursus-admin-table.component.scss'],
})
export class CursusAdminTableComponent implements OnInit {
  displayedColumns: string[] = [
    'picture',
    'name',
    'type',
    'description',
    'actions',
  ];
  pageEvent!: PageEvent;

  sizeNumber: number = 5;

  pageNumber: number = 0 ;
  data = new MatTableDataSource();
  dataSource!: any;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  constructor(
    private cursusAdminService: CursusAdminService,
    public dialog: MatDialog,
    private appRef: ApplicationRef,
    private router: Router,
    private toastr: ToastrService,
    private pag: MatPaginatorIntl
  ) {
   
  }

  openDialog() {
    this.dialog
      .open(AddCursusFormComponent, {})
      .afterClosed()
      .subscribe((val) => {
        if (val === 'save') {
          this.initDataSource(this.pageNumber,this.sizeNumber);
        }
      });
  }

  ngOnInit(): void {
    this.initDataSource(0,5);
  }
  initDataSource(page:number,size:number) {
    this.cursusAdminService.getCursusPagination( page, size).subscribe((appData) => this.dataSource = appData);
  }

  getCursus(): void {
    this.cursusAdminService
      .getAllCursus()
      .subscribe(
        (data) => (this.dataSource = data.sort((a, b) => b.id - a.id))
      );
  }

  changeVisible(id: number, visible: boolean): void {
    this.cursusAdminService
      .updateCursusVisible(id, visible)
      .subscribe((data) => this.initDataSource(this.pageNumber,this.sizeNumber));
  }

  deleteCursus(id: number): void {
    this.cursusAdminService.deleteCursus(id).subscribe((data) => {
      if (data === 'Vous ne pouvez pas supprimer ce cursus') {
        this.toastr.error('Vous ne pouvez pas supprimer ce cursus');
      }
      if (data === 'Cursus supprimer avec success') {
        this.toastr.success('Cursus supprimé avec success');
        this.initDataSource(this.pageNumber,this.sizeNumber);
      }
    });
  }

  editCursus(element: any) {
    this.dialog.open(AddCursusFormComponent, {
      data: element,
    });
  }

  onPaginateChange(event: PageEvent) {
    this.pageNumber = event.pageIndex;
    this.sizeNumber =event.pageSize;
    this.cursusAdminService.getCursusPagination(this.pageNumber, this.sizeNumber)
      .subscribe((appdata) => this.dataSource = appdata);
}
}