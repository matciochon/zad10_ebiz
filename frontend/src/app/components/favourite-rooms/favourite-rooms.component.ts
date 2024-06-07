import { HttpClient } from '@angular/common/http';
import { Component, NgZone, OnInit } from '@angular/core';

@Component({
  selector: 'app-favourite-rooms',
  templateUrl: './favourite-rooms.component.html',
  styleUrls: ['./favourite-rooms.component.css']
})
export class FavouriteRoomsComponent implements OnInit {

  constructor(private http: HttpClient, private _ngZone: NgZone) { this.data = JSON.parse(localStorage.getItem("fav") ?? "[]"); }
  ngOnInit(): void {
  }

  data: any[];


  sortAsc = true;
  sortByCapacity() {
    if (this.sortAsc) {
      this.data.sort((a, b) => Number(a.capacity) - Number(b.capacity));
    } else {
      this.data.sort((a, b) => Number(b.capacity) - Number(a.capacity));
    }
    this.sortAsc = !this.sortAsc;
  }
  removeFromFav(room: any) {
    let a = JSON.parse(localStorage.getItem("fav") ?? "null");

    if(a == null){
      a = [];
    }

    let b = a as any[];
    let c = b.map(x => x.id);
    if(c.includes(room.id)){
      b.splice(c.indexOf(room.id), 1);
    }

    alert("Usunięto salę " + room.name + " z ulubionych.");

    localStorage.setItem("fav", JSON.stringify(b));
    this.data = b;
  }
}
