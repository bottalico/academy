import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NumberGuessingGameComponent } from './number-guessing-game.component';

describe('NumberGuessingGameComponent', () => {
  let component: NumberGuessingGameComponent;
  let fixture: ComponentFixture<NumberGuessingGameComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NumberGuessingGameComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NumberGuessingGameComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
