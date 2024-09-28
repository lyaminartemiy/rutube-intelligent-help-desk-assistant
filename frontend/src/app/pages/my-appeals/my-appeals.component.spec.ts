import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MyAppealsComponent } from './my-appeals.component';

describe('MyAppealsComponent', () => {
  let component: MyAppealsComponent;
  let fixture: ComponentFixture<MyAppealsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MyAppealsComponent]
    });
    fixture = TestBed.createComponent(MyAppealsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
