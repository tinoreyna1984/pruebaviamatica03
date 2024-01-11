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
  styleUrls: ['./contratos-page.component.css']
})
export class ContratosPageComponent {
  constructor(
    private contratosService: ContratosService,
    private tokenValuesService: TokenValuesService,
    private snackBar: MatSnackBar
  ){}

  public dataSource: MatTableDataSource<any> = new MatTableDataSource<any>([]);
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  loading: boolean = false;
}
