import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QuestionCloneComponent } from './question-clone.component';

describe('QuestionCloneComponent', () => {
  let component: QuestionCloneComponent;
  let fixture: ComponentFixture<QuestionCloneComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QuestionCloneComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QuestionCloneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
