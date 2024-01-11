import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { TokenValuesService } from 'src/app/auth/services/token-values.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ContratosService {

  private baseUrl: string = environment.baseUrl;
  private headers: any;

  constructor(
    private http: HttpClient,
    private tokenValuesService: TokenValuesService
  ) {
    this.headers = this.tokenValuesService.headers();
   }

   listarContratos(): Observable<any>{
    return this.http.get<any>(`${this.baseUrl}/contratos`, this.headers)
    .pipe(
      map((res: any) => res)
    );
  }

  buscarContrato(id: number){
    return this.http.get<any>(`${this.baseUrl}/contratos/${id}`, this.headers)
    .pipe(
      map((res: any) => res)
    );
  }

  guardarContrato(req: any){
    return this.http.post<any>(`${this.baseUrl}/contratos`, req, this.headers)
    .pipe(
      map((response: any) => response)
    );
  }

  editarContrato(id: number, req: any){
    return this.http.put<any>(`${this.baseUrl}/contratos/${id}`, req, this.headers)
    .pipe(
      map((response: any) => response)
    );
  }

  borrarContrato(id: number){
    return this.http.delete<any>(`${this.baseUrl}/contratos/${id}`, this.headers)
    .pipe(
      map((res: any) => res)
    );
  }

}
