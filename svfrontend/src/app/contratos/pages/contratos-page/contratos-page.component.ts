import { Component, OnInit, ViewChild } from '@angular/core';
import { TokenValuesService } from 'src/app/auth/services/token-values.service';
import { ContratosService } from '../../services/contratos.service';
import { HelperService } from 'src/app/shared/services/helper.service';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import Swal from 'sweetalert2';
import { MatDialog } from '@angular/material/dialog';
import { AddComponent } from '../../components/add/add.component';

@Component({
  selector: 'app-contratos-page',
  templateUrl: './contratos-page.component.html',
  styleUrls: ['./contratos-page.component.css'],
})
export class ContratosPageComponent {
  constructor(
    private contratosService: ContratosService,
    private tokenValuesService: TokenValuesService,
    private helperService: HelperService,
    private dialog: MatDialog
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
    this.load();
  }
  
  setDatasource(dataSource: any) {
    this.dataSource = dataSource;
    this.dataSource.paginator = this.paginator;
  }

  load() {
    this.contratosService.listarContratos().subscribe({
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
    this.contratosService.borrarContrato(id).subscribe({
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

  onAdd(){
    const dialogRef = this.helperService.openDialog(AddComponent, this.dialog, 250, 250);

    dialogRef.afterClosed().subscribe(
      () => {
        this.loading = true;
        setTimeout(() => {}, 1800);
        this.load();
      }
    );
  }

  onDelete(id: number) {
    Swal.fire({
      title: '¿Desea borrar el contrato?',
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
