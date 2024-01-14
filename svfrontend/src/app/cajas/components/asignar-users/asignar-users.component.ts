import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { UsersService } from 'src/app/users/services/users.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-asignar-users',
  templateUrl: './asignar-users.component.html',
  styleUrls: ['./asignar-users.component.css'],
})
export class AsignarUsersComponent implements OnInit {
  cajaId: number = 0;
  usuarioId: number = 0;
  numUsuarios: number = 0;
  usuarios: any = [];
  constructor(
    @Inject(MAT_DIALOG_DATA) private data: any,
    private usersService: UsersService
  ) {
    this.cajaId = this.data.id;
    this.numUsuarios = this.data.users.length;
  }
  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers() {
    this.usersService.listarUsuarios().subscribe({
      next: (res: any) => {
        if (res.httpCode < 400) {
          this.usuarios = res.data.filter((u: any) => u.role === 'USER');
          console.log(this.usuarios);
        } else {
          Swal.fire('Error HTTP ' + res.httpCode, res.message, 'error');
        }
      },
      error: (e: any) => {
        console.log(e);
        Swal.fire(
          'Error inesperado',
          'Por favor, contacta con el administrador.',
          'error'
        );
      },
    });
  }

  asignar(userId: number, cajaId: number) {
    if(!userId) return;
    console.log({ userId, cajaId });
    this.usersService.asignarUsuarioACaja({ userId, cajaId }).subscribe({
      next: (res: any) => {
        if (res.httpCode < 400) {
          this.loadUsers();
          Swal.fire('Asignación a caja ' + cajaId, res.message, 'success');
        } else {
          Swal.fire('Error HTTP ' + res.httpCode, res.message, 'error');
        }
      },
      error: (e: any) => {
        console.log(e);
        Swal.fire(
          'Error inesperado',
          'Por favor, contacta con el administrador.',
          'error'
        );
      },
    });
  }
}
