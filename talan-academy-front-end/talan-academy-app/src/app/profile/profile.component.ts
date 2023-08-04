import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { User } from '../models/user.model';
import { AuthenticationService } from '../services/authentication.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
})
export class ProfileComponent implements OnInit {
  user: User = new User();

  userId: any;
  editForm!: FormGroup;
  picture!: File;
  isPicture!: boolean;
  id!: any;
  uploadForm = new FormGroup({
    name: new FormControl('', [Validators.required]),
    file: new FormControl('', [Validators.required]),
  });
  constructor(
    private activatedRoute: ActivatedRoute,
    private userService: AuthenticationService,
    private router: Router,
    private fb: FormBuilder,
    private pc: FormBuilder,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    const id = localStorage.getItem('user_id');
    const email = localStorage.getItem('user_email');
    this.editForm = this.fb.group({
      firstName: ['', [Validators.required]],

      lastName: ['', [Validators.required]],
      phone: [
        '',
        [
          Validators.required,
          Validators.pattern('^[0-9]*$'),
          Validators.minLength(8),
          Validators.maxLength(8),
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
    });
    this.userId = +this.activatedRoute.snapshot.params['id'];
    this.isPicture = true;
    if (this.userId == id) {
      this.userService.getUserById(this.userId).subscribe((data) => {
        this.user = data;
        this.isPicture = this.user.picture == null ? true : false;
        this.editForm.patchValue({
          firstName: data.firstName,
          lastName: data.lastName,
          phone: data.phone,
          linkedin: data.linkedin,
        });
      });
    } else {
      this.router.navigateByUrl('/candidat');
    }
  }
  cancel() {

    window.location.reload()
  }


  onFileSelected(pic: any) {

   

    this.picture = pic.files[0];

    console.log('hello' + this.picture);

    this.updatePicture();

  }

  updateProfile() {
    this.editForm.value.id = this.userId;
    this.userService.updateUser(this.editForm.value).subscribe(
      (data) => {
        if (data != null) this.user = data;
        this.toastr.success('Profil mis à jour avec succès!');
      },
      (error) => {
        if (error != null) {
          if (error.status === 400) {
            this.toastr.error('Répéter votre mise à jour !');
          } else {
            this.toastr.error('Une erreur est survenue, réessayez plus tard !');
          }
        }
      }
    );
  }
  updatePicture() {
    const formData = new FormData();
    formData.append('id', JSON.stringify(this.user.id));
    formData.append('picture', this.picture);

    this.userService.UpdatePicture(formData).subscribe(
      (data) => {
        if (data != null) this.user.picture = data.picture;
        this.isPicture = false;
        this.toastr.success('Photo mise à jour avec succès!');
      },
      (error) => {
        if (error != null) {
          if (error.status === 401) {
            this.toastr.error('Réessayer avec un format image');
          } else {
            this.toastr.error('Une erreur est survenue, réessayez plus tard !');
          }
        }
      }
    );
  }
}
