import { Component } from '@angular/core';
import { AtencionesService } from '../../services/atenciones.service';
import { TokenValuesService } from 'src/app/auth/services/token-values.service';
import { HelperService } from 'src/app/shared/services/helper.service';
import { FormControl, FormGroup } from '@angular/forms';
import Swal from 'sweetalert2';
import { ClientesService } from 'src/app/clientes/services/clientes.service';
import { CajasService } from 'src/app/cajas/services/cajas.service';

@Component({
  selector: 'app-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.css'],
})
export class AddComponent {
  form: FormGroup;
  clientes: any;
  cajas: any;

  constructor(
    private atencionesService: AtencionesService,
    private clientesService: ClientesService,
    private cajasService: CajasService,
    private tokenValuesService: TokenValuesService,
    private helperService: HelperService
  ) {
    this.form = new FormGroup({
      attentionType: new FormControl(),
      attentionStatus: new FormControl(),
      cajaId: new FormControl(),
      clienteId: new FormControl(),
    });
    this.loadCajas();
    this.loadClientes();
  }

  loadCajas() {
    this.cajasService.listarCajas().subscribe({
      next: (res: any) => {
        if (res.httpCode < 400) {
          this.cajas = res.data;
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

  validaFormulario(form: any): boolean{
    const attentionType = form.get('attentionType')?.value;
    const attentionStatus = form.get('attentionStatus')?.value;
    const cajaIdValue = form.get('cajaId')?.value;
    const clienteIdValue = form.get('clienteId')?.value;
    if(!attentionType || !attentionStatus || !cajaIdValue || !clienteIdValue){
      Swal.fire('Error', 'No debe estar en blanco', 'error');
      return false;
    }
    return true;
  }

  add() {
    if(!this.validaFormulario(this.form)) return;
    this.atencionesService.guardarAtencion(this.form.value).subscribe({
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
