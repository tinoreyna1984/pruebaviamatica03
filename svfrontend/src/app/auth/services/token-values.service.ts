import { Injectable } from '@angular/core';
import jwt_decode from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class TokenValuesService {

  isLoggedIn(): boolean {
    const jwt: any = localStorage.getItem('jwt');
    return jwt !== null;
  }

  getToken(): any {
    const jwt: any = localStorage.getItem('jwt');
    const token: any = jwt_decode(jwt);
    return token;
  }

  getRole(): any{
    const token: any = this.getToken();
    return token.role;
  }

  getRoutes(): any{
    const token: any = this.getToken();
    return token.routes;
  }

  getUsername(): any{
    const token: any = this.getToken();
    return token.username;
  }

}
