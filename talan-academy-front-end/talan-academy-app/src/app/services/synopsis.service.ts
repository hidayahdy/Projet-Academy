import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Session } from '../models/session.model';
import { Synopsis } from '../models/synopsis.model';

@Injectable({
  providedIn: 'root',
})
export class SynopsisService {
  constructor(private http: HttpClient) {}

  getSynopsisByLessonId(id: number): Observable<Synopsis> {
    return this.http.get<Synopsis>(
      `http://localhost:8080/talan/synopsis/${id}`
    );
  }
}
