import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDemandanteFisico, DemandanteFisico } from '../demandante-fisico.model';
import { DemandanteFisicoService } from '../service/demandante-fisico.service';

@Injectable({ providedIn: 'root' })
export class DemandanteFisicoRoutingResolveService implements Resolve<IDemandanteFisico> {
  constructor(protected service: DemandanteFisicoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDemandanteFisico> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((demandanteFisico: HttpResponse<DemandanteFisico>) => {
          if (demandanteFisico.body) {
            return of(demandanteFisico.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DemandanteFisico());
  }
}
