import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { TokenValuesService } from 'src/app/auth/services/token-values.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AtencionesService {

  private baseUrl: string = environment.baseUrl;
  private headers: any;

  constructor(
    private http: HttpClient,
    private tokenValuesService: TokenValuesService
  ) {
    this.headers = this.tokenValuesService.headers();
   }

  listarAtenciones(): Observable<any>{
    return this.http.get<any>(`${this.baseUrl}/atenciones`, this.headers)
    .pipe(
      map((res: any) => res)
    );
  }

  buscarAtencion(id: number){
    return this.http.get<any>(`${this.baseUrl}/atenciones/${id}`, this.headers)
    .pipe(
      map((res: any) => res)
    );
  }

  guardarAtencion(req: any){
    return this.http.post<any>(`${this.baseUrl}/atenciones`, req, this.headers)
    .pipe(
      map((response: any) => response)
    );
  }

  editarAtencion(id: number, req: any){
    return this.http.patch<any>(`${this.baseUrl}/atenciones/${id}`, req, this.headers)
    .pipe(
      map((response: any) => response)
    );
  }

  borrarAtencion(id: number){
    return this.http.delete<any>(`${this.baseUrl}/atenciones/${id}`, this.headers)
    .pipe(
      map((res: any) => res)
    );
  }

}
