import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { TokenValuesService } from 'src/app/auth/services/token-values.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ServiciosService {

  private baseUrl: string = environment.baseUrl;
  private headers: any;

  constructor(
    private http: HttpClient,
    private tokenValuesService: TokenValuesService
  ) {
    this.headers = this.tokenValuesService.headers();
   }

   listarServicios(): Observable<any>{
    return this.http.get<any>(`${this.baseUrl}/servicios`, this.headers)
    .pipe(
      map((res: any) => res)
    );
  }

  buscarServicio(id: number){
    return this.http.get<any>(`${this.baseUrl}/servicios/${id}`, this.headers)
    .pipe(
      map((res: any) => res)
    );
  }

  guardarServicio(req: any){
    return this.http.post<any>(`${this.baseUrl}/servicios`, req, this.headers)
    .pipe(
      map((response: any) => response)
    );
  }

  editarServicio(id: number, req: any){
    return this.http.put<any>(`${this.baseUrl}/servicios/${id}`, req, this.headers)
    .pipe(
      map((response: any) => response)
    );
  }

  borrarServicio(id: number){
    return this.http.delete<any>(`${this.baseUrl}/servicios/${id}`, this.headers)
    .pipe(
      map((res: any) => res)
    );
  }

}
