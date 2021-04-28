import { NgModule } from '@angular/core';
import { FormsModule,ReactiveFormsModule} from '@angular/forms'
import { DevicesRoutingModule } from './devices-routing.module';
import { NzTableModule } from 'ng-zorro-antd/table';
import { NzCardModule } from 'ng-zorro-antd/card';
import { DevicesComponent } from './devices.component';
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
import { CreateDeviceComponent } from '../../components/create/create-device/create-device.component';

@NgModule({
  imports: [
    DevicesRoutingModule,
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
    NzNotificationModule
  ],
  declarations: [DevicesComponent, CreateDeviceComponent],
  exports: [DevicesComponent]
})
export class DevicesModule { }
