import { NgModule } from '@angular/core';
import { DevicesRoutingModule } from './devices-routing.module';
import { NzTableModule } from 'ng-zorro-antd/table';
import { NzCardModule } from 'ng-zorro-antd/card';
import { DevicesComponent } from './devices.component';
import { CommonModule } from "@angular/common";

import { NzModalModule } from 'ng-zorro-antd/modal';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { CreateDeviceComponent } from '../../components/create/create-device/create-device.component';

@NgModule({
  imports: [DevicesRoutingModule, NzTableModule, NzCardModule, NzButtonModule, NzModalModule, CommonModule],
  declarations: [DevicesComponent, CreateDeviceComponent],
  exports: [DevicesComponent]
})
export class DevicesModule { }
