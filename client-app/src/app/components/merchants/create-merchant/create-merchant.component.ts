import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { DeviceModel } from 'src/app/models/device-model';
import { DeviceService } from 'src/app/services/device/device.service';
import { MerchantService } from 'src/app/services/merchant/merchant.service';

@Component({
  selector: 'app-create-merchant',
  templateUrl: './create-merchant.component.html',
  styleUrls: ['./create-merchant.component.scss']
})
export class CreateMerchantComponent implements OnInit {

  //modal
  isOkLoading = false;
  isVisible = false;

  validateForm!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private merchantService: MerchantService,
    private deviceService: DeviceService,
    private notification: NzNotificationService) {

    }

  ngOnInit(): void {
    this.validateForm = this.fb.group({
      merchantNumber: [null, [Validators.required]],
      merchantName: [null, [Validators.required]],
      status: [null, [Validators.required]],
      devices: [null, [Validators.required]]
    });
    this.getDevicesList();
  }
  // generate random Device Number
  randomString(length: number): string {
    let chars = '0123456789';
    let result = '';
    for (var i = length; i > 0; --i) result += chars[Math.floor(Math.random() * chars.length)];
    return result;
  }

  generateDeviceNumber():void {
    this.validateForm.patchValue({
      merchantNumber: this.randomString(15)
    });
  }

  createNotification(type: string,title: string, message: string): void {
    this.notification.create(
      type,
      title,
      message
    );
  }

  submitForm(): void {
    this.isOkLoading = true;
    for (const i in this.validateForm.controls) {
      this.validateForm.controls[i].markAsDirty();
      this.validateForm.controls[i].updateValueAndValidity();
    }

    this.merchantService.addNewMerchant(this.validateForm.value)
      .subscribe(
        response => {
          this.isOkLoading = false;
          this.sendToSibling(true);
          this.createNotification('success', "New Device Number", `New merchant Number has been added with Number <b>${response.merchantNumber}</b> with status <b>${response.status}</b>`);
          this.validateForm.reset();
        },
        error => {
          console.log(error);
          this.isOkLoading = false;
          this.createNotification('error', "Error Smtg Wrong!!", `There is something wrong when try to add new merchant <b>${error.statusText}</b>`);
          this.validateForm.reset();
        });
  }

  handleOk(): void {
    this.isOkLoading = true;
  }

  handleCancel(): void {
    this.isVisible = false;
  }

  showModal(): void {
    this.isVisible = true;
  }

  /********************************************************************** */
  /************ update ambulancier course status in service ************ */
  /******************************************************************** */
  public sendToSibling(status: boolean): void {
    this.merchantService.updateWhenMerchantAdded(status);
  }

  /********************************************************************** */
  /************************ Device Select Flow ************************* */
  /******************************************************************** */
  listOfDevices: DeviceModel[] = [];
  isLoading = false;
  currentPage:number = 0;
  totalDevices: number = 0;

  // tslint:disable:no-any
  getDevicesList():void {
    this.deviceService.getAllDevices(this.currentPage + "", "10").subscribe(
      async (response: any) => {
        if(response != null) {
          this.isLoading = false;
          const {devices, totalItems} = response;
          this.listOfDevices = [...this.listOfDevices, ...devices];
          this.totalDevices = totalItems;
        }
      }, async(error) => {
        this.createNotification('error', "Load Devices", `There is something wrong when try load devices <b>${error.statusText}</b>`);
      }
    )
  }

  loadMore(): void {
    if(this.totalDevices > this.listOfDevices.length) {
      this.isLoading = true;
      this.currentPage = this.currentPage + 1;
      this.getDevicesList();
    }
  }

}
