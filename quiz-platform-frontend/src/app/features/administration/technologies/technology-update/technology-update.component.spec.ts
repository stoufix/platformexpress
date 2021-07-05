import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TechnologyUpdateComponent } from './technology-update.component';

describe('TechnologyUpdateComponent', () => {
  let component: TechnologyUpdateComponent;
  let fixture: ComponentFixture<TechnologyUpdateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [TechnologyUpdateComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TechnologyUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
