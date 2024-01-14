import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from '../material/material.module';
import { SharedModule } from '../shared/shared.module';
import { UsersLayoutComponent } from './layout/users-layout/users-layout.component';
import { UsersPageComponent } from './pages/users-page/users-page.component';
import { UsersRoutingModule } from './users-routing.module';
import { AddComponent } from './components/add/add.component';
import { ModifyComponent } from './components/modify/modify.component';



@NgModule({
  declarations: [
    UsersLayoutComponent,
    UsersPageComponent,
    AddComponent,
    ModifyComponent
  ],
  imports: [
    CommonModule,
    UsersRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    MaterialModule,
    SharedModule
  ]
})
export class UsersModule { }
