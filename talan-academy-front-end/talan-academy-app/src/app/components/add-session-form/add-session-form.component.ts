import { Component, Inject, OnInit } from '@angular/core';
import { map, Observable } from 'rxjs';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { SessionServiceService } from '../../services/session-service.service';
import { CursusAdmin } from '../../models/CursusAdmin.model';
import { CursusAdminService } from '../../services/cursus-admin.service';
import {
  FormControl,
  FormGroup,
  FormBuilder,
  Validators,
} from '@angular/forms';
import { CursusSession } from 'src/app/models/CursusSession.model';
import { formatDate } from '@angular/common';

@Component({
  selector: 'app-add-session-form',
  templateUrl: './add-session-form.component.html',
  styleUrls: ['./add-session-form.component.scss'],
})
export class AddSessionFormComponent implements OnInit {
  sessionForm!: FormGroup;
  buttonClicked: boolean = false;
  cursus!: number;
  newSessionPreview$!: Observable<any>;
  currentDate: any = new Date();
  listCursusAvailable: CursusSession[];
  constructor(
    private formBuilder: FormBuilder,
    private sessionService: SessionServiceService,
    private cursusService: CursusAdminService,
    private dialogRef: MatDialogRef<AddSessionFormComponent>,
    private router: Router,
    @Inject(MAT_DIALOG_DATA) public editData: any
  ) {}

  ngOnInit(): void {
    this.getAllCursusAvailable();
    this.sessionForm = this.formBuilder.group({
      date: [Validators.required],
      cursus:['',Validators.required]
    });
    this.newSessionPreview$ = this.sessionForm.valueChanges.pipe(
      map((formValue) => ({
        ...formValue,
      }))
    );
  }
  onSubmitForm() {
    if (this.sessionForm.valid) {
      const fd = new FormData();
      fd.append('date',formatDate(this.sessionForm.value.date,"yyyy-MM-dd", 'en-GB') );
      fd.append('id', this.sessionForm.value.cursus);
      this.sessionService.addSession(fd).subscribe((res) => {
        this.sessionForm.reset();
        this.dialogRef.close('save');
      });
    }
    
    
  }

  getAllCursusAvailable() {
    this.cursusService.getAllCursus().subscribe((data) => this.listCursusAvailable = data);
  }
}
