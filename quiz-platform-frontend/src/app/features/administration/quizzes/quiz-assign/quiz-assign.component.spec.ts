import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QuizAssignComponent } from './quiz-assign.component';

describe('QuizAssignComponent', () => {
  let component: QuizAssignComponent;
  let fixture: ComponentFixture<QuizAssignComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QuizAssignComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QuizAssignComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
