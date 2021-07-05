import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QuizzesToCompleteComponent } from './quizzes-to-complete.component';

describe('QuizzesToCompleteComponent', () => {
  let component: QuizzesToCompleteComponent;
  let fixture: ComponentFixture<QuizzesToCompleteComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QuizzesToCompleteComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QuizzesToCompleteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
