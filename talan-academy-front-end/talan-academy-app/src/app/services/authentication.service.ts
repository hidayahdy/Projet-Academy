import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Observable, throwError } from 'rxjs';
import { map, tap } from 'rxjs/operators';
import { User } from '../models/user.model';

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
  private accessToken!: string;
  private baseUrl = 'http://localhost:8080/talan/auth';
  private loggedInUsername!: string;
  private roles: any;
  private jwtHelper = new JwtHelperService();

  constructor(private http: HttpClient) {}

  public login(user: User): Observable<HttpResponse<any>> {
    return this.http
      .post<any>(`http://localhost:8080/talan/auth/login`, user, {
        observe: 'response',
      })
      .pipe(
        tap((res) => {
          this.LocalStorageSave(
            res.body.accessToken,
            res.body.email,
            res.body.id,
            res.body.roles
          );
          this.isUserLoggedIn();
        })
      );
  }

  public LocalStorageSave(
    accessToken: string,
    email: string,
    id: number,
    roles: string[]
  ): void {
    this.accessToken = accessToken;
    window.localStorage.setItem('access_token', accessToken);
    window.localStorage.setItem('user_id', JSON.stringify(id));
    window.localStorage.setItem('roles', JSON.stringify(roles[0]));
    window.localStorage.setItem('user_email', email);
  }

  public loadAccessToken(): string | null {
    return localStorage.getItem('access_token');
  }

  getRoles(): any {
    const token = this.loadAccessToken();

    if (token) {
      const role = this.jwtHelper.decodeToken(token).roles[0];
      return role;
    }
    return null;
  }

  getUserId(): any {
    const token = this.loadAccessToken();

    if (token) {
      const id = this.jwtHelper.decodeToken(token).id;
      return id;
    }
    return null;
  }

  public logOut(): void {
    window.localStorage.removeItem('user_id');
    window.localStorage.removeItem('access_token');
    window.localStorage.removeItem('user_email');
    window.localStorage.removeItem('roles');

    window.location.reload();
  }
  getUser(): any {
    const user = window.localStorage.getItem('user_email');
    if (user) {
      return JSON.stringify(user);
    }
    return null;
  }

  public isAuthenticated(): boolean {
    let jwt = JSON.parse(localStorage.getItem('jwt') || '{}');

    return !this.jwtHelper.isTokenExpired(jwt.token);
  }
  // @ts-ignore
  public isUserLoggedIn(): boolean {
    this.loadAccessToken();

    if (this.accessToken != null && this.accessToken !== '') {
      if (this.jwtHelper.decodeToken(this.accessToken).sub != null || '') {
        if (!this.jwtHelper.isTokenExpired(this.accessToken)) {
          this.loggedInUsername = this.jwtHelper.decodeToken(
            this.accessToken
          ).sub;
          this.roles = this.jwtHelper.decodeToken(this.accessToken).roles;

          return true;
        } else {
          (error: any) => {
            throwError(error);

            return false;
          };
        }
      }
    }
  }

  public register(User: User): Observable<any> {
    return this.http
      .post<User>(`http://localhost:8080/talan/auth/register`, User)
      .pipe(map((res) => res));
  }

  public validationCode(code: string): Observable<any> {
    console.log(code);

    return this.http.get<any>(
      `http://localhost:8080/talan/auth/verify/${code}`
    );
  }
  updateUser(User: any): Observable<any> {
    return this.http.patch<any>(
      `http://localhost:8080/talan/auth/${User.id}`,
      User
    );
  }
  UpdatePicture(formData: FormData): Observable<any> {
    return this.http.put(`${this.baseUrl}`, formData).pipe(map((res) => res));
  }
  getPicture(filename: string): Observable<any> {
    console.log('aprés' + filename);
    return this.http.get<any>(`http://localhost:8080/talan/files/${filename}`);
  }

  public getUserById(id: number): Observable<any> {
    return this.http.get<any>(
      `http://localhost:8080/talan/auth/find-by-id/${id}`
    );
  }

  public getUserByEmail(email: String): Observable<any> {
    return this.http.get<any>(`http://localhost:8080/talan/auth/${email}`);
  }
  isConnected(): boolean {
    const token = this.loadAccessToken();
    if (token && !this.jwtHelper.isTokenExpired(token)) {
      return true;
    }
    return false;
  }

  getUsers(page: number, size: number) {
    return this.http.get<any>(
      `http://localhost:8080/talan/auth/ROLE_REGISTRED/ROLE_STUDENT/${page}/${size}`
    );
  }

  searchUsersByKeyWord(keyWord: String, page: number, size: number) {
    if (keyWord.toLowerCase() == 'oui')
      return this.http.get<any>(
        `http://localhost:8080/talan/auth/usersHaveApps/${page}/${size}`
      );
    if (keyWord.toLowerCase() == 'non')
      return this.http.get<any>(
        `http://localhost:8080/talan/auth/usersWithoutApps/${page}/${size}`
      );
    return this.http.get<any>(
      `http://localhost:8080/talan/auth/filtered&paginated/${keyWord}/${page}/${size}`
    );
  }

  changeAccountStatus(id: number) {
    return this.http.post<boolean>(
      `http://localhost:8080/talan/auth/activation/${id}`,
      {}
    );
  }

  sendActionMail(email: string): Observable<any> {
    return this.http.get<any>(
      `http://localhost:8080/talan/auth/activationEmail/${email}`
    );
  }

  resetEMail(email: string, id: number): Observable<any> {
    return this.http.patch<any>(
      `http://localhost:8080/talan/auth/newEmail/${id}`,
      email
    );
  }
}
