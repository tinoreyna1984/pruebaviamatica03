import { Component, OnInit } from '@angular/core';
import { AtencionesService } from 'src/app/atenciones/services/atenciones.service';
import { TokenValuesService } from 'src/app/auth/services/token-values.service';
import { HelperService } from 'src/app/shared/services/helper.service';
import { UsersService } from 'src/app/users/services/users.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.css'],
})
export class MainPageComponent implements OnInit {

  isAdmin: boolean = false;
  isManager: boolean = false;
  isUser: boolean = true;
  loading: boolean = false;
  asignadosPorGestor: number = 0;
  atenciones: any = [];
  numAtenciones: number = 0;
  usuariosNoAprobados: any = [];

  username: string = '';
  role: string = '';

  constructor(
    private usersService: UsersService,
    private atencionesService: AtencionesService,
    private tokenValuesService: TokenValuesService,
    private helperService: HelperService
  ) {
    this.isAdmin = this.tokenValuesService.isAdmin();
    this.isManager = this.tokenValuesService.isManager();
    this.isUser = !this.isAdmin && !this.isManager;
    this.username = this.tokenValuesService.getUsername();
    this.role = this.tokenValuesService.getRole();
  }
  ngOnInit(): void {
    this.loadAsignadoPor();
    this.loadAtenciones();
    this.loadUsuariosNoAprobados();
  }

  loadAsignadoPor() {
    this.loading = true;
    this.usersService
      .asignadoPor(this.tokenValuesService.getUsername())
      .subscribe({
        next: (res: any) => {
          if (res.httpCode < 400) {
            this.asignadosPorGestor = res.data;
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

  loadAtenciones() {
    this.loading = true;
    this.atencionesService.listarAtenciones().subscribe({
      next: (res: any) => {
        if (res.httpCode < 400) {
          this.atenciones = res.data;
          this.numAtenciones = this.atenciones.length;
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

  loadUsuariosNoAprobados() {
    this.loading = true;
    this.usersService.listarUsuarios().subscribe({
      next: (res: any) => {
        if (res.httpCode < 400) {
          console.log(res.data);
          this.usuariosNoAprobados = res.data.filter((u: any) => u.userStatus === 'NOT_APPROVED').length;
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

  refrescar(){
    this.loadAsignadoPor();
    this.loadAtenciones();
    this.loadUsuariosNoAprobados();
  }
}
