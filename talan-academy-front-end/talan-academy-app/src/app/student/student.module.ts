import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { StudentRoutingModule } from './student-routing.module';
import { MaterialModule } from '../material/material/material.module';
import { DashboardStudentComponent } from './dashboard-student/dashboard-student.component';


@NgModule({
  declarations: [
    DashboardStudentComponent
  ],
  imports: [
    CommonModule,
    StudentRoutingModule,
    MaterialModule
  ]
})
export class StudentModule { }
