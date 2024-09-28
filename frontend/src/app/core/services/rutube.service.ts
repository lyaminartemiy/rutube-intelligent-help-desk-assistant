import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {NotifierService} from "angular-notifier";
import {Router} from "@angular/router";
import {environment} from "../../../enviroments/enviroment";
import {
    AdminEmployeeStats,
    AdminRequestsStats,
    AIProcessedRequestPercentageChart,
    AuthDto, EmployeeDto, EmployeeStats, MessageDto, SendMessage,
    SignInDto, SignUpData, TechSupportRequestDto,
    UserProfileDto
} from "../models/models";
import {Observable} from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class RutubeService {

    constructor(private http: HttpClient, private notifierService: NotifierService, private router: Router,) {
    }

    logout() {
        sessionStorage.clear();
        void this.router.navigate(['/login']);
    }
    login(user: SignInDto): Observable<AuthDto> {
        return this.http.post<AuthDto>(`${environment.auth}/login`,
            user);
    }
    
    getProfile(): Observable<UserProfileDto> {
        return this.http.get<UserProfileDto>(`${environment.api}/employee-profiles`);
    }

    getEmployeeSummary(): Observable<EmployeeStats> {
        return this.http.get<EmployeeStats>(`${environment.api}/employee-stats/requests/summary`);
    }

    getAdminEmployees(): Observable<AdminEmployeeStats> {
        return this.http.get<AdminEmployeeStats>(`${environment.api}/admin-stats/employee/stats`);
    }

    getMyEmployees(): Observable<EmployeeDto[]> {
        return this.http.get<EmployeeDto[]>(`${environment.api}/employees/all`);
    }
    getAdminStats(): Observable<AdminRequestsStats> {
        return this.http.get<AdminRequestsStats>(`${environment.api}/admin-stats/requests/stats`);
    }

    getAdminChartEvery(): Observable<AIProcessedRequestPercentageChart[]> {
        return this.http.get<AIProcessedRequestPercentageChart[]>(`${environment.api}/admin-stats/chart/plot-percentage-of-requests-handled-by-ai`);
    }

    getAdminChartAll(): Observable<AIProcessedRequestPercentageChart[]> {
        return this.http.get<AIProcessedRequestPercentageChart[]>(`${environment.api}/admin-stats/chart/ai-processed-request-percentage`);
    }

    addEmployee(user: SignUpData): Observable<any> {
        return this.http.post<any>(`${environment.auth}/signup/send`,
            user);
    }
    
    getRequests(): Observable<TechSupportRequestDto[]> {
        return this.http.get<TechSupportRequestDto[]>(`${environment.api}/requests/all`);
    }

    getMyRequests(): Observable<TechSupportRequestDto[]> {
        return this.http.get<TechSupportRequestDto[]>(`${environment.api}/requests/assigned/open`);
    }
    getHistory(): Observable<TechSupportRequestDto[]> {
        return this.http.get<TechSupportRequestDto[]>(`${environment.api}/requests/assigned/all`);
    }
    
    assign(reqId: string) {
        return this.http.post<any[]>(`${environment.api}/requests/${reqId}/assign`, null);
    }
    close(reqId: string) {
        return this.http.post<any[]>(`${environment.api}/requests/${reqId}/close`, null);
    }
    dialogue(reqId: null | number) {
        return this.http.get<MessageDto[]>(`${environment.api}/requests/${reqId}/dialogue`);
    }
    send(reqId: string, message: SendMessage) {
        return this.http.post<MessageDto[]>(`${environment.api}/requests/${reqId}/send`, message);
    }
    
    
    
}