import { NgModule } from '@angular/core';
import { CreateMerchantComponent } from './create-merchant.component'
import { FormsModule,ReactiveFormsModule} from '@angular/forms'
import { CommonModule } from "@angular/common";

@NgModule({
  imports: [CommonModule,
    FormsModule,
    ReactiveFormsModule],
  declarations: [CreateMerchantComponent],
  exports: [CreateMerchantComponent]
})
export class DevicesModule { }
