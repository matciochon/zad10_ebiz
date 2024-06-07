import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';

export interface ReservationRequestModel{
  id: number;
    room_id: number;
    start: string;
    end: string;
    name: string;
    lecturer_mail: string;
    lecturer_first_name: string;
    lecturer_last_name: string;
    participants_count: number;
    reservation_datetime: string;
    accepted: boolean;
}

@Component({
  selector: 'app-admin-panel',
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.css']
})
export class AdminPanelComponent implements OnInit {

  constructor(private http: HttpClient, private router: Router) { }

  public data: ReservationRequestModel[] = [];

  ngOnInit(): void {
    const authorization = localStorage.getItem('Authorization');

    if (authorization) {

        const headers = new HttpHeaders({
            'Authorization': authorization
        });
        this.http.get<any>('http://localhost:8080/session', { headers }).subscribe(
            (response: any) => {
                if (response[0]<0) {
                    this.router.navigate(["login"]);
                }
            },
            (error: any) => {
                this.router.navigate(["login"]);
            }
        );

    }
    else
    {
        this.router.navigate(["login"]);
    }

    this.http.get('http://localhost:8080/reservations/notaccepted').subscribe((response: any) => {
      this.data = response;
      this.data.sort((a, b) => {
        const dateA = new Date(a.reservation_datetime);
        const dateB = new Date(b.reservation_datetime);
        return dateA.getTime() - dateB.getTime();
      });
    });
  }

  syncUsosDataForMonth(month: number, year: number) {
    this.http.get('http://localhost:8080/usos?month=' + month.toString() + '&year=' + year.toString()).subscribe();
  }

  onSyncUsosButtonClicked() {
    const currentDate = new Date();
    const month = currentDate.getMonth() + 1;
    const year = currentDate.getFullYear();

    this.syncUsosDataForMonth(month, year);
  }

  acceptReservation(id: number) {
    this.http.get('http://localhost:8080/reservations/accept/' + id.toString()).subscribe((response: any) => {
      location.reload();
    });
  }

  rejectReservation(id: number) {
    this.http.get('http://localhost:8080/reservations/reject/' + id.toString()).subscribe((response: any) => {
      location.reload();
    });
  }

  logout() {
    localStorage.removeItem('Authorization');
    this.router.navigate(["login"]);
  }

}
