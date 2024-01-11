import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ContratosLayoutComponent } from './layout/contratos-layout/contratos-layout.component';
import { ContratosPageComponent } from './pages/contratos-page/contratos-page.component';

const routes: Routes = [
  {
    path: '',
    component: ContratosLayoutComponent,
    children: [
      {
        path: '',
        component: ContratosPageComponent,
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ContratosRoutingModule {}
