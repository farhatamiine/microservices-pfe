import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { MerchantModel } from 'src/app/models/merchant-model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class MerchantService {

  private isMerchantAdded = new Subject<boolean>();
  private api = environment.device_merchnat_api;

  constructor(private httpClient: HttpClient) {

  }

  getAllMerchants(
    pageNumber: string = "0",
    size: string = "15"
    ) {
    return this.httpClient.get<MerchantModel[]>(`${this.api}/merchants`, {
      params: {
        page: pageNumber,
        size
      },
    });
  }

  getMerchant(id: number): Observable<MerchantModel> {
    return this.httpClient.get<MerchantModel>(`${this.api}/merchants/${id}`);
  }

  addNewMerchant(
    device: MerchantModel
  ): Observable<any> {
    return this.httpClient.post(`${this.api}/merchants`, device);
  }

  /*
   * @return {Observable<boolean>} : isMerchantAdded
   */
  public checkMerchantAdded(): Observable<boolean> {
    return this.isMerchantAdded.asObservable();
  }
  /*
   * @param {boolean} message : isMerchantAdded
   */
  public updateWhenMerchantAdded(message: boolean): void {
    this.isMerchantAdded.next(message);
  }

}
