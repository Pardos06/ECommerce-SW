import { Component, OnInit, OnDestroy, HostListener } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet, RouterLink, RouterLinkActive } from '@angular/router';
import { MenubarModule } from 'primeng/menubar';
import { PanelMenuModule } from 'primeng/panelmenu';
import { ButtonModule } from 'primeng/button';
import { MenuItem } from 'primeng/api';
import { Header } from '../../../shared/components/header/header';
import { Footer } from '../../../shared/components/footer/footer';


@Component({
  selector: 'app-dashboard-admin',
  standalone: true,
  imports: [
    CommonModule,
    RouterOutlet,
    MenubarModule,
    PanelMenuModule,
    ButtonModule,
    Header,
    Footer
  ],
  templateUrl: './dashboard-admin.html',
  styleUrls: ['./dashboard-admin.scss']
})
export class DashboardAdmin implements OnInit, OnDestroy {
  sidebarVisible = true;
  isMobile = false;
  isTablet = false; 

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
      icon: 'pi pi-shopping-cart',
      items: [
        { label: 'Gestión de Compras', icon: 'pi pi-list', routerLink: ['/admin/compras'] },
        { label: 'Detalles de Compras', icon: 'pi pi-file-edit', routerLink: ['/admin/compra-detalles'] },
        { label: 'Métodos de Pago', icon: 'pi pi-credit-card', routerLink: ['/admin/metodos-pago'] }
      ]
    },
    {
      label: 'Ventas',
      icon: 'pi pi-users',
      items: [
        { label: 'Ventas', icon: 'pi pi-user', routerLink: ['/admin/ventas'] },
      ]
    },
    {
      label: 'Usuarios',
      icon: 'pi pi-users',
      items: [
        { label: 'Usuarios', icon: 'pi pi-user', routerLink: ['/admin/usuarios'] },
        { label: 'Empleados', icon: 'pi pi-id-card', routerLink: ['/admin/empleados'] },
        { label: 'Clientes', icon: 'pi pi-list', routerLink: ['/admin/clientes'] },
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

  ngOnInit(): void {
    this.checkScreenSize();
  }

  ngOnDestroy(): void {
    // Cleanup si es necesario
  }

  @HostListener('window:resize', ['$event'])
  onResize(event: any): void {
    this.checkScreenSize();
  }

  checkScreenSize(): void {
    const width = window.innerWidth;
    this.isMobile = width < 768;
    this.isTablet = width >= 768 && width < 992;
    
    // En móviles y tablets, el sidebar inicia oculto
    if (this.isMobile || this.isTablet) {
      this.sidebarVisible = false;
    } else {
      this.sidebarVisible = true;
    }
  }

  toggleSidebar(): void {
    this.sidebarVisible = !this.sidebarVisible;
  }

  closeSidebar(): void {
    // Cerrar sidebar al hacer click en el overlay (solo en móviles/tablets)
    if (this.isMobile || this.isTablet) {
      this.sidebarVisible = false;
    }
  }

  logout(): void {
    localStorage.removeItem('token');
    window.location.href = '/login';
  }
}