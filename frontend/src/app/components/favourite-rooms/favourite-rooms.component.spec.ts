import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FavouriteRoomsComponent } from './favourite-rooms.component';

describe('FavouriteRoomsComponent', () => {
  let component: FavouriteRoomsComponent;
  let fixture: ComponentFixture<FavouriteRoomsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FavouriteRoomsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FavouriteRoomsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
