import { Component, OnInit, ViewChild } from '@angular/core';
import { TokenValuesService } from 'src/app/auth/services/token-values.service';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import Swal from 'sweetalert2';
import { UsersService } from '../../services/users.service';
import { HelperService } from 'src/app/shared/services/helper.service';

@Component({
  selector: 'app-users-page',
  templateUrl: './users-page.component.html',
  styleUrls: ['./users-page.component.css'],
})
export class UsersPageComponent implements OnInit {
  constructor(
    private usersService: UsersService,
    private tokenValuesService: TokenValuesService,
    private helperService: HelperService,
  ) {}

  public dataSource: MatTableDataSource<any> = new MatTableDataSource<any>([]);
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  loading: boolean = false;
  displayedColumns: string[] = [
    'id',
    'username',
    'email',
    'role',
    'creationDate',
    'userCreator',
    'approvalDate',
    'userStatus',
    'aprobar',
    'modificar',
    'borrar',
  ];

  ngOnInit(): void {
    this.loading = true;
    this.load();
  }

  load() {
    this.usersService.listarUsuarios().subscribe({
      next: (res: any) => {
        if (res.httpCode < 400) {
          this.dataSource = new MatTableDataSource<any>(res.data);
          this.dataSource.paginator = this.paginator;
        } else {
          Swal.fire('Error HTTP ' + res.httpCode, res.message, 'error');
        }
        if (this.loading) this.loading = false;
      },
      error: (e: any) => {
        console.log(e);
        Swal.fire(
          'Error inesperado',
          'Por favor, contacta con el administrador.',
          'error'
        );
        if (this.loading) this.loading = false;
      },
    });
  }

  onDelete(id: number) {
    Swal.fire({
      title: '¿Desea borrar al usuario?',
      showDenyButton: true,
      confirmButtonText: 'Sí',
      denyButtonText: 'No',
    }).then((res) => {
      if (res.isConfirmed) {
        this.loading = true;
        this.usersService.borrarUsuario(id).subscribe({
          next: (res: any) => {
            if (res.httpCode < 400) {
              this.helperService.snackBarMsg(res.message, 3500);
              this.load();
            } else {
              Swal.fire('Error ' + res.httpCode, res.message, 'error');
              this.loading = false;
            }
          },
          error: (e: any) => {
            console.log(e);
            Swal.fire(
              'Error inesperado',
              'Por favor, contacta con el administrador.',
              'error'
            );
            if (this.loading) this.loading = false;
          },
        });
      } else if (res.isDenied) {
        return;
      }
    });
  }
}
