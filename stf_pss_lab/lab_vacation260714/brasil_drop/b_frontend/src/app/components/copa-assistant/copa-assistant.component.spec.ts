import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CopaAssistantComponent } from './copa-assistant.component';

describe('CopaAssistantComponent', () => {
  let component: CopaAssistantComponent;
  let fixture: ComponentFixture<CopaAssistantComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CommonModule, FormsModule, HttpClientTestingModule, RouterTestingModule], declarations: [CopaAssistantComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CopaAssistantComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
