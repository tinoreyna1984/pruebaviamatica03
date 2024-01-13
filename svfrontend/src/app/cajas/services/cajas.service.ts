import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { TokenValuesService } from 'src/app/auth/services/token-values.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CajasService {

  private baseUrl: string = environment.baseUrl;
  private headers: any;

  constructor(
    private http: HttpClient,
    private tokenValuesService: TokenValuesService
  ) {
    this.headers = this.tokenValuesService.headers();
   }

   listarCajas(): Observable<any>{
    return this.http.get<any>(`${this.baseUrl}/cajas`, this.headers)
    .pipe(
      map((res: any) => res)
    );
  }

  buscarCaja(id: number){
    return this.http.get<any>(`${this.baseUrl}/cajas/${id}`, this.headers)
    .pipe(
      map((res: any) => res)
    );
  }

  guardarCaja(req: any){
    return this.http.post<any>(`${this.baseUrl}/cajas`, req, this.headers)
    .pipe(
      map((response: any) => response)
    );
  }

  editarCaja(id: number, req: any){
    return this.http.put<any>(`${this.baseUrl}/cajas/${id}`, req, this.headers)
    .pipe(
      map((response: any) => response)
    );
  }

  borrarCaja(id: number){
    return this.http.delete<any>(`${this.baseUrl}/cajas/${id}`, this.headers)
    .pipe(
      map((res: any) => res)
    );
  }

}
