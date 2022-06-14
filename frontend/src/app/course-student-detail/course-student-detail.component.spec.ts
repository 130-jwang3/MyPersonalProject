import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CourseStudentDetailComponent } from './course-student-detail.component';

describe('CourseStudentDetailComponent', () => {
  let component: CourseStudentDetailComponent;
  let fixture: ComponentFixture<CourseStudentDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CourseStudentDetailComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CourseStudentDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
