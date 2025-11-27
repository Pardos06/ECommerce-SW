import { Routes } from '@angular/router';
import { DashboardAdmin } from './dashboard-admin/dashboard-admin';
import { ProductosPage } from './productos/pages/productos/productos';
import { CategoriaPage } from './productos/pages/categoria/categoria';
import { MetodoPagoPage } from './compras/pages/metodo-pago/metodo-pago';
import { ProveedorPage } from './proveedores/pages/proveedor/proveedor';
import { TipoProveedorPage } from './proveedores/pages/tipo-proveedor/tipo-proveedor';
import { RolPage } from './usuarios/pages/rol/rol';
import { AreaPage } from './rr_hh/pages/area/area';
import { CargoPage } from './rr_hh/pages/cargo/cargo';
import { UsuariosPage } from './usuarios/pages/usuario/usuario';

export const ADMIN_ROUTES: Routes = [
  {
    path: '',
    component: DashboardAdmin,
    children: [
      { 
        path: '',
        redirectTo: 'categorias',
        pathMatch: 'full' 
      },

      { 
        path: 'productos',
        component: ProductosPage
      },

      { 
        path: 'categorias',
        component: CategoriaPage
      },

      { 
        path: 'metodos-pago',
        component: MetodoPagoPage 
      },
      {
        path: 'proveedor',
        component: ProveedorPage
      },
      {
        path: 'tipo-proveedor',
        component: TipoProveedorPage
      },
      {
        path: 'roles',
        component: RolPage
      },
       {
        path: 'areas',
        component: AreaPage
      },
      {
        path: 'cargos',
        component: CargoPage  
      },
      {
        path: 'usuarios',
        component: UsuariosPage
      }
      
    ],
  },
];