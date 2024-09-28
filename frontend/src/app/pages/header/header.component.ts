import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {AddFileModalComponent} from "../add-file-modal/add-file-modal.component";
import {MatDialog} from "@angular/material/dialog";
import {AllMetricsComponent} from "../all-metrics/all-metrics.component";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit{
  constructor(protected router:Router, private activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    console.log(this.router.url)
  }
  // @Output() goToUploadFile = new EventEmitter();
  // @Output() goToTable = new EventEmitter();
  // @Output() goToMain = new EventEmitter();
  //
  // addFile() {
  //   const d = this.dialog.open(AddFileModalComponent, {
  //     width      : '100%',
  //     disableClose: true,
  //   })
  //   d.afterClosed().subscribe(() => {
  //     this.goToUploadFile.emit()
  //   });
  // }
  //
  // showMetrics() {
  //   const d = this.dialog.open(AllMetricsComponent, {
  //     width      : '50%',
  //     minHeight      : '50%',
  //     disableClose: true,
  //   })
  // }
}
