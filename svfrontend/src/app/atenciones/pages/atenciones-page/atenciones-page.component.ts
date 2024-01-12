import { Component, OnInit, ViewChild } from '@angular/core';
import { AtencionesService } from '../../services/atenciones.service';
import { TokenValuesService } from 'src/app/auth/services/token-values.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-atenciones-page',
  templateUrl: './atenciones-page.component.html',
  styleUrls: ['./atenciones-page.component.css'],
})
export class AtencionesPageComponent implements OnInit {
  constructor(
    private atencionesService: AtencionesService,
    private tokenValuesService: TokenValuesService,
    private snackBar: MatSnackBar
  ) {}

  public dataSource: MatTableDataSource<any> = new MatTableDataSource<any>([]);
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  loading: boolean = false;
  displayedColumns: string[] = [
    'id',
    'attentionType',
    'descripcion',
    'attentionStatus',
    'cliente',
    'modificar',
    'borrar',
  ];

  ngOnInit(): void {
    this.loading = true;
    this.atencionesService.listarAtenciones().subscribe({
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
