import {Component, EventEmitter, Input, Output} from '@angular/core';
import {TechSupportRequestDto} from "../../core/models/models";
import {RutubeService} from "../../core/services/rutube.service";
import {take} from "rxjs";
import {NotifierService} from "angular-notifier";
import {Router} from "@angular/router";

@Component({
  selector: 'app-appeal',
  templateUrl: './appeal.component.html',
  styleUrls: ['./appeal.component.scss']
})
export class AppealComponent {
  @Input() item: TechSupportRequestDto;
  @Input() all: boolean;
  @Output() close = new EventEmitter();
  constructor(private rutubeService: RutubeService, private notifierService:NotifierService, private router: Router) {
  }

    closes(id: string) {
        this.rutubeService.close(id).pipe(take(1)).subscribe({
          next: (r) => {
            this.notifierService.notify('success', 'Обращение успешно закрыто!');
            this.close.emit();
          },
          error: (err) => {
            console.log(err)
            this.notifierService.notify('error', 'Произошла ошибка, попробуйте снова!');
          },
          complete: () => {
          }})
    }

  goToId(id: string, isAssigned: number) {
    this.router.navigate([`/messages/${id}/${isAssigned}`]);
  }

  assign(id: string) {
    this.rutubeService.assign(id).pipe(take(1)).subscribe({
      next: (r) => {
        this.notifierService.notify('success', 'Вы успешно присоединились к обращению!');
        this.goToId(id, 1);
      },
      error: (err) => {
        console.log(err)
        this.notifierService.notify('error', 'Произошла ошибка, попробуйте снова!');
      },
      complete: () => {
      }})
  }
}
