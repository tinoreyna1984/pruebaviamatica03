import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CajasPageComponent } from './pages/cajas-page/cajas-page.component';
import { CajasLayoutComponent } from './layout/cajas-layout/cajas-layout.component';

const routes: Routes = [
    {
      path: '',
      component: CajasLayoutComponent,
      children: [
        {
          path: '',
          component: CajasPageComponent,
        },
      ],
    },
  ];
  
  @NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
  })
  export class CajasRoutingModule {}
  