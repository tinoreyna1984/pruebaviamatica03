import { Component, inject } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent {
  private authService = inject(AuthService);
  private router = inject(Router);

  form: FormGroup;
  loading: boolean = false;
  errorMsg: string = '';

  constructor() {
    this.form = new FormGroup({
      username: new FormControl(),
      password: new FormControl(),
    });
  }

  async onLogin(){
    if (this.loading) {
      return; // Evita múltiples solicitudes si ya se está cargando
    }
    this.loading = true;
    try {
      const res: any = await this.authService.login(this.form.value);
      console.log(res);
    } catch (error: any) {
      console.log(error);
    } finally{
      this.loading = false;
    }
  }
}
