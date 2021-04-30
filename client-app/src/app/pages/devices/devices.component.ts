import { Component, OnInit } from '@angular/core';
import { NzMessageService } from 'ng-zorro-antd/message';
import { Subscription } from 'rxjs';
import {DeviceModel} from '../../models/device-model';
import { DeviceService } from 'src/app/services/device/device.service';

@Component({
  selector: 'app-devices',
  templateUrl: './devices.component.html',
  styleUrls: ['./devices.component.scss']
})
export class DevicesComponent implements OnInit {

  public subscription: Subscription = new Subscription;

  // device data
  listOfDevices: DeviceModel[] = [];
  page = 1;
  count = 0;
  pageSize = 5;

  selectedSize = { label: `${this.pageSize} per page`, value: this.pageSize };
  pageSizes = [
    { label: '15 per page', value: 15 },
    { label: '30 per page', value: 30 },
    { label: '50 per page', value: 50 }
  ];


  constructor(
    private deviceService: DeviceService,
    private message: NzMessageService) { }

  ngOnInit(): void {
    this.getDevices();
    // set subscribe to message service
    this.subscription = this.deviceService.checkDeviceAdded().subscribe(msg => this.getDevices());
  }
  public ngOnDestroy(): void {
    this.subscription.unsubscribe(); // onDestroy cancels the subscribe request
  }


  getDevices():void {
    this.deviceService.getAllDevices(this.page -1 +"", this.pageSize+"").subscribe(
      async (response: any) => {
        if(response != null) {
          const {devices, totalItems} = response;
          this.listOfDevices = devices;
          this.listOfDevices = [...this.listOfDevices];
          this.count = totalItems;
        }
      }, async(error) => {
        this.message.create('error', `This is a message of <b>${error.statusText}</b>`);
      }
    )
  }

  handlePageChange(event: number): void {
    this.page = event;
    console.log(this.page, event);
    this.getDevices();
  }

  handlePageSizeChange(event: any): void {
    this.pageSize = event.value;
    this.page = 1;
    this.getDevices();
  }

}
