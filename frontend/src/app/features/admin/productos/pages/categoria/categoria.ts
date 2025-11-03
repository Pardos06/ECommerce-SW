import { Component, OnInit } from '@angular/core';
import { Categoria } from '../../interfaces/categoria';
import { CategoriaService } from '../../services/categoria';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-categoria-page',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './categoria.html',
  styleUrl: './categoria.scss'
})
export class CategoriaPage implements OnInit {
  categorias: Categoria[] = [];
  cargando: boolean = true;

  constructor(private categoriaService: CategoriaService) { }

  ngOnInit(): void {
    this.categoriaService.listarCategorias().subscribe({
      next: (data) => {
        this.categorias = data;
        this.cargando = false;
      },
      error: (err) => {
        console.error('Error al cargar categorías', err);
        this.cargando = false;
      }
    });
  }
}
