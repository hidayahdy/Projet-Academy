import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { User } from 'src/app/models/user.model';
import { ApplicationsService } from 'src/app/services/applications.service';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-add-application',
  templateUrl: './add-application.component.html',
  styleUrls: ['./add-application.component.scss'],
})
export class AddApplicationComponent implements OnInit {
  firstForm!: FormGroup;
  secondForm!: FormGroup;
  thirdForm!: FormGroup;
  formData: FormData = new FormData();

  email: string;
  uploadedCV: File;
  constructor(
    private fb: FormBuilder,
    private applicationService: ApplicationsService,
    private toastr: ToastrService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.firstForm = this.fb.group({
      phone: [
        '',
        [
          Validators.required,
          Validators.minLength(8),
          Validators.maxLength(8),
          Validators.pattern('^\\d{0,9}$'),
        ],
      ],
      linkedin: [
        '',
        [
          Validators.required,
          Validators.pattern(
            '(https?://)?([\\da-z.-]+)\\.([a-z.]{2,6})[/\\w .-]*/?'
          ),
        ],
      ],
      address: [null, Validators.required],
    });

    this.secondForm = this.fb.group({
      diploma: ['', [Validators.required]],
      situation: ['', Validators.required],
      speciality: ['', Validators.required],
      experience: ['', Validators.required],
      itKnowledge: ['', Validators.required],
      motivation: ['', Validators.required],
      cv: ['', Validators.required],
    });

    this.thirdForm = this.fb.group({
      session: ['', [Validators.required]],
    });

    this.email = localStorage.getItem('user_email');
  }
  saveClicked: boolean = false;
  getUplodedFile(file: File) {
    console.log(file.type);
    if (file.size <= 10000000 && file.type === 'application/pdf') {
      this.uploadedCV = file;
      this.formData.set('cv', this.uploadedCV);
    } else {
      this.toastr.error('Vérifier la taille ou le format de votre CV !');
    }
  }

  onClickCancel() {
    this.firstForm.reset();
    this.secondForm.reset();
    this.toastr.info('Votre Candidature a été annulé');
    this.router.navigateByUrl('/candidat');
  }

  onClicksave() {
    this.saveClicked = true;

    this.formData = new FormData();
    this.formData.append('email', this.email);
    this.formData.append('phone', this.firstForm.value['phone']);
    this.formData.append('linkedin', this.firstForm.value['linkedin']);
    this.formData.append('address', this.firstForm.value['address']);
    this.formData.append('diploma', this.secondForm.value['diploma']);
    this.formData.append('speciality', this.secondForm.value['speciality']);
    this.formData.append('situation', this.secondForm.value['situation']);
    this.formData.append('itKnowledge', this.secondForm.value['itKnowledge']);
    this.formData.append('experience', this.secondForm.value['experience']);
    this.formData.append('cv', this.uploadedCV);
    this.formData.append('motivation', this.secondForm.value['motivation']);
    this.formData.append('session', this.thirdForm.value['session']);
    console.log(this.formData);
    this.applicationService
      .addNewApplication(this.formData)
      .subscribe((data) => {
        this.toastr.success('Votre candidature a été soumise avec succès !');
        this.router.navigateByUrl('/candidat');

        return data;
      });
  }
}
