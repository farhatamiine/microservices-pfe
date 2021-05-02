import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable, Subject} from "rxjs";
import { DeviceModel } from "../../models/device-model";

const optionRequete = {
  headers: new HttpHeaders({
    'Access-Control-Allow-Origin':'*',
    'Accept': 'application/json',
    'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
  })
};

@Injectable({
  providedIn: 'root'
})
export class DeviceService {

  private isDeviceAdded = new Subject<boolean>();
  private api = environment.device_merchnat_api;

  constructor(private httpClient: HttpClient) {

  }

  getAllDevices(
    pageNumber: string = "0",
    size: string = "15") {
    return this.httpClient.get<DeviceModel[]>(`${this.api}/devices`, {
      params: {
        page: pageNumber,
        size
      },
    });
  }

  getDevice(id: number): Observable<DeviceModel> {
    return this.httpClient.get<DeviceModel>(`${this.api}/devices/${id}`);
  }

  addNewDevice(
    device: DeviceModel
  ): Observable<any> {
    return this.httpClient.post(`${this.api}/devices`, device);
  }


  /*
   * @return {Observable<boolean>} : isDeviceAdded
   */
  public checkDeviceAdded(): Observable<boolean> {
    return this.isDeviceAdded.asObservable();
  }
  /*
   * @param {boolean} message : isDeviceAdded
   */
  public updateWhenDeviceAdded(message: boolean): void {
    this.isDeviceAdded.next(message);
  }

}
