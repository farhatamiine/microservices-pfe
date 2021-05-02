import { NgModule } from '@angular/core';
import { FormsModule,ReactiveFormsModule} from '@angular/forms'
import { MerchantsRoutingModule } from './merchants-routing.module';
import { NzTableModule } from 'ng-zorro-antd/table';
import { NzCardModule } from 'ng-zorro-antd/card';
import { MerchantsComponent } from './merchants.component';
import { NzSelectModule } from 'ng-zorro-antd/select';
import { CommonModule } from "@angular/common";
import { NzMessageModule } from 'ng-zorro-antd/message';
import { NgxPaginationModule } from 'ngx-pagination';
import { NzModalModule } from 'ng-zorro-antd/modal';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzFormModule } from 'ng-zorro-antd/form';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzGridModule } from 'ng-zorro-antd/grid';
import { NzNotificationModule } from 'ng-zorro-antd/notification';
import { CreateMerchantComponent } from '../../components/merchants/create-merchant/create-merchant.component';
import { NzSpinModule } from 'ng-zorro-antd/spin';

@NgModule({
  imports: [
    MerchantsRoutingModule,
    NzTableModule,
    NzCardModule,
    NzButtonModule,
    NzModalModule,
    CommonModule,
    NzIconModule,
    NzMessageModule,
    NgxPaginationModule,
    NzSelectModule,
    FormsModule,
    ReactiveFormsModule,
    NzFormModule,
    NzGridModule,
    NzInputModule,
    NzNotificationModule,
    NzSpinModule
  ],
  declarations: [MerchantsComponent, CreateMerchantComponent],
  exports: [MerchantsComponent]
})
export class MerchantsModule { }
