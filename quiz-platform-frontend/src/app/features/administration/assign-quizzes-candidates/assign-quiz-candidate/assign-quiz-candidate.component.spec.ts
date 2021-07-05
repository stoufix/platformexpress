import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AssignQuizCandidateComponent } from './assign-quiz-candidate.component';

describe('AssignQuizCandidateComponent', () => {
  let component: AssignQuizCandidateComponent;
  let fixture: ComponentFixture<AssignQuizCandidateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AssignQuizCandidateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AssignQuizCandidateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
