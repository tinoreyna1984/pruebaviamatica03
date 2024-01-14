import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CajasLayoutComponent } from './layout/cajas-layout/cajas-layout.component';
import { CajasPageComponent } from './pages/cajas-page/cajas-page.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from '../material/material.module';
import { SharedModule } from '../shared/shared.module';
import { CajasRoutingModule } from './cajas-routing.module';
import { AddComponent } from './components/add/add.component';
import { ModifyComponent } from './components/modify/modify.component';
import { CajasUsersComponent } from './components/cajas-users/cajas-users.component';
import { AsignarUsersComponent } from './components/asignar-users/asignar-users.component';



@NgModule({
  declarations: [
    CajasLayoutComponent,
    CajasPageComponent,
    AddComponent,
    ModifyComponent,
    CajasUsersComponent,
    AsignarUsersComponent
  ],
  imports: [
    CommonModule,
    CajasRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    MaterialModule,
    SharedModule
  ]
})
export class CajasModule { }
