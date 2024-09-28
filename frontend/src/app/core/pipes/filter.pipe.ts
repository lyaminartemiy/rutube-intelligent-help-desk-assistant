import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'filter',
  standalone: true
})
export class FilterPipe implements PipeTransform {
  transform<T>(array: Array<T>, prop: keyof T, value: any): Array<T> {
    if ((array === undefined || array === null) && value!='' ) return [];

    return array.filter(item => item[prop] === value);
  }

}
