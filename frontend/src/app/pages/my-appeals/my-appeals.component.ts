import {Component, OnInit} from '@angular/core';
import {RutubeService} from "../../core/services/rutube.service";
import {Observable} from "rxjs";
import {TechSupportRequestDto} from "../../core/models/models";

@Component({
  selector: 'app-my-appeals',
  templateUrl: './my-appeals.component.html',
  styleUrls: ['./my-appeals.component.scss']
})
export class MyAppealsComponent implements OnInit{
  constructor(private rutubeService: RutubeService) {
  }
  all: TechSupportRequestDto[] = []

    closeAppeal() {
      this.getAppeals()
    }

  ngOnInit(): void {
    this.getAppeals()
  }
  getAppeals() {
    this.rutubeService.getMyRequests().subscribe(res => {
      if(res) this.all = res
    })
  }
}
