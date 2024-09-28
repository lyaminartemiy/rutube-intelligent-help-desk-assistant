import {Component, Inject, OnInit, signal} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {AnalyzeService} from "../../core/services/analyze.service";
import {AggregatedMetrics, AllAnalyst, SingleMetrics, SingleMetricsArray} from "../../core/models/analyze";
import {FormControl} from "@angular/forms";
import {MatCheckboxChange} from "@angular/material/checkbox";
import {MatSelectChange} from "@angular/material/select";
import {animate, state, style, transition, trigger} from "@angular/animations";
import {Location} from "@angular/common";

export interface It {
    ids: string
}

@Component({
    selector: 'app-metric-model',
    templateUrl: './metric-model.component.html',
    styleUrls: ['./metric-model.component.scss'],
    animations: [
        trigger('detailExpand', [
            state('collapsed', style({height: '0px', minHeight: '0'})),
            state('expanded', style({height: '*', minHeight: "*"})),
            transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
        ]),
    ],
})
export class MetricModelComponent implements OnInit {
    constructor(private analyzeService: AnalyzeService, public dialog: MatDialog, private location: Location) {
    }

    expandedElement: AggregatedMetrics | null;
    lastEndIndex: number = 0;
    allMetrics: AggregatedMetrics[] = [];
    singleMetrics: SingleMetricsArray[] = [];
    loading = signal(false);
    loading2 = signal(false);
    loading3 = signal(false);
    displayedColumns: string[] = ['Файл', 'Дата загрузки', 'Есть нарушение', 'Тескт'];
    expandedDetail: string[] = ['expandedDetail',];
    filteredNegotiations: SingleMetricsArray[] = [];
    dataSource: SingleMetricsArray[];
    splitted: {
        text: string,
        highlited: boolean,
        violatedRegulation?: string,
    }[][][];
    size = 5;
    pageIndex = 0;
    filterInput: FormControl = new FormControl('');
    ids: string = ''

    ngOnInit(): void {
    }
    
}
