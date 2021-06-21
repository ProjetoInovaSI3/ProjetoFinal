import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDemandanteJuridico, DemandanteJuridico } from '../demandante-juridico.model';
import { DemandanteJuridicoService } from '../service/demandante-juridico.service';

@Injectable({ providedIn: 'root' })
export class DemandanteJuridicoRoutingResolveService implements Resolve<IDemandanteJuridico> {
  constructor(protected service: DemandanteJuridicoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDemandanteJuridico> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((demandanteJuridico: HttpResponse<DemandanteJuridico>) => {
          if (demandanteJuridico.body) {
            return of(demandanteJuridico.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DemandanteJuridico());
  }
}
