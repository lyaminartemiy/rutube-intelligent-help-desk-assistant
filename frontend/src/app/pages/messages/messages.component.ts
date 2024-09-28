import {Component, OnInit, signal} from '@angular/core';
import {FormControl} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {MessageDto, SendMessage, Side} from "../../core/models/models";
import {map, take} from "rxjs";
import {RutubeService} from "../../core/services/rutube.service";
import {NotifierService} from "angular-notifier";

@Component({
  selector: 'app-messages',
  templateUrl: './messages.component.html',
  styleUrls: ['./messages.component.scss']
})
export class MessagesComponent implements OnInit{
  input = new FormControl<string>('');
  messages: MessageDto[] = [];
  message:MessageDto;
  canIEdit  = signal<number>(0);
  isEditable = signal(false);
  id = signal<string>('')
constructor(protected activatedRoute: ActivatedRoute, private router: Router,private rutubeService:RutubeService, private notifierService: NotifierService) {
}
  ngOnInit(): void {
    this.canIEdit.set(this.activatedRoute.snapshot.data['assigned'] ?? 0);
    console.log(this.canIEdit())
    this.id.set(this.activatedRoute.snapshot.paramMap.get('id')!);
    this.activatedRoute.data
        .pipe(
            map(({ messages}) => { return {messages: messages }}))
        .subscribe(result => {
          this.messages = result.messages;
          this.message = this.messages.at(-1)!
          this.input.setValue(this.message.messageText)
        });
  }
  onSubmit(event:  KeyboardEvent) {
    // console.log(this.input.value);
    //   this.input.reset();
    //
    // if(event.shiftKey) console.log('alallaslasldas')
    event.preventDefault();
    if (event.keyCode == 13 && event.shiftKey) {
      event.preventDefault();
      event.stopPropagation();
    }
    else if(event.key === 'Enter') {
      event.preventDefault();
      console.log(this.input.value);
      this.input.reset();
    }
  }
  submit() {
    console.log(this.input.value);
    this.input.reset();
  }

  assign(id: string) {
    if(id) {
      this.rutubeService.assign(id).pipe(take(1)).subscribe({
        next: (r) => {
          this.notifierService.notify('success', 'Вы успешно присоединились к обращению!');
          this.canIEdit.set(1);
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
  goToId(id: string, isAssigned: number) {
    this.router.navigate([`/messages/${id}/${isAssigned}`]);
  }

  aproove() {
    let mes: SendMessage;
    if(this.message.messageText === this.input.value) {
      mes = {
        text: this.message.messageText,
        isEditedByTechSupport: false
      }
    }
    else {
      mes = {
        text: this.input.value as string,
        isEditedByTechSupport: true
      }
    }
    this.rutubeService.send(this.id(),mes).pipe(take(1)).subscribe({
      next: (r) => {
        this.notifierService.notify('success', 'Вы успешно закрыли обращение!');
        this.router.navigate([`/all`]);
      },
      error: (err) => {
        console.log(err)
        this.notifierService.notify('error', 'Произошла ошибка, попробуйте снова!');
      },
      complete: () => {
      }})
    
  }

  edit() {
    if(this.message.side===Side.BOT || this.message.side===Side.TECH_SUPPORT_EMPLOYEE) this.isEditable.set(true)
  }

  protected readonly Side = Side;
}
