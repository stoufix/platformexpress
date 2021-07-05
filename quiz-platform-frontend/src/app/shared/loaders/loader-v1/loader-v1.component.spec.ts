import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoaderV1Component } from './loader-v1.component';

describe('LoaderV1Component', () => {
  let component: LoaderV1Component;
  let fixture: ComponentFixture<LoaderV1Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoaderV1Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoaderV1Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
