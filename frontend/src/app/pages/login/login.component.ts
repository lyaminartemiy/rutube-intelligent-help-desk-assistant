import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {RutubeService} from "../../core/services/rutube.service";
import {take} from "rxjs";
import {NotifierService} from "angular-notifier";
import {Router} from "@angular/router";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit{
  loginForm: FormGroup;
  loginError = false;

  constructor( private fb: FormBuilder, private rutubeService: RutubeService, private router: Router,
  private notifierService: NotifierService,) {
  }

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      password: ['', Validators.required],
      username: ['', Validators.required],
    })
  }

  get password(): any {
    return this.loginForm.get('password')
  };

  get username(): any {
    return this.loginForm.get('username')
  };

  login() {
    if(this.loginForm.valid) {
      this.rutubeService.login(this.loginForm.value).pipe(take(1)).subscribe({
        next: (r) => {
          this.loginError = false;
          sessionStorage.setItem('token', r.jwt);
          sessionStorage.setItem('role', r.role);
          void this.router.navigate(
              ['/all'],
          );
        },
        error: (err) => {
          this.loginError = true;
          console.log(err)
            this.notifierService.notify('error', 'Пользователь не найден, попробуйте снова!');
        },
        complete: () => {
        }})
    }
  }
}
