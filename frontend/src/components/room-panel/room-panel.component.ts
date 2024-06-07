import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { MatDialog } from '@angular/material/dialog';
import { AddReservationComponent } from 'src/app/components/add-reservation/add-reservation.component';

@Component({
  selector: 'app-room-panel',
  templateUrl: './room-panel.component.html',
  styleUrls: ['./room-panel.component.css']
})
export class RoomPanelComponent implements OnInit {
  roomId: string = "";
  data: any;
  reservationData: any[];
  date: Date;
  chartTable: ChartHourlyInfo[][] = [];
  startTime: string = '';
  upMouseButton: ChartHourlyInfo | undefined;
  downMouseButton: ChartHourlyInfo | undefined;

  public email = {
    subject: '',
    body: ''
  };
  public useCustomEmail: boolean = false;
  public customEmail: string = '';

  constructor(private route: ActivatedRoute, private http: HttpClient, private dialog: MatDialog) {
    this.data = {
      name: "placeholder",
      capacity: "placeholder"
    }
    this.reservationData = [];
    this.date = new Date();

    let daysInWeek = 7;
    let hoursInADay = 24;
    for (let hour = 0; hour < hoursInADay; hour++) {
      this.chartTable.push([]);
      let dayTable = this.chartTable[hour];
      for (let day = 1; day <= daysInWeek; day++) {
        dayTable.push({
          hour: hour,
          day: day,
          showHour: false,
          isChosen: false,
          name: "",
          lecturer: "",
          from: 0,
          to: 60,
          date: new Date(),
          usos: false
        } as ChartHourlyInfo);
      }
    }

    let todayDay = this.date.getDay() - 1;
    let todayHour = this.date.getHours();

    let now = new Date(this.date.getFullYear(),
      this.date.getMonth(),
      this.date.getDate(),
      this.date.getHours())

    this.chartTable.forEach(hours => {
      hours.forEach(entity => {
        let localDate = new Date(this.addDays(entity.day - todayDay - 1, this.addHours(entity.hour - todayHour, now)));
        localDate = new Date(localDate.getTime() + 2 * 60 * 60 * 1000);
        entity.date = localDate;
      })
    });

    this.upMouseButton = undefined;
  }

  private addDays(days: number, date: Date) {
    return new Date(date.getTime() + (1000 * 60 * 60 * 24) * days);
  }

  private addHours(hours: number, date: Date) {
    return new Date(date.getTime() + (1000 * 60 * 60) * hours);
  }

  public addWeeks(amount: number) {
    let newChartTable = this.chartTable.map(hours => {
      return hours.map(entity => {
        return { ...entity, date: new Date(this.addDays(amount * 7, entity.date)), usos: false, isChosen: false, name: "", lecturer: "" };
      });
    });
    this.chartTable = newChartTable;
    for (let reservation of this.reservationData) {
      let date_start = new Date(reservation.start);
      let date_end = new Date(reservation.end);
      let day = date_start.getDay() - 1;
      let hour_start = date_start.getHours();
      let minutes_start = date_start.getMinutes();
      let hour_end = date_end.getHours();
      let minutes_end = date_end.getMinutes();
      this.chartTable[hour_start][day].from = minutes_start;
      this.chartTable[hour_end][day].to = minutes_end;
      if (date_start.getDate() >= this.chartTable[0][0].date.getDate() && date_end.getDate() <= this.chartTable[0][6].date.getDate()) {
        if (date_start.getMonth() === this.chartTable[0][0].date.getMonth() && date_end.getMonth() === this.chartTable[0][6].date.getMonth()) {
          if (reservation.accepted === 1) {
            for (let i = hour_start; i < hour_end; i++) {
              this.chartTable[i][day].isChosen = true;
              this.chartTable[i][day].name = reservation.name;
              this.chartTable[i][day].lecturer = reservation.lecturer_first_name + " " + reservation.lecturer_last_name + " - " + reservation.lecturer_mail;
              if (reservation.lecturer_mail === "USOS") {
                this.chartTable[i][day].usos = true;
              }
            }
          }
        }
      }
    }
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.roomId = params['id'];
    });

    this.http.get(`http://localhost:8080/room/${this.roomId}`).subscribe({
      next: response => {
        this.data = response;
      },
      error: err => {
        console.log(err);
      }
    });

    this.http.get(`http://localhost:8080/reservations?roomId=${this.roomId}`).subscribe({
      next: (response: any) => {
        console.log(response);
        this.reservationData = response;
        for (let reservation of this.reservationData) {
          let date_start = new Date(reservation.start);
          let date_end = new Date(reservation.end);
          let day = date_start.getDay() - 1;
          let hour_start = date_start.getHours();
          let minutes_start = date_start.getMinutes();
          let hour_end = date_end.getHours();
          let minutes_end = date_end.getMinutes();
          this.chartTable[hour_start][day].from = minutes_start;
          this.chartTable[hour_end][day].to = minutes_end;
          if (date_start.getDate() >= this.chartTable[0][0].date.getDate() && date_end.getDate() <= this.chartTable[0][6].date.getDate()) {
            if (date_start.getMonth() === this.chartTable[0][0].date.getMonth() && date_end.getMonth() === this.chartTable[0][6].date.getMonth()) {
              if (reservation.accepted === 1) {
                for (let i = hour_start; i < hour_end; i++) {
                  this.chartTable[i][day].isChosen = true;
                  this.chartTable[i][day].name = reservation.name;
                  this.chartTable[i][day].lecturer = reservation.lecturer_first_name + " " + reservation.lecturer_last_name + " - " + reservation.lecturer_mail;
                  if (reservation.lecturer_mail === "USOS") {
                    this.chartTable[i][day].usos = true;
                  }
                }
              }
            }
          }
        }
      },
      error: err => {
        console.log(err);
      }
    });
  }

  getWeekDay(day: number) {
    return ["Poniedziałek", "Wtorek", "Środa", "Czwartek", "Piątek", "Sobota", "Niedziela"][day - 1];
  }

  getDateToDisplay(date: Date) {
    return date.toLocaleString().split(',')[0];
  }

  mouseEnter(hour: ChartHourlyInfo) {
    hour.showHour = true;
  }

  mouseLeave(hour: ChartHourlyInfo) {
    if(this.downMouseButton == undefined){
      hour.showHour = false;
    }
  }

  showHour(hour: ChartHourlyInfo) {
    if (!hour.showHour)
      return ''

    return hour.hour + ':00'
  }

  makeReservation() {
    if (this.downMouseButton == undefined || this.downMouseButton.isChosen) {
      alert("W tym czasie istnieje już rezerwacja. Wybierz inny termin.");
      return;
    }

    if (this.upMouseButton == undefined || this.upMouseButton.isChosen) {
      alert("W tym czasie istnieje już rezerwacja. Wybierz inny termin.");
      return;
    }

    const startHour = this.downMouseButton.hour;
    const endHour = this.upMouseButton.hour;
    const day = this.downMouseButton.day - 1;

    for (let hour = startHour; hour <= endHour; hour++) {
      if (this.chartTable[hour][day].isChosen) {
        alert("W tym czasie istnieje już rezerwacja. Wybierz inny termin.");
        return;
      }
    }

    const isoString = this.downMouseButton.date.toISOString();
    const dotIndex = isoString.indexOf('.');
    const dateWithoutTimezone = dotIndex !== -1 ? isoString.substring(0, dotIndex) : isoString;
    this.startTime = dateWithoutTimezone;
    
    const endisoString = this.upMouseButton.date.toISOString();
    const enddotIndex = isoString.indexOf('.');
    const enddateWithoutTimezone = enddotIndex !== -1 ? endisoString.substring(0, enddotIndex) : endisoString;
    let endTime = new Date(new Date(enddateWithoutTimezone).getTime() + 1000*60*60).toISOString();

    if(this.upMouseButton == undefined){
      alert("Error");
    }

    let dialogRef = this.dialog.open(AddReservationComponent, {
      height: '450px',
      width: '300px',
      data: { ...this.data, roomId: this.roomId, startTime: this.startTime, endTime: endTime }
    });
  }

  sendEmail() {
    let recipientEmail = ['example@example.com'];

    if (this.useCustomEmail && this.customEmail.trim() !== '') {
      recipientEmail.push(this.customEmail.trim());
    }

    const subject = encodeURIComponent(this.email.subject);
    const body = encodeURIComponent(this.email.body);
    const mailtoLink = `mailto:${recipientEmail}?subject=${subject}&body=${body}`;
    window.location.href = mailtoLink;
  }

  onSendToAdminChange() {
    if (!this.useCustomEmail) {
      this.customEmail = '';
    }
  }

  public mouseUp(data: any){
    this.upMouseButton = data;
    this.makeReservation();
    this.downMouseButton = undefined;
    this.chartTable.forEach(x => {
      x.forEach(y => {
        y.showHour = false;
      })
    })
  }

  public mouseDown(data: any){
    this.downMouseButton = data;
  }
}

export interface ChartHourlyInfo {
  name: any;
  lecturer: any;
  hour: number;
  day: number;
  date: Date;
  showHour: boolean;
  isChosen: boolean;
  from: number;
  to: number;
  usos: boolean;
}
