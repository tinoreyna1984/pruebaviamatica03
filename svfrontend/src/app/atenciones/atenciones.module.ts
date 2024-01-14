import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from '../material/material.module';
import { SharedModule } from '../shared/shared.module';
import { AtencionesLayoutComponent } from './layout/atenciones-layout/atenciones-layout.component';
import { AtencionesPageComponent } from './pages/atenciones-page/atenciones-page.component';
import { AtencionesRoutingModule } from './atenciones-routing.module';
import { AddComponent } from './components/add/add.component';
import { ModifyComponent } from './components/modify/modify.component';



@NgModule({
  declarations: [
    AtencionesLayoutComponent,
    AtencionesPageComponent,
    AddComponent,
    ModifyComponent
  ],
  imports: [
    CommonModule,
    AtencionesRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    MaterialModule,
    SharedModule
  ]
})
export class AtencionesModule { }
