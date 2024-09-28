import { Component } from '@angular/core';
import {RutubeService} from "../../core/services/rutube.service";

@Component({
  selector: 'app-my-appeals',
  templateUrl: './my-appeals.component.html',
  styleUrls: ['./my-appeals.component.scss']
})
export class MyAppealsComponent {
  constructor(private rutubeService: RutubeService) {
  }
  all = this.rutubeService.getMyRequests();
}
