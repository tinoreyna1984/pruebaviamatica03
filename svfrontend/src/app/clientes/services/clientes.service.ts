import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { TokenValuesService } from 'src/app/auth/services/token-values.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ClientesService {

  private baseUrl: string = environment.baseUrl;
  private headers: any;

  constructor(
    private http: HttpClient,
    private tokenValuesService: TokenValuesService
  ) {
    this.headers = this.tokenValuesService.headers();
   }

   listarClientes(): Observable<any>{
    return this.http.get<any>(`${this.baseUrl}/clientes`, this.headers)
    .pipe(
      map((res: any) => res)
    );
  }

  buscarCliente(id: number){
    return this.http.get<any>(`${this.baseUrl}/clientes/${id}`, this.headers)
    .pipe(
      map((res: any) => res)
    );
  }

  guardarCliente(req: any){
    return this.http.post<any>(`${this.baseUrl}/clientes`, req, this.headers)
    .pipe(
      map((response: any) => response)
    );
  }

  editarCliente(id: number, req: any){
    return this.http.put<any>(`${this.baseUrl}/clientes/${id}`, req, this.headers)
    .pipe(
      map((response: any) => response)
    );
  }

  borrarCliente(id: number){
    return this.http.delete<any>(`${this.baseUrl}/clientes/${id}`, this.headers)
    .pipe(
      map((res: any) => res)
    );
  }

  cargarDesdeCSV(req: any){
    return this.http.post<any>(`${this.baseUrl}/clientes/csv`, req, this.headers)
    .pipe(
      map((response: any) => response)
    );
  }
}
