import { Pipe, PipeTransform } from '@angular/core';

@Pipe({ name: 'fileUrl' })
export class FileUrlPipe implements PipeTransform {
  transform(file: File | null | undefined): string | null {
    return file ? URL.createObjectURL(file) : null;
  }
}
