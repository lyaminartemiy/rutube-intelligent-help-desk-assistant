import {Component, OnDestroy, OnInit} from '@angular/core';
import {RutubeService} from "../../core/services/rutube.service";
import {WebSocketService} from "../../core/services/web-socket.service";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-all-appeals',
  templateUrl: './all-appeals.component.html',
  styleUrls: ['./all-appeals.component.scss']
})
export class AllAppealsComponent implements OnInit{
  constructor(private rutubeService: RutubeService, private webSocketService: WebSocketService) {
  }
  all = this.rutubeService.getRequests();
  private subscription: Subscription;

  ngOnInit(): void {
    // Подключение к WebSocket серверу
    this.webSocketService.connect('ws://193.124.47.226:8080/tech-support-requests'); // URL вашего WebSocket сервера

    // Подписка на сообщения WebSocket
    this.all= this.webSocketService.getMessages()
  }
}
