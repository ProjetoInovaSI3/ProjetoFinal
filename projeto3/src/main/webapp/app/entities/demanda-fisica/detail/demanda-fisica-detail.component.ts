import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDemandaFisica } from '../demanda-fisica.model';

@Component({
  selector: 'jhi-demanda-fisica-detail',
  templateUrl: './demanda-fisica-detail.component.html',
})
export class DemandaFisicaDetailComponent implements OnInit {
  demandaFisica: IDemandaFisica | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ demandaFisica }) => {
      this.demandaFisica = demandaFisica;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
