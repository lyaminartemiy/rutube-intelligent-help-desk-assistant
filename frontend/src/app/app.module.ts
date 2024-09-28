import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {PagesModule} from "./pages/pages.module";
import {NotifierModule} from "angular-notifier";
import { NgChartsModule } from 'ng2-charts';
import {HttpTokenInterceptor} from "./core/interceptors/http.token.interceptor";
import {AuthGuard} from "./core/guards/auth.guard";

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    PagesModule,
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    NotifierModule,
    NgChartsModule
  ],
  providers:[
      {provide: HTTP_INTERCEPTORS, useClass: HttpTokenInterceptor, multi: true},
    AuthGuard,
      ],
  bootstrap: [AppComponent]
})
export class AppModule { }
