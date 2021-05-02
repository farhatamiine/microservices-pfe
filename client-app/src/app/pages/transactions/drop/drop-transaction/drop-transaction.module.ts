import { NgModule } from '@angular/core';
import { FormsModule,ReactiveFormsModule} from '@angular/forms'
import { DropTransactionRoutingModule } from './drop-transaction-routing.module';
import { NzTableModule } from 'ng-zorro-antd/table';
import { NzCardModule } from 'ng-zorro-antd/card';
import { DropTransactionComponent } from './drop-transaction.component';
import { CommonModule } from "@angular/common";
import { NgxPaginationModule } from 'ngx-pagination';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzSelectModule } from 'ng-zorro-antd/select';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzNotificationModule } from 'ng-zorro-antd/notification';
import { NzMessageModule } from 'ng-zorro-antd/message';

@NgModule({
  imports: [
    DropTransactionRoutingModule,
    NzTableModule,
    NzCardModule,
    NzButtonModule,
    CommonModule,
    NzIconModule,
    NgxPaginationModule,
    NzNotificationModule,
    FormsModule,ReactiveFormsModule,
    NzSelectModule, NzMessageModule
  ],
  declarations: [DropTransactionComponent],
  exports: [DropTransactionComponent]
})
export class DropTransactionModule { }
