import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { Session } from '../models/session.model';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class SessionServiceService {
  constructor(private http: HttpClient) {}

  getAllSession(): Observable<[Session]> {
    return this.http.get<[Session]>(`http://localhost:8080/talan/session`);
  }

  getAllSessionsWithPagination(
    page: number,
    size: number
  ): Observable<Session> {
    let params = new HttpParams();
    params = params.append('page', page);
    params = params.append('size', size);
    return this.http
      .get(`http://localhost:8080/talan/session/pages`, { params })
      .pipe(
        map((appData: any) => appData),
        catchError((err) => throwError(err))
      );
  }

  addSession(formValue: any): Observable<any> {
    return this.http
      .post('http://localhost:8080/talan/session/add', formValue)
      .pipe(map((res) => res));
  }

  deleteSession(id: number): Observable<any> {
    return this.http.delete(
      `http://localhost:8080/talan/session/delete/${id}`,
      {
        responseType: 'text',
      }
    );
  }
}
