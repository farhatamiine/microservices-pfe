import { Component, OnInit } from '@angular/core';
import { NzMessageService } from 'ng-zorro-antd/message';
import {DeviceModel, ListDeviceModel} from '../../models/device-model';
import { DeviceService } from '../../services/device.service'
@Component({
  selector: 'app-devices',
  templateUrl: './devices.component.html',
  styleUrls: ['./devices.component.scss']
})
export class DevicesComponent implements OnInit {

  // device data
  listOfDevices: DeviceModel[] = [];
  page = 1;
  count = 0;
  pageSize = 1;

  selectedSize = { label: '15 per page', value: 15 };
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
