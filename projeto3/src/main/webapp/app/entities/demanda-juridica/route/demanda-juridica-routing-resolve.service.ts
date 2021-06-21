import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDemandaJuridica, DemandaJuridica } from '../demanda-juridica.model';
import { DemandaJuridicaService } from '../service/demanda-juridica.service';

@Injectable({ providedIn: 'root' })
export class DemandaJuridicaRoutingResolveService implements Resolve<IDemandaJuridica> {
  constructor(protected service: DemandaJuridicaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDemandaJuridica> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((demandaJuridica: HttpResponse<DemandaJuridica>) => {
          if (demandaJuridica.body) {
            return of(demandaJuridica.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DemandaJuridica());
  }
}
