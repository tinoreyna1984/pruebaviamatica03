import { Component, Inject } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import Swal from 'sweetalert2';
import { TokenValuesService } from 'src/app/auth/services/token-values.service';
import { HelperService } from 'src/app/shared/services/helper.service';
import { AtencionesService } from '../../services/atenciones.service';
import { ClientesService } from 'src/app/clientes/services/clientes.service';
import { CajasService } from 'src/app/cajas/services/cajas.service';

@Component({
  selector: 'app-modify',
  templateUrl: './modify.component.html',
  styleUrls: ['./modify.component.css']
})
export class ModifyComponent {
  form: FormGroup;
  clientes: any;

  constructor(
    @Inject(MAT_DIALOG_DATA) private data: any,
    private atencionesService: AtencionesService,
    private clientesService: ClientesService,
    private cajasService: CajasService,
    private tokenValuesService: TokenValuesService,
    private helperService: HelperService
  ) {
    this.form = new FormGroup({
      attentionType: new FormControl(),
      attentionStatus: new FormControl(),
      clienteId: new FormControl(),
    });
    console.log(this.data);
    this.loadClientes();
    this.form.get('attentionType')!.setValue(this.data.attentionType);
    this.form.get('attentionStatus')!.setValue(this.data.attentionStatus);
    this.form.get('clienteId')!.setValue(this.data.cliente.id);
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
    const clienteIdValue = form.get('clienteId')?.value;
    if(!attentionType || !attentionStatus || !clienteIdValue){
      Swal.fire('Error', 'No debe estar en blanco', 'error');
      return false;
    }
    return true;
  }

  modify() {
    if(!this.validaFormulario(this.form)) return;
    this.atencionesService.editarAtencion(this.data.id, this.form.value).subscribe({
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
