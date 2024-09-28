import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {RutubeService} from "../../core/services/rutube.service";
import {Observable} from "rxjs";
import {AdminEmployeeStats, AdminRequestsStats, EmployeeStats, Role, UserProfileDto} from "../../core/models/models";

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent {
  constructor(private router:Router, protected rutubeService: RutubeService) {
  }
  profile: Observable<UserProfileDto> = this.rutubeService.getProfile();
  employeeStats: Observable<EmployeeStats> = this.rutubeService.getEmployeeSummary();
  // ngOnInit(): void {
  //   this.router.navigate(['main'])
  // }

  protected readonly Role = Role;
}
