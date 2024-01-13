import { Component, OnInit, ViewChild } from '@angular/core';
import { AtencionesService } from '../../services/atenciones.service';
import { TokenValuesService } from 'src/app/auth/services/token-values.service';
import { HelperService } from 'src/app/shared/services/helper.service';
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
    private helperService: HelperService,
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
    this.load();
  }
  
  setDatasource(dataSource: any) {
    this.dataSource = dataSource;
    this.dataSource.paginator = this.paginator;
  }

  load() {
    this.atencionesService.listarAtenciones().subscribe({
      next: (res: any) => {
        if (res.httpCode < 400) {
          this.setDatasource(new MatTableDataSource<any>(res.data));
        } else {
          Swal.fire('Error HTTP ' + res.httpCode, res.message, 'error');
        }
        if (this.loading) this.loading = false;
      },
      error: (e: any) => {
        console.log(e);
        Swal.fire(
          'Error inesperado',
          'Por favor, contacta con el administrador.',
          'error'
        );
        if (this.loading) this.loading = false;
      },
    });
  }

  deleteItem(id: number) {
    this.atencionesService.borrarAtencion(id).subscribe({
      next: (res: any) => {
        if (res.httpCode < 400) {
          this.helperService.snackBarMsg(res.message, 3500);
          this.load();
        } else {
          Swal.fire('Error ' + res.httpCode, res.message, 'error');
          if (this.loading) this.loading = false;
        }
      },
      error: (e: any) => {
        console.log(e);
        Swal.fire(
          'Error inesperado',
          'Por favor, contacta con el administrador.',
          'error'
        );
        if (this.loading) this.loading = false;
      },
    });
  }

  onDelete(id: number) {
    Swal.fire({
      title: '¿Desea borrar la atención?',
      showDenyButton: true,
      confirmButtonText: 'Sí',
      denyButtonText: 'No',
    }).then((res) => {
      if (res.isConfirmed) {
        this.loading = true;
        this.deleteItem(id);
      } else if (res.isDenied) {
        return;
      }
    });
  }
}
