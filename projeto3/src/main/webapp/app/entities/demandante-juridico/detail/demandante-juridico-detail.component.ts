import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDemandanteJuridico } from '../demandante-juridico.model';

@Component({
  selector: 'jhi-demandante-juridico-detail',
  templateUrl: './demandante-juridico-detail.component.html',
})
export class DemandanteJuridicoDetailComponent implements OnInit {
  demandanteJuridico: IDemandanteJuridico | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ demandanteJuridico }) => {
      this.demandanteJuridico = demandanteJuridico;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
