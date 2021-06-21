import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IDemandanteFisico, DemandanteFisico } from '../demandante-fisico.model';
import { DemandanteFisicoService } from '../service/demandante-fisico.service';
import { IDemandaFisica } from 'app/entities/demanda-fisica/demanda-fisica.model';
import { DemandaFisicaService } from 'app/entities/demanda-fisica/service/demanda-fisica.service';

@Component({
  selector: 'jhi-demandante-fisico-update',
  templateUrl: './demandante-fisico-update.component.html',
})
export class DemandanteFisicoUpdateComponent implements OnInit {
  isSaving = false;

  demandasCollection: IDemandaFisica[] = [];

  editForm = this.fb.group({
    id: [],
    cpf: [],
    nomeCompleto: [],
    email: [],
    telefone: [],
    demanda: [],
  });

  constructor(
    protected demandanteFisicoService: DemandanteFisicoService,
    protected demandaFisicaService: DemandaFisicaService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ demandanteFisico }) => {
      this.updateForm(demandanteFisico);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const demandanteFisico = this.createFromForm();
    if (demandanteFisico.id !== undefined) {
      this.subscribeToSaveResponse(this.demandanteFisicoService.update(demandanteFisico));
    } else {
      this.subscribeToSaveResponse(this.demandanteFisicoService.create(demandanteFisico));
    }
  }

  trackDemandaFisicaById(index: number, item: IDemandaFisica): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDemandanteFisico>>): void {
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

  protected updateForm(demandanteFisico: IDemandanteFisico): void {
    this.editForm.patchValue({
      id: demandanteFisico.id,
      cpf: demandanteFisico.cpf,
      nomeCompleto: demandanteFisico.nomeCompleto,
      email: demandanteFisico.email,
      telefone: demandanteFisico.telefone,
      demanda: demandanteFisico.demanda,
    });

    this.demandasCollection = this.demandaFisicaService.addDemandaFisicaToCollectionIfMissing(
      this.demandasCollection,
      demandanteFisico.demanda
    );
  }

  protected loadRelationshipsOptions(): void {
    this.demandaFisicaService
      .query({ 'demandanteFisicoId.specified': 'false' })
      .pipe(map((res: HttpResponse<IDemandaFisica[]>) => res.body ?? []))
      .pipe(
        map((demandaFisicas: IDemandaFisica[]) =>
          this.demandaFisicaService.addDemandaFisicaToCollectionIfMissing(demandaFisicas, this.editForm.get('demanda')!.value)
        )
      )
      .subscribe((demandaFisicas: IDemandaFisica[]) => (this.demandasCollection = demandaFisicas));
  }

  protected createFromForm(): IDemandanteFisico {
    return {
      ...new DemandanteFisico(),
      id: this.editForm.get(['id'])!.value,
      cpf: this.editForm.get(['cpf'])!.value,
      nomeCompleto: this.editForm.get(['nomeCompleto'])!.value,
      email: this.editForm.get(['email'])!.value,
      telefone: this.editForm.get(['telefone'])!.value,
      demanda: this.editForm.get(['demanda'])!.value,
    };
  }
}
