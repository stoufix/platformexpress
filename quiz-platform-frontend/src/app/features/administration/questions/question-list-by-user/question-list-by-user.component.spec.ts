import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QuestionListByUserComponent } from './question-list-by-user.component';

describe('QuestionListByUserComponent', () => {
  let component: QuestionListByUserComponent;
  let fixture: ComponentFixture<QuestionListByUserComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QuestionListByUserComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QuestionListByUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
