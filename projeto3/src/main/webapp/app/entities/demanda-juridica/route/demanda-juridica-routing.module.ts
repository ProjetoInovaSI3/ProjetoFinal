import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DemandaJuridicaComponent } from '../list/demanda-juridica.component';
import { DemandaJuridicaDetailComponent } from '../detail/demanda-juridica-detail.component';
import { DemandaJuridicaUpdateComponent } from '../update/demanda-juridica-update.component';
import { DemandaJuridicaRoutingResolveService } from './demanda-juridica-routing-resolve.service';

const demandaJuridicaRoute: Routes = [
  {
    path: '',
    component: DemandaJuridicaComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DemandaJuridicaDetailComponent,
    resolve: {
      demandaJuridica: DemandaJuridicaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DemandaJuridicaUpdateComponent,
    resolve: {
      demandaJuridica: DemandaJuridicaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DemandaJuridicaUpdateComponent,
    resolve: {
      demandaJuridica: DemandaJuridicaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(demandaJuridicaRoute)],
  exports: [RouterModule],
})
export class DemandaJuridicaRoutingModule {}
