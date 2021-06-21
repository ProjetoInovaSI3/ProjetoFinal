import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IDemandanteJuridico, DemandanteJuridico } from '../demandante-juridico.model';
import { DemandanteJuridicoService } from '../service/demandante-juridico.service';
import { IDemandaJuridica } from 'app/entities/demanda-juridica/demanda-juridica.model';
import { DemandaJuridicaService } from 'app/entities/demanda-juridica/service/demanda-juridica.service';

@Component({
  selector: 'jhi-demandante-juridico-update',
  templateUrl: './demandante-juridico-update.component.html',
})
export class DemandanteJuridicoUpdateComponent implements OnInit {
  isSaving = false;

  demandasCollection: IDemandaJuridica[] = [];

  editForm = this.fb.group({
    id: [],
    cnpj: [],
    nomeDaEmpresa: [],
    nomefantasia: [],
    email: [],
    telefone: [],
    demanda: [],
  });

  constructor(
    protected demandanteJuridicoService: DemandanteJuridicoService,
    protected demandaJuridicaService: DemandaJuridicaService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ demandanteJuridico }) => {
      this.updateForm(demandanteJuridico);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const demandanteJuridico = this.createFromForm();
    if (demandanteJuridico.id !== undefined) {
      this.subscribeToSaveResponse(this.demandanteJuridicoService.update(demandanteJuridico));
    } else {
      this.subscribeToSaveResponse(this.demandanteJuridicoService.create(demandanteJuridico));
    }
  }

  trackDemandaJuridicaById(index: number, item: IDemandaJuridica): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDemandanteJuridico>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(demandanteJuridico: IDemandanteJuridico): void {
    this.editForm.patchValue({
      id: demandanteJuridico.id,
      cnpj: demandanteJuridico.cnpj,
      nomeDaEmpresa: demandanteJuridico.nomeDaEmpresa,
      nomefantasia: demandanteJuridico.nomefantasia,
      email: demandanteJuridico.email,
      telefone: demandanteJuridico.telefone,
      demanda: demandanteJuridico.demanda,
    });

    this.demandasCollection = this.demandaJuridicaService.addDemandaJuridicaToCollectionIfMissing(
      this.demandasCollection,
      demandanteJuridico.demanda
    );
  }

  protected loadRelationshipsOptions(): void {
    this.demandaJuridicaService
      .query({ 'demandanteJuridicoId.specified': 'false' })
      .pipe(map((res: HttpResponse<IDemandaJuridica[]>) => res.body ?? []))
      .pipe(
        map((demandaJuridicas: IDemandaJuridica[]) =>
          this.demandaJuridicaService.addDemandaJuridicaToCollectionIfMissing(demandaJuridicas, this.editForm.get('demanda')!.value)
        )
      )
      .subscribe((demandaJuridicas: IDemandaJuridica[]) => (this.demandasCollection = demandaJuridicas));
  }

  protected createFromForm(): IDemandanteJuridico {
    return {
      ...new DemandanteJuridico(),
      id: this.editForm.get(['id'])!.value,
      cnpj: this.editForm.get(['cnpj'])!.value,
      nomeDaEmpresa: this.editForm.get(['nomeDaEmpresa'])!.value,
      nomefantasia: this.editForm.get(['nomefantasia'])!.value,
      email: this.editForm.get(['email'])!.value,
      telefone: this.editForm.get(['telefone'])!.value,
      demanda: this.editForm.get(['demanda'])!.value,
    };
  }
}
