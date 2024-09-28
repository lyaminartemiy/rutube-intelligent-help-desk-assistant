import {Observable, Subject} from "rxjs";
import {Injectable} from "@angular/core";


@Injectable({
    providedIn: 'root',
})
export class WebSocketService {
    private socket: WebSocket;
    private messages: Subject<any>;

    constructor() {
        this.messages = new Subject<any>();
    }

    // Подключение к WebSocket
    connect(url: string): void {
        this.socket = new WebSocket(url);

        // Обработка сообщений
        this.socket.onmessage = (event) => {
            const message = JSON.parse(event.data);
            this.messages.next(message);
        };

        // Обработка закрытия соединения
        this.socket.onclose = (event) => {
            console.log('WebSocket connection closed', event);
        };

        // Обработка ошибок
        this.socket.onerror = (error) => {
            console.error('WebSocket error', error);
        };
    }

    // Метод для отправки сообщений на сервер
    sendMessage(msg: any): void {
        this.socket.send(JSON.stringify(msg));
    }

    // Получение сообщений как Observable
    getMessages(): Observable<any> {
        return this.messages.asObservable();
    }

    // Закрытие соединения
    close(): void {
        if (this.socket) {
            this.socket.close();
        }
    }
}