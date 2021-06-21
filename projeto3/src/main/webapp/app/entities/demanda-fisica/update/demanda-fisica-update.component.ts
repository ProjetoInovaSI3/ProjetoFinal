import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IDemandaFisica, DemandaFisica } from '../demanda-fisica.model';
import { DemandaFisicaService } from '../service/demanda-fisica.service';
import { IEndereco } from 'app/entities/endereco/endereco.model';
import { EnderecoService } from 'app/entities/endereco/service/endereco.service';

@Component({
  selector: 'jhi-demanda-fisica-update',
  templateUrl: './demanda-fisica-update.component.html',
})
export class DemandaFisicaUpdateComponent implements OnInit {
  isSaving = false;

  enderecosSharedCollection: IEndereco[] = [];

  editForm = this.fb.group({
    id: [],
    descricao: [],
    curso: [],
    enderecos: [],
  });

  constructor(
    protected demandaFisicaService: DemandaFisicaService,
    protected enderecoService: EnderecoService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ demandaFisica }) => {
      this.updateForm(demandaFisica);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const demandaFisica = this.createFromForm();
    if (demandaFisica.id !== undefined) {
      this.subscribeToSaveResponse(this.demandaFisicaService.update(demandaFisica));
    } else {
      this.subscribeToSaveResponse(this.demandaFisicaService.create(demandaFisica));
    }
  }

  trackEnderecoById(index: number, item: IEndereco): number {
    return item.id!;
  }

  getSelectedEndereco(option: IEndereco, selectedVals?: IEndereco[]): IEndereco {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDemandaFisica>>): void {
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

  protected updateForm(demandaFisica: IDemandaFisica): void {
    this.editForm.patchValue({
      id: demandaFisica.id,
      descricao: demandaFisica.descricao,
      curso: demandaFisica.curso,
      enderecos: demandaFisica.enderecos,
    });

    this.enderecosSharedCollection = this.enderecoService.addEnderecoToCollectionIfMissing(
      this.enderecosSharedCollection,
      ...(demandaFisica.enderecos ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.enderecoService
      .query()
      .pipe(map((res: HttpResponse<IEndereco[]>) => res.body ?? []))
      .pipe(
        map((enderecos: IEndereco[]) =>
          this.enderecoService.addEnderecoToCollectionIfMissing(enderecos, ...(this.editForm.get('enderecos')!.value ?? []))
        )
      )
      .subscribe((enderecos: IEndereco[]) => (this.enderecosSharedCollection = enderecos));
  }

  protected createFromForm(): IDemandaFisica {
    return {
      ...new DemandaFisica(),
      id: this.editForm.get(['id'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      curso: this.editForm.get(['curso'])!.value,
      enderecos: this.editForm.get(['enderecos'])!.value,
    };
  }
}
