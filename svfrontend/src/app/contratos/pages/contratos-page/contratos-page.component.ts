import { Component, OnInit, ViewChild } from '@angular/core';
import { TokenValuesService } from 'src/app/auth/services/token-values.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import Swal from 'sweetalert2';
import { ContratosService } from '../../services/contratos.service';

@Component({
  selector: 'app-contratos-page',
  templateUrl: './contratos-page.component.html',
  styleUrls: ['./contratos-page.component.css'],
})
export class ContratosPageComponent {
  constructor(
    private contratosService: ContratosService,
    private tokenValuesService: TokenValuesService,
    private snackBar: MatSnackBar
  ) {}

  public dataSource: MatTableDataSource<any> = new MatTableDataSource<any>([]);
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  loading: boolean = false;
  displayedColumns: string[] = [
    'id',
    'fechaInicioContrato',
    'fechaFinContrato',
    'contractStatus',
    'paymentMethod',
    'servicio',
    'cliente',
    'modificar',
    'borrar',
  ];

  ngOnInit(): void {
    this.loading = true;
    this.contratosService.listarContratos().subscribe({
      next: (res: any) => {
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
}
