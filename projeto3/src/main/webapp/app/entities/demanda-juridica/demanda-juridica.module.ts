import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DemandaJuridicaComponent } from './list/demanda-juridica.component';
import { DemandaJuridicaDetailComponent } from './detail/demanda-juridica-detail.component';
import { DemandaJuridicaUpdateComponent } from './update/demanda-juridica-update.component';
import { DemandaJuridicaDeleteDialogComponent } from './delete/demanda-juridica-delete-dialog.component';
import { DemandaJuridicaRoutingModule } from './route/demanda-juridica-routing.module';

@NgModule({
  imports: [SharedModule, DemandaJuridicaRoutingModule],
  declarations: [
    DemandaJuridicaComponent,
    DemandaJuridicaDetailComponent,
    DemandaJuridicaUpdateComponent,
    DemandaJuridicaDeleteDialogComponent,
  ],
  entryComponents: [DemandaJuridicaDeleteDialogComponent],
})
export class DemandaJuridicaModule {}
