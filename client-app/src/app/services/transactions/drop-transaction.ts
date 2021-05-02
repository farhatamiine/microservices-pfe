import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { DropTransactionModel } from 'src/app/models/drop-transaction-model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DropTransactionService {

  private api = environment.drop_transaction_api;

  constructor(private httpClient: HttpClient) {

  }

  getAllDropTransactions(
    pageNumber: string = "0",
    size: string = "15"
    ) {
    return this.httpClient.get<DropTransactionModel[]>(`${this.api}/`, {
      params: {
        page: pageNumber,
        size
      },
    });
  }
}
