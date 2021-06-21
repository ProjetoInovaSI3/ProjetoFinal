import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDemandanteJuridico } from '../demandante-juridico.model';
import { DemandanteJuridicoService } from '../service/demandante-juridico.service';

@Component({
  templateUrl: './demandante-juridico-delete-dialog.component.html',
})
export class DemandanteJuridicoDeleteDialogComponent {
  demandanteJuridico?: IDemandanteJuridico;

  constructor(protected demandanteJuridicoService: DemandanteJuridicoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.demandanteJuridicoService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
