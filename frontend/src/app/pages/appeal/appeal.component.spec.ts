import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AppealComponent } from './appeal.component';

describe('AppealComponent', () => {
  let component: AppealComponent;
  let fixture: ComponentFixture<AppealComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AppealComponent]
    });
    fixture = TestBed.createComponent(AppealComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
