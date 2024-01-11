import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from '../material/material.module';
import { SharedModule } from '../shared/shared.module';
import { ClientesLayoutComponent } from './layout/clientes-layout/clientes-layout.component';
import { ClientesPageComponent } from './pages/clientes-page/clientes-page.component';
import { ClientesRoutingModule } from './clientes-routing.module';



@NgModule({
  declarations: [
    ClientesLayoutComponent,
    ClientesPageComponent
  ],
  imports: [
    CommonModule,
    ClientesRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    MaterialModule,
    SharedModule
  ]
})
export class ClientesModule { }
