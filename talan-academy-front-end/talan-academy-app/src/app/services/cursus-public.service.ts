import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Cursus } from '../models/cursus.model';

@Injectable({
  providedIn: 'root',
})
export class CursusPublicService {
  constructor(private http: HttpClient) {}

  getAllCursus(): Observable<Cursus[]> {
    return this.http.get<Cursus[]>('http://localhost:8080/talan/cursus/public');
  }
}
