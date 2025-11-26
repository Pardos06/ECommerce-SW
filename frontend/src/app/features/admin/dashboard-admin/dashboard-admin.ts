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
    {
      label: 'Compras',
      icon: 'pi pi-users',
      items: [
        { label: 'Métodos de Pago', icon: 'pi pi-credit-card', routerLink: ['/admin/metodos-pago'] },
      ]
    },
    
    {
      label: 'Usuarios',
      icon: 'pi pi-users',
      items: [
        { label: 'Usuarios', icon: 'pi pi-user', routerLink: ['/admin/usuarios/tipos'] },
        { label: 'Gestión de Roles', icon: 'pi pi-list', routerLink: ['/admin/roles'] }
      ]
    },
    {
      label: 'Proveedor',
      icon: 'pi pi-users',
      items: [
        { label: 'Proveedores', icon: 'pi pi-user', routerLink: ['/admin/proveedor'] },
        { label: 'Tipo de Proveedor', icon: 'pi pi-user', routerLink: ['/admin/tipo-proveedor'] }
      ]
    },
    {
      label: 'Recursos Humanos',
      icon: 'pi pi-users',
      items: [
        { label: 'Áreas', icon: 'pi pi-user', routerLink: ['/admin/areas'] },
        { label: 'Cargos', icon: 'pi pi-user', routerLink: ['/admin/cargos'] }
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