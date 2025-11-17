import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { Auth } from '../../../core/services/auth';
import { RegistrarClienteRequest } from '../../../core/interfaces/registrar-cliente.request';
import { CommonModule } from '@angular/common';
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
  selector: 'app-register',
  standalone: true,
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
  providers: [MessageService],
  templateUrl: './register.html',
  styleUrl: './register.scss'
})
export class RegisterComponent {
  form: FormGroup;
  cargando = false;
  errorMsg = '';
  successMsg = '';
  currentYear = new Date().getFullYear();

  constructor(
    private fb: FormBuilder,
    private authService: Auth,
    private router: Router,
    private messageService: MessageService
  ) {
    this.form = this.fb.group({
      nombre: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      contrasena: ['', [Validators.required, Validators.minLength(6)]],
      telefono: ['', [Validators.required, Validators.pattern('^[0-9]{9}$')]],
      direccion: ['', Validators.required]
    });
  }

  registrar(): void {
    if (this.form.invalid) {
      this.messageService.add({
        severity: 'warn',
        summary: 'Formulario incompleto',
        detail: 'Por favor complete todos los campos correctamente.',
        life: 3000
      });
      return;
    }

    const request: RegistrarClienteRequest = this.form.value;
    this.cargando = true;
    this.errorMsg = '';
    this.successMsg = '';

    this.authService.register(request).subscribe({
      next: (res) => {
        this.cargando = false;
        this.messageService.add({
          severity: 'success',
          summary: '¡Registro exitoso!',
          detail: 'Tu cuenta ha sido creada. Redirigiendo al inicio de sesión...',
          life: 3000
        });
        setTimeout(() => this.router.navigate(['/login']), 2000);
      },
      error: (err) => {
        this.cargando = false;
        this.messageService.add({
          severity: 'error',
          summary: 'Error al registrar',
          detail: err.error?.mensaje || 'Ocurrió un error. Por favor intenta nuevamente.',
          life: 4000
        });
      }
    });
  }
}
