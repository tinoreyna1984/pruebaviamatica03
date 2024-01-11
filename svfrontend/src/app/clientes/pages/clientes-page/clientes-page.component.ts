import { Component, OnInit, ViewChild } from '@angular/core';
import { TokenValuesService } from 'src/app/auth/services/token-values.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import Swal from 'sweetalert2';
import { ClientesService } from '../../services/clientes.service';

@Component({
  selector: 'app-clientes-page',
  templateUrl: './clientes-page.component.html',
  styleUrls: ['./clientes-page.component.css']
})
export class ClientesPageComponent implements OnInit {

  constructor(
    private clientesService: ClientesService,
    private tokenValuesService: TokenValuesService,
    private snackBar: MatSnackBar
  ) {}

  public dataSource: MatTableDataSource<any> = new MatTableDataSource<any>([]);
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  archivo: any = null;
  loading: boolean = false;
  displayedColumns: string[] = [
    'id',
    'name',
    'lastName',
    'docId',
    'email',
    'phone',
    'address',
    'refAddress',
    'modificar',
    'borrar',
  ];

  ngOnInit(): void {
    this.loading = true;
    this.clientesService.listarClientes().subscribe({
      next: (res: any) => {
        console.log(res.data);
        if (res.httpCode < 400) {
          this.dataSource = new MatTableDataSource<any>(res.data);
          this.dataSource.paginator = this.paginator;
        } else {
          Swal.fire('Error HTTP ' + res.httpCode, res.message, 'error');
        }
        this.loading = false;
      },
      error: (e: any) => {
        console.log(e);
        Swal.fire(
          'Error inesperado',
          'Por favor, contacta con el administrador.',
          'error'
        );
        this.loading = false;
      },
    });
  }

  onFileSelected(event: any): void {
    this.archivo = event.target.files[0];
  }
}
