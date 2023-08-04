import { NgModule } from '@angular/core';
import { ExtraOptions, RouterModule, Routes } from '@angular/router';

import { AuthGuard } from './guards/auth.guard';
import { RoleGuard } from './guards/role.guard';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/login/login.component';
import { CursusAdminPageComponent } from './pages/cursus-admin-page/cursus-admin-page.component';
import { RegisterComponent } from './pages/register/register.component';
import { ValidationRegisterComponent } from './pages/validation-register/validation-register.component';

import { SidenavComponent } from './components/sidenav/sidenav.component';
import { UserApplicationComponent } from './components/user-application/user-application.component';
import { TestComponent } from './components/test/test/test.component';
import { ApplicationDetailsComponent } from './components/application-details/application-details.component';
import { ProfileComponent } from './profile/profile.component';
import { AddApplicationComponent } from './components/applications/add-application/add-application.component';
import { SessionAdminPageComponent } from './pages/session-admin-page/session-admin-page.component';
import { CursusDetailsComponent } from './components/cursus-details/cursus-details.component';
const routerOptions: ExtraOptions = {
  scrollPositionRestoration: 'enabled',
  anchorScrolling: 'enabled',
  scrollOffset: [0, 64],
};
import { SynopsisComponent } from './components/sidenav-student/synopsis/synopsis.component';
import { LessonComponent } from './components/sidenav-student/lesson/lesson.component';
import { CursusDetailsNavbarComponent } from './components/cursus-details-navbar/cursus-details-navbar.component';
import { AccountConfirmationComponent } from './components/account-confirmation/account-confirmation.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: '', component: HomeComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'cursus/details/:id', component: CursusDetailsNavbarComponent },
  { path: 'verify/:code', component: ValidationRegisterComponent },
  { path: 'activation', component: AccountConfirmationComponent },
  {
    path: 'apprenti/cursus/:id',
    component: TestComponent,
    children: [
      {
        path: 'lesson/:id',
        component: LessonComponent,
      },
    ],
    canActivate: [RoleGuard],
    data: {
      role: 'ROLE_STUDENT',
    },
  },
  {
    path: 'candidat/profil/:id',
    component: ProfileComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'applications/:id',
    component: ApplicationDetailsComponent,
  },
  {
    path: 'user/applications',
    component: UserApplicationComponent,
  },

  // {
  //   path: 'cursus',
  //   component: CursusAdminPageComponent,
  // },
  {
    path: 'admin',
    component: SidenavComponent,
    loadChildren: () =>
      import('./admin/admin.module').then((m) => m.AdminModule),
    canActivate: [RoleGuard],
    data: {
      role: 'ROLE_ADMIN',
    },
  },
  {
    path: 'apprenti',
    component: SidenavComponent,
    loadChildren: () =>
      import('./student/student.module').then((m) => m.StudentModule),
    canActivate: [RoleGuard],
    data: {
      role: 'ROLE_STUDENT',
    },
  },

  {
    path: 'candidat',
    component: SidenavComponent,
    loadChildren: () =>
      import('./registred/registred.module').then((m) => m.RegistredModule),
    canActivate: [RoleGuard],
    data: {
      role: 'ROLE_REGISTRED',
    },
  },

  { path: '**', pathMatch: 'full', redirectTo: '/' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, routerOptions)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
