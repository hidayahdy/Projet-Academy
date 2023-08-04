import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SidenavStudentComponent } from '../components/sidenav-student/sidenav-student.component';
import { DashboardStudentComponent } from './dashboard-student/dashboard-student.component';

const routes: Routes = [
  {
    path: '',
    component: DashboardStudentComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class StudentRoutingModule {}
