import {Component, OnInit, signal} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Observable, take} from "rxjs";
import {AdminEmployeeStats, AdminRequestsStats, EmployeeDto, Role, UserProfileDto} from "../../core/models/models";
import {RutubeService} from "../../core/services/rutube.service";
import {NotifierService} from "angular-notifier";
export enum Menu {
  My,
  Employee,
  Messages,
  Add
}
@Component({
  selector: 'app-admin-account',
  templateUrl: './admin-account.component.html',
  styleUrls: ['./admin-account.component.scss']
})
export class AdminAccountComponent implements OnInit{
  opened = signal<Menu>(Menu.My);
  protected readonly Menu = Menu;
  employeeName: FormControl = new FormControl<string>('');
  profile: Observable<UserProfileDto> = this.rutubeService.getProfile();
  adminEmployees: Observable<AdminEmployeeStats> = this.rutubeService.getAdminEmployees();
  adminStats: Observable<AdminRequestsStats> = this.rutubeService.getAdminStats();
  myEmployee: Observable<EmployeeDto[]> = this.rutubeService.getMyEmployees();

  constructor( private fb: FormBuilder,protected rutubeService: RutubeService, private notifierService: NotifierService) {
  }
  addForm: FormGroup;
  ngOnInit(): void {
    this.addForm = this.fb.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email,Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$')]],
      role: [Role.ADMIN]
    })
  }

  get name(): any {
    return this.addForm.get('name')
  };

  get email(): any {
    return this.addForm.get('email')
  };

  protected readonly Role = Role;

  addEmployee() {
    if(this.addForm.valid) {
      this.rutubeService.addEmployee(this.addForm.value).pipe(take(1)).subscribe({
        next: (r) => {
          this.notifierService.notify('success', 'Приглашение отправлено пользователю на почту!');
          this.addForm = this.fb.group({
            name: ['', Validators.required],
            email: ['', [Validators.required, Validators.email,Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$')]],
            role: [Role.ADMIN]
          })
          
        },
        error: (err) => {
          this.notifierService.notify('error', 'Произошла ошибка, попробуйте снова!');
        },
        complete: () => {
        }})
    }
  }
}