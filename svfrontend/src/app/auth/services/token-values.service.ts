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

  getUsername(): any{
    const token: any = this.getToken();
    return token.username;
  }

  getRole(): any{
    const token: any = this.getToken();
    return token.role;
  }

  isAdmin(): boolean {
    return this.getRole() === 'ADMINISTRATOR';
  }

  getPermissions():any {
    const token: any = this.getToken();
    return token.permissions;
  }

  hasPermission(p: string): boolean {
    const permissions = this.getPermissions();
    return permissions.includes(p);
  }

  getRoutes(): any{
    const token: any = this.getToken();
    return token.routes;
  }

  getTokenLifetime(): any {
    const token: any = this.getToken();
    const issuedAt = token.iat;
    const expires = token.exp;
    return [issuedAt, expires]; // fechas de acceso otorgado y de expiración
  }

}
