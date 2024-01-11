import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ClientesLayoutComponent } from './layout/clientes-layout/clientes-layout.component';
import { ClientesPageComponent } from './pages/clientes-page/clientes-page.component';

const routes: Routes = [
  {
    path: '',
    component: ClientesLayoutComponent,
    children: [
      {
        path: '',
        component: ClientesPageComponent,
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ClientesRoutingModule {}
