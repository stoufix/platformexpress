import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RecruiterUpdateComponent } from './recruiter-update.component';

describe('RecruiterUpdateComponent', () => {
  let component: RecruiterUpdateComponent;
  let fixture: ComponentFixture<RecruiterUpdateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RecruiterUpdateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RecruiterUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
