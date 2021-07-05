import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QuizAddMarkComponent } from './quiz-add-mark.component';

describe('QuizAddMarkComponent', () => {
  let component: QuizAddMarkComponent;
  let fixture: ComponentFixture<QuizAddMarkComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QuizAddMarkComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QuizAddMarkComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
