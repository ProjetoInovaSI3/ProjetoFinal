import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DemandanteFisicoComponent } from '../list/demandante-fisico.component';
import { DemandanteFisicoDetailComponent } from '../detail/demandante-fisico-detail.component';
import { DemandanteFisicoUpdateComponent } from '../update/demandante-fisico-update.component';
import { DemandanteFisicoRoutingResolveService } from './demandante-fisico-routing-resolve.service';

const demandanteFisicoRoute: Routes = [
  {
    path: '',
    component: DemandanteFisicoComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DemandanteFisicoDetailComponent,
    resolve: {
      demandanteFisico: DemandanteFisicoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DemandanteFisicoUpdateComponent,
    resolve: {
      demandanteFisico: DemandanteFisicoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DemandanteFisicoUpdateComponent,
    resolve: {
      demandanteFisico: DemandanteFisicoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(demandanteFisicoRoute)],
  exports: [RouterModule],
})
export class DemandanteFisicoRoutingModule {}
