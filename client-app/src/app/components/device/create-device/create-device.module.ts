import { NgModule } from '@angular/core';
import { CreateDeviceComponent } from './create-device.component'
import { FormsModule,ReactiveFormsModule} from '@angular/forms'
import { CommonModule } from "@angular/common";

@NgModule({
  imports: [CommonModule,
    FormsModule,
    ReactiveFormsModule],
  declarations: [CreateDeviceComponent],
  exports: [CreateDeviceComponent]
})
export class DevicesModule { }
