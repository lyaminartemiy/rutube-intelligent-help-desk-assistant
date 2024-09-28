import {Component, Input} from '@angular/core';
import {MessageDto, Side} from "../../core/models/models";

@Component({
  selector: 'app-one-message',
  templateUrl: './one-message.component.html',
  styleUrls: ['./one-message.component.scss']
})
export class OneMessageComponent {
  @Input() message: MessageDto;

  protected readonly Side = Side;
}
