import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})
@Component({
  selector: 'app-add-reservation',
  templateUrl: './add-reservation.component.html',
  styleUrls: ['./add-reservation.component.css']
})
export class AddReservationComponent implements OnInit {

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: {startTime:string, name: string, capacity: string, roomId: string, endTime: string },
    private http: HttpClient,
    private snackBar: MatSnackBar,
    private dialogRef: MatDialogRef<AddReservationComponent>
  ) { }

  ngOnInit(): void {
  }

  public get startDate(){
    return new Date(this.data.startTime).toLocaleTimeString()
  }

  public get endDate(){
    return new Date(this.data.endTime).toLocaleTimeString()
  }

  submitForm(email: string, name: string, eventName: string, participantsCount: string) {
    const startTime = new Date(this.data.startTime);
    const endTime = new Date(this.data.endTime);
    startTime.setHours(startTime.getHours() + 2);
    endTime.setHours(endTime.getHours() + 2);

    const requestBody = {
      start: startTime,
      end: endTime,
      name: eventName,
      room_id: this.data.roomId,
      lecturer_mail: email,
      lecturer_first_name: name.split(' ')[0],
      lecturer_last_name: name.split(' ')[1],
      participants_count: parseInt(participantsCount, 10),
      reservation_datetime: new Date().toISOString()
    };

    this.http.post<any>('http://localhost:8080/reservations/add', requestBody, { responseType: 'text' as 'json' }).subscribe(
      response => {
        this.snackBar.open(response, 'Close', {
          duration: 5000,
        });
        this.dialogRef.close();
      },
      error => {
        this.snackBar.open(error.error, 'Close', {
          duration: 5000,
        });
      }
    );

  }

}
