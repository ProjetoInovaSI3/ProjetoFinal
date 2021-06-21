import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDemandanteFisico } from '../demandante-fisico.model';
import { DemandanteFisicoService } from '../service/demandante-fisico.service';

@Component({
  templateUrl: './demandante-fisico-delete-dialog.component.html',
})
export class DemandanteFisicoDeleteDialogComponent {
  demandanteFisico?: IDemandanteFisico;

  constructor(protected demandanteFisicoService: DemandanteFisicoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.demandanteFisicoService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
