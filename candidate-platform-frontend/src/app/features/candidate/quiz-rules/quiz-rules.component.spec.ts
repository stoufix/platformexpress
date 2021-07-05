import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QuizRulesComponent } from './quiz-rules.component';

describe('QuizRulesComponent', () => {
  let component: QuizRulesComponent;
  let fixture: ComponentFixture<QuizRulesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QuizRulesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QuizRulesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
