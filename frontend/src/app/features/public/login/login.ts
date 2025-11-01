import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Auth } from '../../../core/services/auth';
import { AuthRequest } from '../../../core/interfaces/auth.request';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';


@Component({
  selector: 'app-login',
  templateUrl: './login.html',
  styleUrls: ['./login.scss'],
  imports: [CommonModule, ReactiveFormsModule], 
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
    console.log('LOGIN OK, response:', res);
    this.authService.guardarToken(res.token);
    this.authService.guardarRol(res.rol);
    console.log('token guardado:', this.authService.obtenerToken());
    console.log('rol guardado:', this.authService.obtenerRol());
    localStorage.setItem('nombre', res.nombre);

    setTimeout(() => {
      if (res.rol === 'Administrador') {
        this.router.navigate(['/admin']);
      } else {
        this.router.navigate(['/cliente']);
      }
    }, 100);
  },
  error: (err) => {
    this.cargando = false;
    this.errorMsg = err.error?.mensaje || 'Credenciales incorrectas.';
  }
});

  }
}
