import { Component, OnInit } from '@angular/core';
import { NzMessageService } from 'ng-zorro-antd/message';
import { DropTransactionModel } from 'src/app/models/drop-transaction-model';
import { DropTransactionService} from 'src/app/services/transactions/drop-transaction';

@Component({
  selector: 'app-drop-transaction',
  templateUrl: './drop-transaction.component.html',
  styleUrls: ['./drop-transaction.component.scss']
})
export class DropTransactionComponent implements OnInit {

  // device data
  listOfTransactions: DropTransactionModel[] = [];
  page = 1;
  count = 0;
  pageSize = 5;

  selectedSize = { label: `${this.pageSize} per page`, value: this.pageSize };
  pageSizes = [
    { label: '15 per page', value: 15 },
    { label: '30 per page', value: 30 },
    { label: '50 per page', value: 50 }
  ];
  expandSet = new Set<number>();
  onExpandChange(id: number, checked: boolean): void {
    if (checked) {
      this.expandSet.add(id);
    } else {
      this.expandSet.delete(id);
    }
  }

  constructor(private dropTransactionService: DropTransactionService,
    private message: NzMessageService) { }

  ngOnInit(): void {
    this.getTransactions();
  }

  getTransactions():void {
    this.dropTransactionService.getAllDropTransactions(this.page -1 + "", this.pageSize + "").subscribe(
      async (response: any) => {
        if(response != null) {
          const {transactions, totalItems} = response;
          this.listOfTransactions = transactions;
          this.listOfTransactions = [...this.listOfTransactions];
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
    this.getTransactions();
  }

  handlePageSizeChange(event: any): void {
    this.pageSize = event.value;
    this.page = 1;
    this.getTransactions();
  }
}
