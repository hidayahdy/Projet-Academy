import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { catchError, map, Observable, throwError } from 'rxjs';
import { Application } from '../models/application.model';
import {
  tabEquivalence,
  EquivalenceElement,
} from '../models/equivalence.model';

export interface ApplicationData {
  content: Application[];
  pageable: {
    sort: {
      empty: boolean;
      sorted: boolean;
      unsorted: boolean;
    };
    offset: number;
    pageSize: number;
    pageNumber: number;
    unpaged: boolean;
    paged: boolean;
  };
  last: boolean;
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
  sort: {
    empty: boolean;
    sorted: boolean;
    unsorted: boolean;
  };
  first: boolean;
  numberOfElements: number;
  empty: boolean;
}
@Injectable({
  providedIn: 'root',
})
export class ApplicationsService {
  tab: EquivalenceElement[] = tabEquivalence;
  constructor(private http: HttpClient) {}

  getAllApplicationsPagination(
    page: number,
    size: number
  ): Observable<ApplicationData> {
    return this.http
      .get<any>(`http://localhost:8080/talan/application/${page}/${size}`)
      .pipe(
        map((appData: ApplicationData) => appData),
        catchError((err) => throwError(err))
      );
  }

  getCV(cv: string): Observable<any> {
    return this.http.get<any>(
      `http://localhost:8080/talan/files/${cv}`,

      { responseType: 'arraybuffer' as 'json' }
    );
  }

  paginateByQuery(
    query: string,
    page: number,
    size: number
  ): Observable<ApplicationData> {
    let params = new HttpParams();
    let query1!: any;
    this.tab.forEach((element) => {
      if (
        (element.word_French.includes(query) ||
          element.word_French_UPPERCASE.includes(query) ||
          element.word_French_Capital.includes(query)) &&
        query !== ''
      ) {
        query1 = element.word_English;
      }
    });
    params = params.append('query1', query1);
    params = params.append('query', query);
    params = params.append('page', page);
    params = params.append('size', size);

    return this.http
      .get('http://localhost:8080/talan/application/', { params })
      .pipe(
        map((appData: any) => appData),
        catchError((err) => throwError(err))
      );
  }

  getUserApplicationsPagination(
    user_id: number,
    page: number,
    size: number
  ): Observable<ApplicationData> {
    let params = new HttpParams();

    params = params.append('page', page);
    params = params.append('size', size);

    return this.http
      .get(`http://localhost:8080/talan/application/user/${user_id}`, {
        params,
      })
      .pipe(
        map((appData: any) => appData),
        catchError((err) => throwError(err))
      );
  }

  getUserApplicationsPaginationfilter1(
    user_id: number,
    query: string,
    page: number,
    size: number
  ): Observable<ApplicationData> {
    let params = new HttpParams();

    params = params.append('query', query);
    params = params.append('page', page);
    params = params.append('size', size);

    return this.http
      .get(`http://localhost:8080/talan/application/user/filter/${user_id}`, {
        params,
      })
      .pipe(
        map((appData: any) => appData),
        catchError((err) => throwError(err))
      );
  }

  getUserApplicationsPaginationfilter(
    user_id: number,
    query: string,
    page: number,
    size: number
  ): Observable<ApplicationData> {
    let params = new HttpParams();
    let query1!: any;
    this.tab.forEach((element) => {
      if (
        (element.word_French.includes(query) ||
          element.word_French_UPPERCASE.includes(query) ||
          element.word_French_Capital.includes(query)) &&
        query !== ''
      ) {
        query1 = element.word_English;
      }
    });
    params = params.append('query', query);
    params = params.append('query1', query1);
    params = params.append('page', page);
    params = params.append('size', size);

    return this.http
      .get(`http://localhost:8080/talan/application/user/filter/${user_id}`, {
        params,
      })
      .pipe(
        map((appData: any) => appData),
        catchError((err) => throwError(err))
      );
  }
  changeStatusById(app: Application): Observable<any> {
    return this.http.put<any>(
      `http://localhost:8080/talan/application/update/${app.id}`,
      app
    );
  }

  cancelApplicationsById(id: number): Observable<[Application]> {
    return this.http.get<[Application]>(
      `http://localhost:8080/talan/application/status/${id}`
    );
  }
  updateApplicationsById(
    application: Application,
    id: number
  ): Observable<[Application]> {
    return this.http.put<[Application]>(
      `http://localhost:8080/talan/application/${id}`,
      application
    );
  }

  addNewApplication(formData: FormData): Observable<any> {
    return this.http
      .post(`http://localhost:8080/talan/application`, formData)
      .pipe(map((res) => res));
  }

  verifStatutApplication(id: number): Observable<any> {
    return this.http.get<any>(
      `http://localhost:8080/talan/application/verif/${id}`
    );
  }

  usersHasApp() {
    return this.http.get<any>(`http://localhost:8080/talan/application/ids`);
  }
}
