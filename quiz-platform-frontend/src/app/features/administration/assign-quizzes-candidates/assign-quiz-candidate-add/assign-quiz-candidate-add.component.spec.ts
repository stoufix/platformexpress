import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AssignQuizCandidateAddComponent } from './assign-quiz-candidate-add.component';

describe('AssignQuizCandidateAddComponent', () => {
  let component: AssignQuizCandidateAddComponent;
  let fixture: ComponentFixture<AssignQuizCandidateAddComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AssignQuizCandidateAddComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AssignQuizCandidateAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
