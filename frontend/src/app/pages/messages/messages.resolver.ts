import {ActivatedRouteSnapshot, Router, RouterStateSnapshot} from "@angular/router";
import {RutubeService} from "../../core/services/rutube.service";
import {catchError, Observable} from "rxjs";
import {inject} from "@angular/core";
import {MessageDto} from "../../core/models/models";


export const MessagesResolver: (route: ActivatedRouteSnapshot, state: RouterStateSnapshot, rutubeService?: RutubeService) => Observable<MessageDto[]> = (
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot,
    rutubeService: RutubeService = inject(RutubeService),
): Observable<MessageDto[]> =>
    rutubeService.dialogue(+route.paramMap.get('id')!)