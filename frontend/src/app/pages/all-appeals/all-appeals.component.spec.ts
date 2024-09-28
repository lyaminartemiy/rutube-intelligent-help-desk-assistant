import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllAppealsComponent } from './all-appeals.component';

describe('AllAppealsComponent', () => {
  let component: AllAppealsComponent;
  let fixture: ComponentFixture<AllAppealsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AllAppealsComponent]
    });
    fixture = TestBed.createComponent(AllAppealsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
