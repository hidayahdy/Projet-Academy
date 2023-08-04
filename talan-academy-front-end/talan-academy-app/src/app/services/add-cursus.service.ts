import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AddCursusService {
  constructor(private http: HttpClient) {}

  addCursus(formValue: any): Observable<any> {
    return this.http
      .post('http://localhost:8080/talan/cursus', formValue)
      .pipe(map((res) => res));
  }
}
