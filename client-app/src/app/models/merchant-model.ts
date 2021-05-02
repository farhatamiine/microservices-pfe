import { DeviceModel } from "./device-model";

export interface MerchantModel {
  id: number,
  merchantNumber: number,
  merchantName: string,
  status: string,
  devices: DeviceModel[]
}
