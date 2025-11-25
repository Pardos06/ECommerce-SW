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
      label: 'Productos',
      icon: 'pi pi-tags',
      items: [
        { label: 'Lista de productos', icon: 'pi pi-list', routerLink: ['/admin/productos'] },
        { label: 'Gestionar Categorías', icon: 'pi pi-list', routerLink: ['/admin/categorias'] }
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
        { label: 'Proveedor', icon: 'pi pi-user', routerLink: ['/admin/proveedor'] },
        { label: 'Tipo de Proveedor', icon: 'pi pi-user', routerLink: ['/admin/tipo-proveedor'] }
      ]
    }
    
  ];

  toggleSidebar() {
    this.sidebarVisible = !this.sidebarVisible;
  }

  logout() {
    localStorage.removeItem('token');
    window.location.href = '/login';
  }
}