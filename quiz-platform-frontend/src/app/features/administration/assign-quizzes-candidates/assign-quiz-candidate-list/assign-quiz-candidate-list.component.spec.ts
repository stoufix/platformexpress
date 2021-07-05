import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AssignQuizCandidateListComponent } from './assign-quiz-candidate-list.component';

describe('AssignQuizCandidateListComponent', () => {
  let component: AssignQuizCandidateListComponent;
  let fixture: ComponentFixture<AssignQuizCandidateListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AssignQuizCandidateListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AssignQuizCandidateListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
