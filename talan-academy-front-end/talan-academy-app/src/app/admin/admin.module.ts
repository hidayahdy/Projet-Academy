import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminRoutingModule } from './admin-routing.module';
import { MaterialModule } from '../material/material/material.module';
import { DashboadAdminComponent } from './dashboad-admin/dashboad-admin.component';
import { UsersComponent } from './users/users.component';
import { MentorsComponent } from './mentors/mentors.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AccountValidationModalComponent } from './account-validation-modal/account-validation-modal.component';
import { EmailUpdateModalComponent } from './email-update-modal/email-update-modal.component';

@NgModule({
  declarations: [
    DashboadAdminComponent,
    UsersComponent,
    MentorsComponent,
    AccountValidationModalComponent,
    EmailUpdateModalComponent,
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
  ],
})
export class AdminModule {}
