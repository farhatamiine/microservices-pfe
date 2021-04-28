export interface DeviceModel {
  id: number;
  deviceNumber: string;
}

export interface ListDeviceModel {
  totalItems: number,
  devices: DeviceModel[],
  totalPages: number,
  currentPage: number
}
