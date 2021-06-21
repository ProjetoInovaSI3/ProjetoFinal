import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IDemandaJuridica, DemandaJuridica } from '../demanda-juridica.model';
import { DemandaJuridicaService } from '../service/demanda-juridica.service';

@Component({
  selector: 'jhi-demanda-juridica-update',
  templateUrl: './demanda-juridica-update.component.html',
})
export class DemandaJuridicaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    descricao: [],
    curso: [],
  });

  constructor(
    protected demandaJuridicaService: DemandaJuridicaService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ demandaJuridica }) => {
      this.updateForm(demandaJuridica);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const demandaJuridica = this.createFromForm();
    if (demandaJuridica.id !== undefined) {
      this.subscribeToSaveResponse(this.demandaJuridicaService.update(demandaJuridica));
    } else {
      this.subscribeToSaveResponse(this.demandaJuridicaService.create(demandaJuridica));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDemandaJuridica>>): void {
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

  protected updateForm(demandaJuridica: IDemandaJuridica): void {
    this.editForm.patchValue({
      id: demandaJuridica.id,
      descricao: demandaJuridica.descricao,
      curso: demandaJuridica.curso,
    });
  }

  protected createFromForm(): IDemandaJuridica {
    return {
      ...new DemandaJuridica(),
      id: this.editForm.get(['id'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      curso: this.editForm.get(['curso'])!.value,
    };
  }
}
