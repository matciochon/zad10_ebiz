import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MainComponent } from '../components/main/main.component';
import { TopMenuComponent } from '../components/top-menu/top-menu.component';
import { RoomListComponent } from '../components/room-list/room-list.component';
import { AdminPanelComponent } from '../components/admin-panel/admin-panel.component';
import { RoomPanelComponent } from '../components/room-panel/room-panel.component';
import { ContactPanelComponent } from '../components/contact-panel/contact-panel.component';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AddReservationComponent } from './components/add-reservation/add-reservation.component';
import {MatInputModule} from '@angular/material/input';
import { MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { TextFieldModule } from '@angular/cdk/text-field';
import { FormsModule } from '@angular/forms';
import {MatTooltipModule} from '@angular/material/tooltip';
import { LoginComponent } from '../components/login/login.component';
import {MatButtonModule} from '@angular/material/button';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { FavouriteRoomsComponent } from './components/favourite-rooms/favourite-rooms.component';
import {MatCardModule} from "@angular/material/card";
import {MatIconModule} from "@angular/material/icon";

@NgModule({
  declarations: [
    AppComponent,
    MainComponent,
    TopMenuComponent,
    RoomListComponent,
    AdminPanelComponent,
    ContactPanelComponent,
    RoomPanelComponent,
    AddReservationComponent,
    LoginComponent,
    FavouriteRoomsComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatInputModule,
    MatDialogModule,
    MatFormFieldModule,
    TextFieldModule,
    FormsModule,
    MatTooltipModule,
    MatButtonModule,
    MatProgressSpinnerModule,
    MatSnackBarModule,
    MatCardModule,
    MatIconModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
