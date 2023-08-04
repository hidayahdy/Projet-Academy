import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { catchError, map, Observable, throwError } from 'rxjs';
import { CursusAdmin } from '../models/CursusAdmin.model';
import { Cursus } from '../models/cursus.model';

@Injectable({
  providedIn: 'root',
})
export class CursusAdminService {
  constructor(private http: HttpClient) {}

  public getAllCursus(): Observable<CursusAdmin[]> {
    return this.http.get<CursusAdmin[]>(
      'http://localhost:8080/talan/cursus/all'
    );
  }

  public getCursusById(id: number): Observable<Cursus> {
    return this.http.get<Cursus>(`http://localhost:8080/talan/cursus/${id}`);
  }

  public updateCursusVisible(
    id: number,
    visible: boolean
  ): Observable<CursusAdmin> {
    const params = new HttpParams();
    params.append('visible', visible);
    const requestOptions = { params: params };
    return this.http.put<CursusAdmin>(
      `http://localhost:8080/talan/cursus/update/${id}?visible=${visible}`,
      {}
    );
  }

  deleteCursus(id: number): Observable<any> {
    return this.http.delete(`http://localhost:8080/talan/cursus/delete/${id}`, {
      responseType: 'text',
    });
  }

  getCursusPagination(
    page: number,

    size: number
  ): Observable<CursusAdmin> {
    let params = new HttpParams();

    params = params.append('page', page);

    params = params.append('size', size);

    return this.http

      .get(`http://localhost:8080/talan/cursus/pages`, {
        params,
      })

      .pipe(
        map((appData: any) => appData),

        catchError((err) => throwError(err))
      );
  }
}
