import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {MainPageComponent} from "./pages/main-page/main-page.component";
import {MetricModelComponent} from "./pages/metric-model/metric-model.component";
import {MainComponent} from "./pages/main/main.component";
import {AdminAccountComponent} from "./pages/admin-account/admin-account.component";
import {AllMetricsComponent} from "./pages/all-metrics/all-metrics.component";
import {LoginComponent} from "./pages/login/login.component";
import {AppComponent} from "./app.component";
import {AuthGuard} from "./core/guards/auth.guard";
import {MyAppealsComponent} from "./pages/my-appeals/my-appeals.component";
import {HistoryComponent} from "./pages/history/history.component";
import {AllAppealsComponent} from "./pages/all-appeals/all-appeals.component";
import {MessagesComponent} from "./pages/messages/messages.component";
import {MessagesResolver} from "./pages/messages/messages.resolver";

const routes: Routes = [
  {
    path:'login',
    component: LoginComponent,
    // canActivate: [AuthGuard]
  },
  {
    path:'',
    component: MainPageComponent,
    canActivate: [AuthGuard],
    // canActivate: [AuthGuard],
    children: [
      {
        path: 'account',
        component: MainComponent,
        canActivate: [AuthGuard],
      },
      {
        path: 'admin',
        component: AdminAccountComponent,
        canActivate: [AuthGuard],
      },
      {
        path: 'all',
        component: AllAppealsComponent,
        canActivate: [AuthGuard],
      },
      {
        path: 'my',
        component: MyAppealsComponent,
        canActivate: [AuthGuard],
      },
      {
        path: 'history',
        component: HistoryComponent,
        canActivate: [AuthGuard],
      },
      {
        path: 'messages/:id/0',
        component: MessagesComponent,
        data: { assigned: 0},
        resolve: {messages: MessagesResolver},
        canActivate: [AuthGuard],
      },
      {
        path: 'messages',
        component: MessagesComponent,
        // data: { assigned: 0},
        // resolve: {messages: MessagesResolver},
        canActivate: [AuthGuard],
      },
      {
        path: 'messages/:id/1',
        component: MessagesComponent,
        data: { assigned: 1},
        resolve: {messages: MessagesResolver},
        canActivate: [AuthGuard],
      },
      {
        path: 'messages/:id/3',
        component: MessagesComponent,
        data: { assigned: 3},
        resolve: {messages: MessagesResolver},
        canActivate: [AuthGuard],
      }
        
    ]
  },
  // {
  //   path:'',
  //   component: MainPageComponent,
  //   children: [
  //      
  //   
  //     // {
  //     //   path: 'account',
  //     //   component: AccountComponent,
  //     // },
  //     // {
  //     //   path: 'portfolio',
  //     //   component: PortfolioComponent,
  //     // },
  //     // {
  //     //   path: 'portfolio-futures/:id',
  //     //   component: PortfolioFuturesComponent,
  //     // },
  //     // {
  //     //   path: 'position/new/:id',
  //     //   component: NewPositionComponent,
  //     // },
  //     // {
  //     //   path: '',
  //     //   component: MainPageComponent,
  //     // },
  //   ]
  // },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
