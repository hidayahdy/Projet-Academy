import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddApplicationComponent } from '../components/applications/add-application/add-application.component';

import { UserApplicationComponent } from '../components/user-application/user-application.component';
import { DashboardRegistredComponent } from './dashboard-registred/dashboard-registred.component';

const routes: Routes = [
  {
    path: '',
    component: DashboardRegistredComponent,
  },
  {
    path: 'applications',
    component: UserApplicationComponent,
  },
  {
    path: 'add',
    component: AddApplicationComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class RegistredRoutingModule {}
