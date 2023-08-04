import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'subDescription',
})
export class SubDescriptionPipe implements PipeTransform {
  // transform(value: unknown, ...args: unknown[]): unknown {
  //   return null;
  // }
  transform(value: string): string {
    if (value.length > 150) {
      return value.substring(0, 150) + '...';
    } else {
      return value;
    }
  }
}
