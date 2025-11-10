import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Auth } from '../../../core/services/auth';
import { AuthRequest } from '../../../core/interfaces/auth.request';
import { FloatLabelModule } from 'primeng/floatlabel';
import { PasswordModule } from 'primeng/password';


// PrimeNG modules
import { CardModule } from 'primeng/card';
import { InputTextModule } from 'primeng/inputtext';
import { InputGroupModule } from 'primeng/inputgroup';
import { ButtonModule } from 'primeng/button';
import { MessageModule } from 'primeng/message';
import { PrimeImportsModule } from '../../../prime-imports';
@Component({
  selector: 'app-login',
  standalone: true,
  templateUrl: './login.html',
  styleUrls: ['./login.scss'],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    CardModule,
    InputTextModule,
    InputGroupModule,
    ButtonModule,
    MessageModule,
    PrimeImportsModule
  ]
})
export class LoginComponent {
  form: FormGroup;
  cargando = false;
  errorMsg = '';

  constructor(
    private fb: FormBuilder,
    private authService: Auth,
    private router: Router
  ) {
    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      contrasena: ['', Validators.required]
    });
  }

  login(): void {
    if (this.form.invalid) return;

    const request: AuthRequest = this.form.value;
    this.cargando = true;
    this.errorMsg = '';

    this.authService.login(request).subscribe({
      next: (res) => {
        this.cargando = false;
        this.authService.guardarToken(res.token);
        this.authService.guardarRol(res.rol);
        localStorage.setItem('nombre', res.nombre);

        requestAnimationFrame(() => {
          const rol = res.rol?.toUpperCase();
          if (rol === 'ADMINISTRADOR' || rol === 'ADMIN') {
            this.router.navigate(['/admin']);
          } else {
            this.router.navigate(['/cliente']);
          }
        });
      },
      error: (err) => {
        this.cargando = false;
        this.errorMsg = err.error?.mensaje || 'Credenciales incorrectas.';
      }
    });
  }
}