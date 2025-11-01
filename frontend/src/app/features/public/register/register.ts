import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Auth } from '../../../core/services/auth';
import { RegistrarClienteRequest } from '../../../core/interfaces/registrar-cliente.request';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-register',
  imports: [CommonModule, ReactiveFormsModule],
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
    private router: Router
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
    if (this.form.invalid) return;

    const request: RegistrarClienteRequest = this.form.value;
    this.cargando = true;
    this.errorMsg = '';
    this.successMsg = '';

    this.authService.register(request).subscribe({
      next: (res) => {
        this.cargando = false;
        this.successMsg = 'Registro exitoso. Redirigiendo al inicio de sesión...';
        setTimeout(() => this.router.navigate(['/login']), 2000);
      },
      error: (err) => {
        this.cargando = false;
        this.errorMsg = err.error?.mensaje || 'Error al registrar. Intenta nuevamente.';
      }
    });
  }
}