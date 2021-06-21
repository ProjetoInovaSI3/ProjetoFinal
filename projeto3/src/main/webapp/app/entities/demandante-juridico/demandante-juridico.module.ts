import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DemandanteJuridicoComponent } from './list/demandante-juridico.component';
import { DemandanteJuridicoDetailComponent } from './detail/demandante-juridico-detail.component';
import { DemandanteJuridicoUpdateComponent } from './update/demandante-juridico-update.component';
import { DemandanteJuridicoDeleteDialogComponent } from './delete/demandante-juridico-delete-dialog.component';
import { DemandanteJuridicoRoutingModule } from './route/demandante-juridico-routing.module';

@NgModule({
  imports: [SharedModule, DemandanteJuridicoRoutingModule],
  declarations: [
    DemandanteJuridicoComponent,
    DemandanteJuridicoDetailComponent,
    DemandanteJuridicoUpdateComponent,
    DemandanteJuridicoDeleteDialogComponent,
  ],
  entryComponents: [DemandanteJuridicoDeleteDialogComponent],
})
export class DemandanteJuridicoModule {}
