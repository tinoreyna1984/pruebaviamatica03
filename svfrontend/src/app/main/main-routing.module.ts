import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MainLayoutComponent } from './layout/main-layout/main-layout.component';
import { MainPageComponent } from './pages/main-page/main-page.component';

const routes: Routes = [
    {
      path: '',
      component: MainLayoutComponent,
      children: [
        {
          path: '',
          component: MainPageComponent,
        },
        {
          path: 'attentions',
          loadChildren: () => import('../atenciones/atenciones.module').then( m => m.AtencionesModule ),
        },
        {
          path: 'customers',
          loadChildren: () => import('../clientes/clientes.module').then( m => m.ClientesModule ),
        },
        {
          path: 'contracts',
          loadChildren: () => import('../contratos/contratos.module').then( m => m.ContratosModule ),
        },
        {
          path: 'dashboard',
          loadChildren: () => import('../dashboard/dashboard.module').then( m => m.DashboardModule ),
        },
        {
          path: 'users',
          loadChildren: () => import('../users/users.module').then( m => m.UsersModule ),
        },
        {
          path: '**',
          redirectTo: '',
        },
      ]
    },
  ];
  
  @NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
  })
  export class MainRoutingModule { }