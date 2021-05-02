import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { DeviceService } from 'src/app/services/device/device.service';

@Component({
  selector: 'app-create-device',
  templateUrl: './create-device.component.html',
  styleUrls: ['./create-device.component.scss']
})
export class CreateDeviceComponent implements OnInit {

  //modal
  isOkLoading = false;
  isVisible = false;

  validateForm!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private deviceService: DeviceService,
    private notification: NzNotificationService) {

    }

  ngOnInit(): void {
    this.validateForm = this.fb.group({
      deviceNumber: [null, [Validators.required]],
    });
  }
  // generate random Device Number
  randomString(length: number): string {
    let chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ';
    let result = '';
    for (var i = length; i > 0; --i) result += chars[Math.floor(Math.random() * chars.length)];
    return result;
  }

  generateDeviceNumber():void {
    this.validateForm.patchValue({
      deviceNumber: this.randomString(6)
    })
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
    this.deviceService.addNewDevice(this.validateForm.value)
      .subscribe(
        response => {
          this.isOkLoading = false;
          this.sendToSibling(true);
          this.createNotification('success', "New Device Number", `New device Number has been added with Number <b>${response.deviceNumber}</b>`);
          this.validateForm.reset();
        },
        error => {
          console.log(error);
          this.isOkLoading = false;
          this.createNotification('error', "Error Smtg Wrong!!", `There is something wrong when try to add new device <b>${error.statusText}</b>`);
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
    this.deviceService.updateWhenDeviceAdded(status);
  }

}
