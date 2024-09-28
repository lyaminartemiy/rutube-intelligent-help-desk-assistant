import {AfterViewInit, ChangeDetectorRef, Component, ContentChild, OnInit, signal, ViewChild} from '@angular/core';
import {
    AggregatedMetrics,
    AllAnalyst,
    MetricsCalculate,
    SingleMetricsArray,
    TextIntervals
} from "../../core/models/analyze";
import {AnalyzeService} from "../../core/services/analyze.service";
import {Observable, tap} from "rxjs";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatCheckboxChange} from "@angular/material/checkbox";
import {NotifierService} from "angular-notifier";
import {MatSelectChange} from "@angular/material/select";
import {FormControl} from "@angular/forms";
import {MetricModelComponent} from "../metric-model/metric-model.component";
import {MatDialog} from "@angular/material/dialog";
import {AddFileModalComponent} from "../add-file-modal/add-file-modal.component";
import {Router} from "@angular/router";
import {RutubeService} from "../../core/services/rutube.service";
import {UserProfileDto} from "../../core/models/models";

@Component({
    selector: 'app-main-page',
    templateUrl: './main-page.component.html',
    styleUrls: ['./main-page.component.scss']
})
export class MainPageComponent {
    opened = signal<boolean>(false);
    profile: Observable<UserProfileDto> = this.rutubeService.getProfile();
    constructor(protected rutubeService: RutubeService) {
    }
    

}
