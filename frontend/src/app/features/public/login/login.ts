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

      // 🔹 Guarda datos y verifica inmediatamente
      this.authService.guardarToken(res.token);
      this.authService.guardarRol(res.rol);
      localStorage.setItem('nombre', res.nombre);

      console.log('Token en localStorage:', localStorage.getItem('jwt_token'));
      console.log('Rol en localStorage:', localStorage.getItem('rol'));

      // 🔹 Espera al siguiente ciclo del event loop (no microtask)
      requestAnimationFrame(() => {
        const rol = res.rol?.toUpperCase();
        if (rol === 'ADMINISTRADOR' || rol === 'ADMIN') {
          console.log('Navegando a /admin...');
          this.router.navigate(['/admin']);
        } else {
          console.log('Navegando a /cliente...');
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
