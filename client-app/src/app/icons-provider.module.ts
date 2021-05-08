import { NgModule } from '@angular/core';
import { NZ_ICONS, NzIconModule } from 'ng-zorro-antd/icon';

import {
  MenuFoldOutline,
  MenuUnfoldOutline,
  CrownTwoTone,
  FolderOpenTwoTone,
  TeamOutline,
  ShopOutline,
  EditOutline,
  DeleteOutline,
  EllipsisOutline,
  BellTwoTone
} from '@ant-design/icons-angular/icons';

const icons = [MenuFoldOutline, MenuUnfoldOutline, FolderOpenTwoTone, CrownTwoTone, TeamOutline, ShopOutline, EditOutline, DeleteOutline, EllipsisOutline,BellTwoTone];

@NgModule({
  imports: [NzIconModule],
  exports: [NzIconModule],
  providers: [
    { provide: NZ_ICONS, useValue: icons }
  ]
})
export class IconsProviderModule {
}
