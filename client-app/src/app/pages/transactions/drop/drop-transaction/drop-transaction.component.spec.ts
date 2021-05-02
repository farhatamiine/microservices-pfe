import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DropTransactionComponent } from './drop-transaction.component';

describe('DropTransactionComponent', () => {
  let component: DropTransactionComponent;
  let fixture: ComponentFixture<DropTransactionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DropTransactionComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DropTransactionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
