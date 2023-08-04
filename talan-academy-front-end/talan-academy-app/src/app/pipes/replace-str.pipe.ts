import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'replaceStr',
})
export class ReplaceStrPipe implements PipeTransform {
  transform(value: string): string {
    return value.replace('\\', '/');
  }
}
