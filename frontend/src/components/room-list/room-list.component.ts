import { Component, ElementRef, NgZone, OnInit, ViewChild } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-room-list',
  templateUrl: './room-list.component.html',
  styleUrls: ['./room-list.component.css']
})
export class RoomListComponent implements OnInit {


  constructor(private http: HttpClient, private _ngZone: NgZone) { this.originalData = []; this.data = [] }

  originalData: any[];
  data: any[];
  filterFunctions: [name: string, function: { (a: any): boolean }][] = [];

  @ViewChild("min") minInput: ElementRef | undefined;
  @ViewChild("max") maxInput: ElementRef | undefined;
  @ViewChild("name") nameInput: ElementRef | undefined;

  ngOnInit(): void {

    this.http.get('http://localhost:8080/rooms').subscribe((response: any) => {
      this.originalData = response;
      this.data = response;
    });
  }

  filter() {
    let filtered = this.originalData;
    this.filterFunctions.forEach(fun => filtered = filtered.filter(fun[1]));
    this.data = filtered;
  }

  addCapacityFilter() {
    let indexCapacityFn = this.filterFunctions.findIndex(x => x[0] == "capacity");
    if (indexCapacityFn != -1) {
      this.filterFunctions.splice(indexCapacityFn, 1);
    }

    let min = Number(this.minInput?.nativeElement.value);
    let max = Number(this.maxInput?.nativeElement.value);
    if ( min == 0 && max == 0 ) {
    } else if ( max == 0 ) {
      this.filterFunctions.push(["capacity", (x => x.capacity >= min)]);
    } else if ( min == 0) {
      this.filterFunctions.push(["capacity", (x => x.capacity <= max)]);
    } else {
      this.filterFunctions.push(["capacity", (x => x.capacity >= min && x.capacity <= max)]);
    }
    this.filter();
  }

  removeCapacityFilter() {
    let indexCapacityFn = this.filterFunctions.findIndex(x => x[0] == "capacity");
    if (indexCapacityFn != -1) {
      this.filterFunctions.splice(indexCapacityFn, 1);
    }
    this.filter();
  }

  addNameFilter() {
    let indexNameFn = this.filterFunctions.findIndex(x => x[0] == "name");
    if (indexNameFn != -1) {
      this.filterFunctions.splice(indexNameFn, 1);
    }
    this.filterFunctions.push(["name", (x => (x.name as unknown as string).startsWith(this.nameInput?.nativeElement.value))]);
    this.filter();
  }

  removeNameFilter() {
    let indexNameFn = this.filterFunctions.findIndex(x => x[0] == "name");
    if (indexNameFn != -1) {
      this.filterFunctions.splice(indexNameFn, 1);
    }
    this.filter();
  }

  sortAsc = true;
  sortByCapacity() {
    if (this.sortAsc) {
      this.data.sort((a, b) => Number(a.capacity) - Number(b.capacity));
    } else {
      this.data.sort((a, b) => Number(b.capacity) - Number(a.capacity));
    }
    this.sortAsc = !this.sortAsc;
  }

  addToFav(room: any) {
    let a = JSON.parse(localStorage.getItem("fav") ?? "null");

    if(a == null){
      a = [room];
    }

    let b = a as any[];
    let c = b.map(x => x.id);
    if(!c.includes(room.id)){
      b.push(room);
    }

    alert("Dodano salę " + room.name + " do ulubionych. Aby ją usunąć wejdź w ulubione sale.");

    localStorage.setItem("fav", JSON.stringify(b));
  }
}
