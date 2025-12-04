import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { Auth } from '../services/auth';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const auth = inject(Auth);
  const token = auth.obtenerToken();

  console.log("🔥 INTERCEPTOR EJECUTADO");
  console.log("🔥 TOKEN OBTENIDO:", token);

  if (token) {
    const cloned = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });

    console.log("🔥 HEADERS ENVIADOS:", cloned.headers);

    return next(cloned);
  }

  console.warn("⚠️ NO HAY TOKEN → petición se envía sin Authorization");
  return next(req);
};
