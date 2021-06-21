import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DemandaFisicaComponent } from './list/demanda-fisica.component';
import { DemandaFisicaDetailComponent } from './detail/demanda-fisica-detail.component';
import { DemandaFisicaUpdateComponent } from './update/demanda-fisica-update.component';
import { DemandaFisicaDeleteDialogComponent } from './delete/demanda-fisica-delete-dialog.component';
import { DemandaFisicaRoutingModule } from './route/demanda-fisica-routing.module';

@NgModule({
  imports: [SharedModule, DemandaFisicaRoutingModule],
  declarations: [DemandaFisicaComponent, DemandaFisicaDetailComponent, DemandaFisicaUpdateComponent, DemandaFisicaDeleteDialogComponent],
  entryComponents: [DemandaFisicaDeleteDialogComponent],
})
export class DemandaFisicaModule {}
