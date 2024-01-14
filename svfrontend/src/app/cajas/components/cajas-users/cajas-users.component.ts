import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-cajas-users',
  templateUrl: './cajas-users.component.html',
  styleUrls: ['./cajas-users.component.css']
})
export class CajasUsersComponent implements OnInit {
  cajaId: number = 0;
  constructor(
    @Inject(MAT_DIALOG_DATA) private data: any,
  ){
    this.cajaId = this.data.id;
  }
  public dataSource: MatTableDataSource<any> = new MatTableDataSource<any>(this.data.users);
  displayedColumns: string[] = [
    'id',
    'username',
    'email',
    'role',
    'userCreator',
    'userStatus',
  ];

  ngOnInit(): void {
    //this.users = this.data;
  }
}
