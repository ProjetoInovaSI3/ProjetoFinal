import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DemandanteFisicoComponent } from './list/demandante-fisico.component';
import { DemandanteFisicoDetailComponent } from './detail/demandante-fisico-detail.component';
import { DemandanteFisicoUpdateComponent } from './update/demandante-fisico-update.component';
import { DemandanteFisicoDeleteDialogComponent } from './delete/demandante-fisico-delete-dialog.component';
import { DemandanteFisicoRoutingModule } from './route/demandante-fisico-routing.module';

@NgModule({
  imports: [SharedModule, DemandanteFisicoRoutingModule],
  declarations: [
    DemandanteFisicoComponent,
    DemandanteFisicoDetailComponent,
    DemandanteFisicoUpdateComponent,
    DemandanteFisicoDeleteDialogComponent,
  ],
  entryComponents: [DemandanteFisicoDeleteDialogComponent],
})
export class DemandanteFisicoModule {}
