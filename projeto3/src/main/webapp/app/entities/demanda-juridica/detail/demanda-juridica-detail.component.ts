import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDemandaJuridica } from '../demanda-juridica.model';

@Component({
  selector: 'jhi-demanda-juridica-detail',
  templateUrl: './demanda-juridica-detail.component.html',
})
export class DemandaJuridicaDetailComponent implements OnInit {
  demandaJuridica: IDemandaJuridica | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ demandaJuridica }) => {
      this.demandaJuridica = demandaJuridica;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
