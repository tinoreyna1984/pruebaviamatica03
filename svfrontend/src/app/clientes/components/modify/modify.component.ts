import { Component, Inject } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import Swal from 'sweetalert2';
import { TokenValuesService } from 'src/app/auth/services/token-values.service';
import { HelperService } from 'src/app/shared/services/helper.service';
import { ClientesService } from '../../services/clientes.service';

@Component({
  selector: 'app-modify',
  templateUrl: './modify.component.html',
  styleUrls: ['./modify.component.css']
})
export class ModifyComponent {
  form: FormGroup;

  constructor(
    @Inject(MAT_DIALOG_DATA) private data: any,
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
    
    this.form.get('name')!.setValue(this.data.name);
    this.form.get('lastName')!.setValue(this.data.lastName);
    this.form.get('docId')!.setValue(this.data.docId);
    this.form.get('email')!.setValue(this.data.email);
    this.form.get('phone')!.setValue(this.data.phone);
    this.form.get('address')!.setValue(this.data.address);
    this.form.get('refAddress')!.setValue(this.data.refAddress);
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

  modify(){
    if(!this.validaFormulario(this.form)) return;
    this.clientesService.editarCliente(this.data.id, this.form.value).subscribe({
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
