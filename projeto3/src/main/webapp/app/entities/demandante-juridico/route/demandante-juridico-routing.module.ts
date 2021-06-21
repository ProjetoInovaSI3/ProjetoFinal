import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DemandanteJuridicoComponent } from '../list/demandante-juridico.component';
import { DemandanteJuridicoDetailComponent } from '../detail/demandante-juridico-detail.component';
import { DemandanteJuridicoUpdateComponent } from '../update/demandante-juridico-update.component';
import { DemandanteJuridicoRoutingResolveService } from './demandante-juridico-routing-resolve.service';

const demandanteJuridicoRoute: Routes = [
  {
    path: '',
    component: DemandanteJuridicoComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DemandanteJuridicoDetailComponent,
    resolve: {
      demandanteJuridico: DemandanteJuridicoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DemandanteJuridicoUpdateComponent,
    resolve: {
      demandanteJuridico: DemandanteJuridicoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DemandanteJuridicoUpdateComponent,
    resolve: {
      demandanteJuridico: DemandanteJuridicoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(demandanteJuridicoRoute)],
  exports: [RouterModule],
})
export class DemandanteJuridicoRoutingModule {}
