import { Component, Inject } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import Swal from 'sweetalert2';
import { TokenValuesService } from 'src/app/auth/services/token-values.service';
import { HelperService } from 'src/app/shared/services/helper.service';
import { CajasService } from '../../services/cajas.service';

@Component({
  selector: 'app-modify',
  templateUrl: './modify.component.html',
  styleUrls: ['./modify.component.css']
})
export class ModifyComponent {
  form: FormGroup;

  constructor(
    @Inject(MAT_DIALOG_DATA) private data: any,
    private cajasService: CajasService,
    private tokenValuesService: TokenValuesService,
    private helperService: HelperService
  ) {
    this.form = new FormGroup({
      descripcion: new FormControl(),
      active: new FormControl(),
    });
    this.form.get('descripcion')!.setValue(this.data.descripcion);
    this.form.get('active')!.setValue(this.data.active);
  }
  
  validaFormulario(form: any): boolean{
    const descripcion = form.get('descripcion')?.value;
    const regex = /\bCAJA\d{4}\b/;
    if(!descripcion || !regex.test(descripcion)){
      Swal.fire('Error', 'La descripción no debe estar en blanco y debe componerse de la palabra CAJA con cuatro dígitos.', 'error');
      return false;
    }
    return true;
  }

  modify(){
    if(!this.validaFormulario(this.form)) return;
    this.cajasService.editarCaja(this.data.id, this.form.value).subscribe({
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
