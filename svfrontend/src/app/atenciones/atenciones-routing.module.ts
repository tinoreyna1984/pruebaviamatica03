import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AtencionesLayoutComponent } from './layout/atenciones-layout/atenciones-layout.component';
import { AtencionesPageComponent } from './pages/atenciones-page/atenciones-page.component';

const routes: Routes = [
  {
    path: '',
    component: AtencionesLayoutComponent,
    children: [
      {
        path: '',
        component: AtencionesPageComponent,
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AtencionesRoutingModule {}
