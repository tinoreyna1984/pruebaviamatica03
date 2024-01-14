import { Component } from '@angular/core';
import { TokenValuesService } from 'src/app/auth/services/token-values.service';
import { HelperService } from 'src/app/shared/services/helper.service';
import { FormControl, FormGroup } from '@angular/forms';
import Swal from 'sweetalert2';
import { ClientesService } from '../../services/clientes.service';

@Component({
  selector: 'app-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.css']
})
export class AddComponent {

  form: FormGroup;
  
  constructor(
    private clientesService: ClientesService,
    private tokenValuesService: TokenValuesService,
    private helperService: HelperService
  ) {
    this.form = new FormGroup({
      name: new FormControl(),
      lastName: new FormControl(),
      docId: new FormControl(),
      email: new FormControl(),
      phone: new FormControl(),
      address: new FormControl(),
      refAddress: new FormControl(),
    });
  }

  validaFormulario(form: any): boolean{
    const name = form.get('name')?.value;
    const lastName = form.get('lastName')?.value;
    const docId = form.get('docId')?.value;
    const email = form.get('email')?.value;
    const phone = form.get('phone')?.value;
    const address = form.get('address')?.value;
    const refAddress = form.get('refAddress')?.value;
    if(!name || !lastName || !docId || !email || !phone || !address || !refAddress){
      Swal.fire('Error', 'Se debe llenar todos los datos.', 'error');
      return false;
    }
    return true;
  }

  add(){
    if(!this.validaFormulario(this.form)) return;
    this.clientesService.guardarCliente(this.form.value).subscribe({
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
