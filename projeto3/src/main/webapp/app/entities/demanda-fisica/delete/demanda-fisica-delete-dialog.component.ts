import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDemandaFisica } from '../demanda-fisica.model';
import { DemandaFisicaService } from '../service/demanda-fisica.service';

@Component({
  templateUrl: './demanda-fisica-delete-dialog.component.html',
})
export class DemandaFisicaDeleteDialogComponent {
  demandaFisica?: IDemandaFisica;

  constructor(protected demandaFisicaService: DemandaFisicaService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.demandaFisicaService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
