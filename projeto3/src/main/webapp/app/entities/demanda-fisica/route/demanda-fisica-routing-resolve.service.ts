import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDemandaFisica, DemandaFisica } from '../demanda-fisica.model';
import { DemandaFisicaService } from '../service/demanda-fisica.service';

@Injectable({ providedIn: 'root' })
export class DemandaFisicaRoutingResolveService implements Resolve<IDemandaFisica> {
  constructor(protected service: DemandaFisicaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDemandaFisica> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((demandaFisica: HttpResponse<DemandaFisica>) => {
          if (demandaFisica.body) {
            return of(demandaFisica.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DemandaFisica());
  }
}
