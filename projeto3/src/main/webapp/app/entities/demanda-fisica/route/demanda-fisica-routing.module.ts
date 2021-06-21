import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DemandaFisicaComponent } from '../list/demanda-fisica.component';
import { DemandaFisicaDetailComponent } from '../detail/demanda-fisica-detail.component';
import { DemandaFisicaUpdateComponent } from '../update/demanda-fisica-update.component';
import { DemandaFisicaRoutingResolveService } from './demanda-fisica-routing-resolve.service';

const demandaFisicaRoute: Routes = [
  {
    path: '',
    component: DemandaFisicaComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DemandaFisicaDetailComponent,
    resolve: {
      demandaFisica: DemandaFisicaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DemandaFisicaUpdateComponent,
    resolve: {
      demandaFisica: DemandaFisicaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DemandaFisicaUpdateComponent,
    resolve: {
      demandaFisica: DemandaFisicaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(demandaFisicaRoute)],
  exports: [RouterModule],
})
export class DemandaFisicaRoutingModule {}
