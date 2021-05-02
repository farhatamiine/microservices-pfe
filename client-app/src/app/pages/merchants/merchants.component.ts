import { Component, OnInit } from '@angular/core';
import { NzMessageService } from 'ng-zorro-antd/message';
import { Subscription } from 'rxjs';
import { MerchantModel } from 'src/app/models/merchant-model';
import { MerchantService } from 'src/app/services/merchant/merchant.service';

@Component({
  selector: 'app-merchants',
  templateUrl: './merchants.component.html',
  styleUrls: ['./merchants.component.scss']
})
export class MerchantsComponent implements OnInit {

  public subscription: Subscription = new Subscription;

  // device data
  listOfMerchants: MerchantModel[] = [];
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
    private merchantService: MerchantService,
    private message: NzMessageService) { }

  ngOnInit(): void {
    this.getMerchants();
    // set subscribe to message service
    this.subscription = this.merchantService.checkMerchantAdded().subscribe(msg => this.getMerchants());
  }
  public ngOnDestroy(): void {
    this.subscription.unsubscribe(); // onDestroy cancels the subscribe request
  }


  getMerchants():void {
    this.merchantService.getAllMerchants(this.page -1 + "", this.pageSize + "").subscribe(
      async (response: any) => {
        if(response != null) {
          const {merchants, totalItems} = response;
          this.listOfMerchants = merchants;
          this.listOfMerchants = [...this.listOfMerchants];
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
    this.getMerchants();
  }

  handlePageSizeChange(event: any): void {
    this.pageSize = event.value;
    this.page = 1;
    this.getMerchants();
  }

}
