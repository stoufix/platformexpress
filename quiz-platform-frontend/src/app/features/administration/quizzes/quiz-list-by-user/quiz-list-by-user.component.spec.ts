import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QuizListByUserComponent } from './quiz-list-by-user.component';

describe('QuizListByUserComponent', () => {
  let component: QuizListByUserComponent;
  let fixture: ComponentFixture<QuizListByUserComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QuizListByUserComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QuizListByUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
