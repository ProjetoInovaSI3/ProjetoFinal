import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDemandaJuridica } from '../demanda-juridica.model';
import { DemandaJuridicaService } from '../service/demanda-juridica.service';

@Component({
  templateUrl: './demanda-juridica-delete-dialog.component.html',
})
export class DemandaJuridicaDeleteDialogComponent {
  demandaJuridica?: IDemandaJuridica;

  constructor(protected demandaJuridicaService: DemandaJuridicaService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.demandaJuridicaService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
