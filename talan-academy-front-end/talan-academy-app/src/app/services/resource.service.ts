import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Resource } from '../models/resource.model';
import { Session } from '../models/session.model';
import { Synopsis } from '../models/synopsis.model';

@Injectable({
  providedIn: 'root',
})
export class ResourceService {
  constructor(private http: HttpClient) {}

  getResourceByLessonId(id: number): Observable<Resource[]> {
    return this.http.get<Resource[]>(
      `http://localhost:8080/talan/resource/${id}`
    );
  }
}
