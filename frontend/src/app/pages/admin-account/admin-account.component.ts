import {AfterViewInit, Component, ElementRef, OnInit, signal} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Observable, take} from "rxjs";
import {
  AdminEmployeeStats,
  AdminRequestsStats,
  AIProcessedRequestPercentageChart,
  EmployeeDto,
  Role,
  UserProfileDto
} from "../../core/models/models";
import {RutubeService} from "../../core/services/rutube.service";
import {NotifierService} from "angular-notifier";
import {Chart} from "chart.js";
import * as moment from "moment";
export enum Menu {
  My,
  Employee,
  Messages,
  Add
}
interface ChartData {
  name: string,
  value: number,
}
interface Data {
  name: string,
  series: ChartData[],
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
  chartAll: AIProcessedRequestPercentageChart[];
  chartEvery: AIProcessedRequestPercentageChart[];
  chartJsAll: any;
  chartDataAll: ChartData[] = [];
  chartDataAllData: Data[] = [];
  chartDataEvery: ChartData[] = [];
  chartDataEveryData: Data[] = [];
  chartJsEvery: any;
  colorScheme = {
    domain: ['#5AA454', '#A10A28', '#C7B42C', '#AAAAAA']
  };
    customColors = (value: any) => {
        console.log(value);
        return "#ffffff";
    }

  constructor(   private elementRef: ElementRef,
  private fb: FormBuilder,protected rutubeService: RutubeService, private notifierService: NotifierService) {
  }
  addForm: FormGroup;
  ngOnInit(): void {
    this.addForm = this.fb.group({
      fullName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email,Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$')]],
      role: [Role.TECH_SUPPORT_EMPLOYEE]
    });
    this.getChartAll();
    this.getChartEvery();
  }

  get fullName(): any {
    return this.addForm.get('fullName')
  };

  get email(): any {
    return this.addForm.get('email')
  };

  protected readonly Role = Role;
  getChartAll() {
    this.rutubeService.getAdminChartAll().subscribe(res => {
      if(res) this.chartEvery = res;
      res.forEach(d => {
        this.chartDataAll.push({
          value: d.percentage,
          name: moment(d.dateTime).utc().format('DD.MM HH:mm')
        })
      });
      this.chartDataAllData = [JSON.parse(JSON.stringify({
        name: '',
        series: this.chartDataAll
      }))]
    })
  }
  getChartEvery() {
    this.rutubeService.getAdminChartEvery().subscribe(res => {
      if(res) this.chartEvery = res;
      res.forEach(d => {
        this.chartDataEvery.push({
          value: d.percentage,
          name: moment(d.dateTime).utc().format('DD.MM HH:mm')
        })
      });
      this.chartDataEveryData = [JSON.parse(JSON.stringify({
        name: '',
        series: this.chartDataEvery
      }))]
    })
    
  }
  

  addEmployee() {
    if(this.addForm.valid) {
      this.rutubeService.addEmployee(this.addForm.value).pipe(take(1)).subscribe({
        next: (r) => {
          this.notifierService.notify('success', 'Приглашение отправлено пользователю на почту!');
          this.addForm = this.fb.group({
            fullName: ['', Validators.required],
            email: ['', [Validators.required, Validators.email,Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$')]],
            role: [Role.TECH_SUPPORT_EMPLOYEE]
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