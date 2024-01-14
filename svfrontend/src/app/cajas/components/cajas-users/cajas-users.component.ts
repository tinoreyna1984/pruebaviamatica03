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
  users:any = [];
  constructor(
    @Inject(MAT_DIALOG_DATA) private data: any,
  ){
    this.cajaId = this.data.id;
    console.log(this.data.users);
  }
  public dataSource: MatTableDataSource<any> = new MatTableDataSource<any>([]);
  displayedColumns: string[] = [
    'id',
    'username',
    'email',
    'role',
    'userCreator',
    'userStatus',
  ];

  ngOnInit(): void {
    this.users = this.data.users.map((u:any) => u.user);
    this.dataSource = new MatTableDataSource<any>(this.users);
  }
}
