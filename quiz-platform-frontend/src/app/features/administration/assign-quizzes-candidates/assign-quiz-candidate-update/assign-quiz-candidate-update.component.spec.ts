import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AssignQuizCandidateUpdateComponent } from './assign-quiz-candidate-update.component';

describe('AssignQuizCandidateUpdateComponent', () => {
  let component: AssignQuizCandidateUpdateComponent;
  let fixture: ComponentFixture<AssignQuizCandidateUpdateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AssignQuizCandidateUpdateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AssignQuizCandidateUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
