import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DropTransactionComponent } from './drop-transaction.component';

const routes: Routes = [
  { path: '', component: DropTransactionComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DropTransactionRoutingModule { }
