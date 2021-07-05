import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RecruiterAddComponent } from './recruiter-add.component';

describe('RecruiterAddComponent', () => {
  let component: RecruiterAddComponent;
  let fixture: ComponentFixture<RecruiterAddComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RecruiterAddComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RecruiterAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
