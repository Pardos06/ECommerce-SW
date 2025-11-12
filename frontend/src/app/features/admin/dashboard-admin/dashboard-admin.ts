import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet, RouterLink, RouterLinkActive } from '@angular/router';
import { MenubarModule } from 'primeng/menubar';
import { PanelMenuModule } from 'primeng/panelmenu';
import { ButtonModule } from 'primeng/button';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-dashboard-admin',
  standalone: true,
  imports: [
    CommonModule,
    RouterOutlet,
    MenubarModule,
    PanelMenuModule,
    ButtonModule
  ],
  templateUrl: './dashboard-admin.html',
  styleUrls: ['./dashboard-admin.scss']
})
export class DashboardAdmin {
    sidebarVisible = true; 

  menuItems: MenuItem[] = [
    { label: 'Dashboard', icon: 'pi pi-th-large', routerLink: ['/admin'] },
    {
      label: 'Gestión de Productos',
      icon: 'pi pi-tags',
      items: [
        { label: 'Lista de productos', icon: 'pi pi-list', routerLink: ['/admin/productos'] },
        { label: 'Agregar producto', icon: 'pi pi-plus', routerLink: ['/admin/productos/nuevo'] }
      ]
    },
    {
      label: 'Categorías',
      icon: 'pi pi-sitemap',
      items: [
        { label: 'Ver categorías', icon: 'pi pi-list', routerLink: ['/admin/categorias'] },
        { label: 'Agregar categoría', icon: 'pi pi-plus', routerLink: ['/admin/categorias/nueva'] }
      ]
    },
    { label: 'Métodos de Pago', icon: 'pi pi-credit-card', routerLink: ['/admin/metodos-pago'] },
    {
      label: 'Usuarios',
      icon: 'pi pi-users',
      items: [
        { label: 'Usuarios', icon: 'pi pi-user', routerLink: ['/admin/usuarios/tipos'] },
        { label: 'Lista de Clientes', icon: 'pi pi-list', routerLink: ['/admin/usuarios'] }
      ]
    },
    {
      label: 'Proveedores',
      icon: 'pi pi-users',
      items: [
        { label: 'Mayoristas', icon: 'pi pi-user', routerLink: ['/admin/usuarios/tipos'] },
        { label: 'Minoristas', icon: 'pi pi-user', routerLink: ['/admin/usuarios'] },
        { label: 'Fabricante', icon: 'pi pi-user', routerLink: ['/admin/usuarios'] }
      ]
    },
    { label: 'Reportes', icon: 'pi pi-chart-line', routerLink: ['/admin/reportes'] },
    { separator: true },
    
  ];

  toggleSidebar() {
    this.sidebarVisible = !this.sidebarVisible;
  }

  logout() {
    localStorage.removeItem('token');
    window.location.href = '/login';
  }
}