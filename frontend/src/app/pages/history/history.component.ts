import { Component } from '@angular/core';
import {FormControl} from "@angular/forms";
import {RutubeService} from "../../core/services/rutube.service";

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.scss']
})
export class HistoryComponent {
  name = new FormControl<string>('')
  constructor(private rutubeService: RutubeService) {
  }
  all = this.rutubeService.getHistory();
}
