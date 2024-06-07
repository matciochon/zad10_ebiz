import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminPanelComponent } from 'src/components/admin-panel/admin-panel.component';
import { ContactPanelComponent } from 'src/components/contact-panel/contact-panel.component';
import { MainComponent } from 'src/components/main/main.component';
import { RoomListComponent } from 'src/components/room-list/room-list.component';
import { RoomPanelComponent } from 'src/components/room-panel/room-panel.component';
import { LoginComponent } from 'src/components/login/login.component';
import { FavouriteRoomsComponent } from './components/favourite-rooms/favourite-rooms.component';

const routes: Routes = [
  { path: '', component: RoomListComponent },
  { path: 'room-list', component: RoomListComponent },
  { path: 'fav', component: FavouriteRoomsComponent },
  { path: 'admin-panel', component: AdminPanelComponent },
  { path: 'contact-panel', component: ContactPanelComponent },
  { path: 'room/:id', component: RoomPanelComponent },
  { path: 'login', component: LoginComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
