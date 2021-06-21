import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDemandanteFisico } from '../demandante-fisico.model';

@Component({
  selector: 'jhi-demandante-fisico-detail',
  templateUrl: './demandante-fisico-detail.component.html',
})
export class DemandanteFisicoDetailComponent implements OnInit {
  demandanteFisico: IDemandanteFisico | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ demandanteFisico }) => {
      this.demandanteFisico = demandanteFisico;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
