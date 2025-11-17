import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Auth } from '../../../core/services/auth';
import { AuthRequest } from '../../../core/interfaces/auth.request';
import { MessageService } from 'primeng/api';

// PrimeNG modules
import { CardModule } from 'primeng/card';
import { InputTextModule } from 'primeng/inputtext';
import { InputGroupModule } from 'primeng/inputgroup';
import { ButtonModule } from 'primeng/button';
import { PasswordModule } from 'primeng/password';
import { ToastModule } from 'primeng/toast';
import { PrimeImportsModule } from '../../../prime-imports';

@Component({
  selector: 'app-login',
  standalone: true,
  templateUrl: './login.html',
  styleUrls: ['./login.scss'],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule,
    CardModule,
    InputTextModule,
    InputGroupModule,
    ButtonModule,
    PasswordModule,
    ToastModule,
    PrimeImportsModule
  ],
  providers: [MessageService]
})
export class LoginComponent {
  form: FormGroup;
  cargando = false;
  errorMsg = '';

  constructor(
    private fb: FormBuilder,
    private authService: Auth,
    private router: Router,
    private messageService: MessageService
  ) {
    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      contrasena: ['', Validators.required]
    });
  }

  login(): void {
    if (this.form.invalid) {
      this.messageService.add({
        severity: 'warn',
        summary: 'Formulario incompleto',
        detail: 'Por favor ingrese sus credenciales.',
        life: 3000
      });
      return;
    }

    const request: AuthRequest = this.form.value;
    this.cargando = true;
    this.errorMsg = '';

    this.authService.login(request).subscribe({
      next: (res) => {
        this.cargando = false;
        this.authService.guardarToken(res.token);
        this.authService.guardarRol(res.rol);
        localStorage.setItem('nombre', res.nombre);

        this.messageService.add({
          severity: 'success',
          summary: '¡Bienvenido!',
          detail: `Hola ${res.nombre}, inicio de sesión exitoso.`,
          life: 2000
        });

        requestAnimationFrame(() => {
          const rol = res.rol?.toUpperCase();
          setTimeout(() => {
            if (rol === 'ADMINISTRADOR' || rol === 'ADMIN') {
              this.router.navigate(['/admin']);
            } else {
              this.router.navigate(['/cliente']);
            }
          }, 1000);
        });
      },
      error: (err) => {
        this.cargando = false;
        this.messageService.add({
          severity: 'error',
          summary: 'Error de autenticación',
          detail: err.error?.mensaje || 'Credenciales incorrectas. Por favor intenta nuevamente.',
          life: 4000
        });
      }
    });
  }
}
