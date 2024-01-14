import { Component, OnInit } from '@angular/core';
import { CajasService } from 'src/app/cajas/services/cajas.service';
import { HelperService } from 'src/app/shared/services/helper.service';
import { UsersService } from 'src/app/users/services/users.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-dashboard-page',
  templateUrl: './dashboard-page.component.html',
  styleUrls: ['./dashboard-page.component.css'],
})
export class DashboardPageComponent implements OnInit {
  usuarios: any = [];
  cajas: any = [];
  loading: boolean = false;

  constructor(
    private usersService: UsersService,
    private cajasService: CajasService,
    private helperService: HelperService,
    ) {
  }

  ngOnInit(): void {
    this.loadUsers();
    this.loadCajas();
  }

  loadUsers(){
    this.loading = true;
    this.usersService.getUsersDashboard().subscribe({
      next: (res: any) => {
        if (res.httpCode < 400) {
          this.usuarios = res.data;
        } else {
          Swal.fire('Error ' + res.httpCode, res.message, 'error');
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

  loadCajas(){
    this.loading = true;
    this.cajasService.listarCajas().subscribe({
      next: (res: any) => {
        if (res.httpCode < 400) {
          console.log(res.data);
          this.cajas = res.data;
        } else {
          Swal.fire('Error ' + res.httpCode, res.message, 'error');
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

  load(){
    this.loadUsers();
    this.loadCajas();
  }

}
