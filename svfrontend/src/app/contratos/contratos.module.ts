import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from '../material/material.module';
import { SharedModule } from '../shared/shared.module';
import { ContratosLayoutComponent } from './layout/contratos-layout/contratos-layout.component';
import { ContratosPageComponent } from './pages/contratos-page/contratos-page.component';
import { ContratosRoutingModule } from './contratos-routing.module';
import { AddComponent } from './components/add/add.component';
import { ModifyComponent } from './components/modify/modify.component';



@NgModule({
  declarations: [
    ContratosLayoutComponent,
    ContratosPageComponent,
    AddComponent,
    ModifyComponent
  ],
  imports: [
    CommonModule,
    ContratosRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    MaterialModule,
    SharedModule
  ]
})
export class ContratosModule { }
