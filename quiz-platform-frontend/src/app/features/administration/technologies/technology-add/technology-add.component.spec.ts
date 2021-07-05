import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TechnologyAddComponent } from './technology-add.component';

describe('TechnologyAddComponent', () => {
  let component: TechnologyAddComponent;
  let fixture: ComponentFixture<TechnologyAddComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [TechnologyAddComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TechnologyAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
