import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ApplicationsComponent } from '../components/applications/applications.component';
import { ApplicationsAdminPageComponent } from '../pages/applications-admin-page/applications-admin-page.component';
import { CursusAdminPageComponent } from '../pages/cursus-admin-page/cursus-admin-page.component';
import { DashboadAdminComponent } from './dashboad-admin/dashboad-admin.component';
import { SessionAdminPageComponent } from '../pages/session-admin-page/session-admin-page.component';
import { UsersComponent } from './users/users.component';
import { MentorsComponent } from './mentors/mentors.component';

const routes: Routes = [
  {
    path: '',
    component: ApplicationsComponent,
  },
  {
    path: 'cursus',
    component: CursusAdminPageComponent,
  },
  {
    path: 'applications',
    component: ApplicationsComponent,
  },
  {
    path: 'sessions',
    component: SessionAdminPageComponent,
  },
  {
    path: 'utilisateurs',
    component: UsersComponent,
  },
  {
    path: 'mentors',
    component: MentorsComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AdminRoutingModule {}
