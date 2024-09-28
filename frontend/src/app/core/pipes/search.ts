import {Pipe, PipeTransform} from "@angular/core";

@Pipe({
  name: 'search',
  standalone: true
})
export class SearchPipe implements PipeTransform {
  transform<T>(array: Array<T>, prop: keyof T, value: any): Array<T> {
    if (array === undefined || array === null) return [];
    if(value == '') return array;

    return array.filter(it => {
      const val = it[prop];
      return (typeof val === "string" && val.toLowerCase().includes(value.toLowerCase()))
    });
  }

}
