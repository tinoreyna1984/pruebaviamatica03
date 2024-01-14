import { Component } from '@angular/core';
import { TokenValuesService } from 'src/app/auth/services/token-values.service';
import { HelperService } from 'src/app/shared/services/helper.service';
import { FormControl, FormGroup } from '@angular/forms';
import Swal from 'sweetalert2';
import { CajasService } from 'src/app/cajas/services/cajas.service';

@Component({
  selector: 'app-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.css']
})
export class AddComponent {

  form: FormGroup;
  
  constructor(
    private cajasService: CajasService,
    private tokenValuesService: TokenValuesService,
    private helperService: HelperService
  ) {
    this.form = new FormGroup({
      descripcion: new FormControl(),
      active: new FormControl(),
    });
    this.form.get('active')!.setValue(true);
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

  add(){
    if(!this.validaFormulario(this.form)) return;
    this.cajasService.guardarCaja(this.form.value).subscribe({
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
