import { Component, Inject, OnInit } from '@angular/core';
import { map, Observable } from 'rxjs';
import { AddCursusService } from '../../services/add-cursus.service';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router } from '@angular/router';
import {
  FormControl,
  FormGroup,
  FormBuilder,
  Validators,
} from '@angular/forms';

@Component({
  selector: 'app-add-cursus-form',
  templateUrl: './add-cursus-form.component.html',
  styleUrls: ['./add-cursus-form.component.scss'],
})
export class AddCursusFormComponent implements OnInit {
  cursusForm!: FormGroup;
  buttonClicked: boolean = false;
  cursusFile: any = File;
  type!: string;
  newCursusPreview$!: Observable<any>;

  constructor(
    private formBuilder: FormBuilder,
    private addCursusService: AddCursusService,
    private dialogRef: MatDialogRef<AddCursusFormComponent>,
    private router: Router,
    @Inject(MAT_DIALOG_DATA) public editData: any
  ) {}

  ngOnInit(): void {
    this.cursusForm = this.formBuilder.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      keyWords: ['', Validators.required],
      file: ['', Validators.required],
      type: ['', Validators.required],
    });
    this.newCursusPreview$ = this.cursusForm.valueChanges.pipe(
      map((formValue) => ({
        ...formValue,
      }))
    );

    if (this.editData) {
      this.cursusForm.controls['name'].setValue(this.editData.name);
      this.cursusForm.controls['description'].setValue(
        this.editData.description
      );
      this.cursusForm.controls['keyWords'].setValue(
        this.editData.keyWords.join(',')
      );
      this.type = this.editData.type;
      console.log(this.type);
    }
  }

  onSelectFile(event: any) {
    const file = event.target.files[0];
    this.cursusFile = file;
    if (this.cursusFile != null) {
      this.cursusForm.patchValue({
        file: this.cursusFile,
      });
    }
  }

  onSubmitForm() {
    // const formValue = {
    //   name: this.cursusForm.value.name,
    //   description: this.cursusForm.value.description,
    //   type: this.type,
    //   cursusPicture: this.cursusFile,
    // };
    console.log(this.buttonClicked);

    this.buttonClicked = true;
    console.log(this.buttonClicked);

    if (this.cursusForm.valid) {
      const fd = new FormData();
      fd.append('name', this.cursusForm.value.name);
      fd.append('description', this.cursusForm.value.description);
      fd.append('type', this.type);
      fd.append('keyWords', this.cursusForm.value.keyWords);
      fd.append('cursusPicture', this.cursusFile);

      this.addCursusService.addCursus(fd).subscribe((res) => {
        this.cursusForm.reset();
        this.dialogRef.close('save');
      });
    }
  }
}
