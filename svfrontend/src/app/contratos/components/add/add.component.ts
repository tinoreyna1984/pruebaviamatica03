import { Component } from '@angular/core';
import { TokenValuesService } from 'src/app/auth/services/token-values.service';
import { HelperService } from 'src/app/shared/services/helper.service';
import { FormControl, FormGroup } from '@angular/forms';
import Swal from 'sweetalert2';
import { ClientesService } from 'src/app/clientes/services/clientes.service';
import { ServiciosService } from 'src/app/servicios/services/servicios.service';
import { ContratosService } from '../../services/contratos.service';

@Component({
  selector: 'app-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.css'],
})
export class AddComponent {
  form: FormGroup;
  clientes: any;
  servicios: any;

  constructor(
    private contratosService: ContratosService,
    private serviciosService: ServiciosService,
    private clientesService: ClientesService,
    private tokenValuesService: TokenValuesService,
    private helperService: HelperService
  ) {
    this.form = new FormGroup({
      fechaInicioContrato: new FormControl(),
      fechaFinContrato: new FormControl(),
      contractStatus: new FormControl(),
      paymentMethod: new FormControl(),
      servicioId: new FormControl(),
      clienteId: new FormControl(),
    });
    this.loadClientes();
    this.loadServicios();
    this.form.get('contractStatus')!.setValue('VIG');
  }

  loadClientes() {
    this.clientesService.listarClientes().subscribe({
      next: (res: any) => {
        if (res.httpCode < 400) {
          this.clientes = res.data;
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

  loadServicios() {
    this.serviciosService.listarServicios().subscribe({
      next: (res: any) => {
        if (res.httpCode < 400) {
          this.servicios = res.data;
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

  validaFormulario(form: any): boolean {
    const fechaInicioContrato = form.get('fechaInicioContrato')?.value;
    const fechaFinContrato = form.get('fechaFinContrato')?.value;
    const paymentMethod = form.get('paymentMethod')?.value;
    const servicioId = form.get('servicioId')?.value;
    const clienteId = form.get('clienteId')?.value;
    if (
      !fechaInicioContrato ||
      !fechaFinContrato ||
      !paymentMethod ||
      !servicioId ||
      !clienteId
    ) {
      Swal.fire('Error', 'Se debe llenar todos los datos.', 'error');
      return false;
    }
    return true;
  }

  add() {
    if (!this.validaFormulario(this.form)) return;

    // el formato requerido para el backend
    this.form.get('fechaInicioContrato')?.setValue(this.form.get('fechaInicioContrato')?.value.replace('T', ' '));
    this.form.get('fechaFinContrato')?.setValue(this.form.get('fechaFinContrato')?.value.replace('T', ' '));
    
    //console.log(this.form.value);
    this.contratosService.guardarContrato(this.form.value).subscribe({
      next: (res: any) => {
        if (res.httpCode < 400) {
          this.helperService.snackBarMsg(res.message, 3500);
        } else {
          Swal.fire('Error ' + res.httpCode, res.message, 'error');
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
