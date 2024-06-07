import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RoomService {
  private baseUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  getRoomById(roomId: string): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/room/${roomId}`);
  }
}