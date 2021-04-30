import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'transaction/drop' },
  { path: 'devices', loadChildren: () => import('./pages/devices/devices.module').then(m => m.DevicesModule) },
  { path: 'merchants', loadChildren: () => import('./pages/merchants/merchants.module').then(m => m.MerchantsModule) },
  { path: 'transaction/drop', loadChildren: () => import('./pages/transactions/drop/drop-transaction/drop-transaction.module').then(m => m.DropTransactionModule) }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
