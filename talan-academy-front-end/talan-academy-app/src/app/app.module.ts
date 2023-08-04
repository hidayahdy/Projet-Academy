import { NgModule, LOCALE_ID } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { JwtModule } from '@auth0/angular-jwt';
import { TokenInterceptorService } from './guards/token-interceptor.service';
import { MatSliderModule } from '@angular/material/slider';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './material/material/material.module';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/login/login.component';

import { IntroductionComponent } from './components/introduction/introduction.component';
import { DescriptionComponent } from './components/description/description.component';
import { FlexLayoutModule } from '@angular/flex-layout';
import { CardCursusComponent } from './components/card-cursus/card-cursus.component';
import { SubDescriptionPipe } from './pipes/sub-description.pipe';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { CursusAdminTableComponent } from './components/cursus-admin-table/cursus-admin-table.component';
import { SidenavListComponent } from './navigation/sidenav-list/sidenav-list.component';
import { LayoutComponent } from './components/layout/layout.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { AddCursusComponent } from './components/add-cursus/add-cursus.component';
import { CursusAdminPageComponent } from './pages/cursus-admin-page/cursus-admin-page.component';
import { ApplicationsComponent } from './components/applications/applications.component';
import { ApplicationDetailsComponent } from './components/application-details/application-details.component';
import { UserApplicationComponent } from './components/user-application/user-application.component';
import { ValidationRegisterComponent } from './pages/validation-register/validation-register.component';
import { RegisterComponent } from './pages/register/register.component';
import { ToastrModule } from 'ngx-toastr';
import { ReplaceStrPipe } from './pipes/replace-str.pipe';
import { AddCursusFormComponent } from './components/add-cursus-form/add-cursus-form.component';
import { NavbarSectionComponent } from './components/navbar-section/navbar-section.component';

import { registerLocaleData } from '@angular/common';
import * as fr from '@angular/common/locales/fr';
import { SidenavComponent } from './components/sidenav/sidenav.component';
import { SidenavStudentComponent } from './components/sidenav-student/sidenav-student.component';
import { SectionCursusHomeComponent } from './components/section-cursus-home/section-cursus-home.component';
import { TreeMobileComponent } from './components/tree-mobile/tree-mobile.component';
import { ProfileComponent } from './profile/profile.component';
import { TestComponent } from './components/test/test/test.component';
import { SweetAlert2Module } from '@sweetalert2/ngx-sweetalert2';
import { MatPaginatorIntl } from '@angular/material/paginator';
import { MyPaginatorLabel } from './components/applications/test';
import { ApplicationModalComponent } from './components/application-modal/application-modal.component';
import { ApplicationsAdminPageComponent } from './pages/applications-admin-page/applications-admin-page.component';
import { AddApplicationComponent } from './components/applications/add-application/add-application.component';
import { FormIdentityComponent } from './components/applications/add-application/form-identity/form-identity.component';
import { FormFormationComponent } from './components/applications/add-application/form-formation/form-formation.component';
import { FormChoixComponent } from './components/applications/add-application/form-choix/form-choix.component';
import { SessionAdminTableComponent } from './components/session-admin-table/session-admin-table.component';
import { SessionAdminPageComponent } from './pages/session-admin-page/session-admin-page.component';
import { AddSessionFormComponent } from './components/add-session-form/add-session-form.component';
import { CandidateProfileComponent } from './candidate-profile/candidate-profile.component';
import { CursusDetailsComponent } from './components/cursus-details/cursus-details.component';
import { SynopsisComponent } from './components/sidenav-student/synopsis/synopsis.component';
import { LessonComponent } from './components/sidenav-student/lesson/lesson.component';
import { ResourceComponent } from './components/sidenav-student/lesson/resource/resource.component';
import { CardSessionComponent } from './components/card-session/card-session.component';
import { CursusDetailsNavbarComponent } from './components/cursus-details-navbar/cursus-details-navbar.component';
import { ErrorhandlerInterceptor } from './guards/errorhandler.interceptor';
import { AccountConfirmationComponent } from './components/account-confirmation/account-confirmation.component';

export function tokenGetter() {
  return localStorage.getItem('access_token');
}

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    CardCursusComponent,
    SubDescriptionPipe,
    CursusAdminTableComponent,
    IntroductionComponent,
    DescriptionComponent,
    CardCursusComponent,
    SidenavListComponent,
    LayoutComponent,
    NavbarComponent,
    LoginComponent,
    SectionCursusHomeComponent,
    AddCursusComponent,
    CursusAdminPageComponent,
    ApplicationsComponent,
    ApplicationDetailsComponent,
    UserApplicationComponent,
    RegisterComponent,
    ValidationRegisterComponent,
    ReplaceStrPipe,
    SidenavStudentComponent,
    AddCursusFormComponent,
    NavbarSectionComponent,
    SidenavComponent,
    SidenavStudentComponent,
    TreeMobileComponent,
    ProfileComponent,
    TestComponent,
    ApplicationModalComponent,
    ApplicationsAdminPageComponent,
    AddApplicationComponent,
    FormIdentityComponent,
    FormFormationComponent,
    FormChoixComponent,
    SessionAdminTableComponent,
    SessionAdminPageComponent,
    AddSessionFormComponent,
    CandidateProfileComponent,
    CursusDetailsComponent,
    SynopsisComponent,
    LessonComponent,
    ResourceComponent,
    CardSessionComponent,
    CursusDetailsNavbarComponent,
    AccountConfirmationComponent,
  ],

  imports: [
    BrowserModule,
    FlexLayoutModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    MaterialModule,
    MatSliderModule,
    HttpClientModule,
    JwtModule.forRoot({
      config: {
        tokenGetter: tokenGetter,
        allowedDomains: ['example.com'],
        disallowedRoutes: ['http://example.com/examplebadroute/'],
      },
    }),
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    BrowserAnimationsModule, // required animations module
    ToastrModule.forRoot(),
    SweetAlert2Module.forRoot(),
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptorService,
      multi: true,
    },

    {
      provide: HTTP_INTERCEPTORS,
      useClass: ErrorhandlerInterceptor,
      multi: true,
    },

    { provide: LOCALE_ID, useValue: 'fr-FR' },
    { provide: MatPaginatorIntl, useValue: new MyPaginatorLabel() },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {
  constructor() {
    registerLocaleData(fr.default);
  }
}
