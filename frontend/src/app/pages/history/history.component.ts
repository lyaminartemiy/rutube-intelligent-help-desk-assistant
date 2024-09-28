import { Component } from '@angular/core';
import {FormControl} from "@angular/forms";
import {RutubeService} from "../../core/services/rutube.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.scss']
})
export class HistoryComponent {
  name = new FormControl<string>('')
  constructor(private rutubeService: RutubeService, private router: Router) {
  }
  all = this.rutubeService.getHistory();

  goToId(id: string, isAssigned: number) {
    this.router.navigate([`/messages/${id}/${isAssigned}`]);
  }
}
