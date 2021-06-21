import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ICurso, Curso } from '../curso.model';
import { CursoService } from '../service/curso.service';
import { IDemandaFisica } from 'app/entities/demanda-fisica/demanda-fisica.model';
import { DemandaFisicaService } from 'app/entities/demanda-fisica/service/demanda-fisica.service';
import { IProfessor } from 'app/entities/professor/professor.model';
import { ProfessorService } from 'app/entities/professor/service/professor.service';
import { IDemandaJuridica } from 'app/entities/demanda-juridica/demanda-juridica.model';
import { DemandaJuridicaService } from 'app/entities/demanda-juridica/service/demanda-juridica.service';

@Component({
  selector: 'jhi-curso-update',
  templateUrl: './curso-update.component.html',
})
export class CursoUpdateComponent implements OnInit {
  isSaving = false;

  demandaFisicasSharedCollection: IDemandaFisica[] = [];
  professorsSharedCollection: IProfessor[] = [];
  demandaJuridicasSharedCollection: IDemandaJuridica[] = [];

  editForm = this.fb.group({
    id: [],
    nomeDoCurso: [],
    turma: [],
    demandaFisica: [],
    professor: [],
    demandaJuridica: [],
  });

  constructor(
    protected cursoService: CursoService,
    protected demandaFisicaService: DemandaFisicaService,
    protected professorService: ProfessorService,
    protected demandaJuridicaService: DemandaJuridicaService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ curso }) => {
      this.updateForm(curso);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const curso = this.createFromForm();
    if (curso.id !== undefined) {
      this.subscribeToSaveResponse(this.cursoService.update(curso));
    } else {
      this.subscribeToSaveResponse(this.cursoService.create(curso));
    }
  }

  trackDemandaFisicaById(index: number, item: IDemandaFisica): number {
    return item.id!;
  }

  trackProfessorById(index: number, item: IProfessor): number {
    return item.id!;
  }

  trackDemandaJuridicaById(index: number, item: IDemandaJuridica): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICurso>>): void {
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

  protected updateForm(curso: ICurso): void {
    this.editForm.patchValue({
      id: curso.id,
      nomeDoCurso: curso.nomeDoCurso,
      turma: curso.turma,
      demandaFisica: curso.demandaFisica,
      professor: curso.professor,
      demandaJuridica: curso.demandaJuridica,
    });

    this.demandaFisicasSharedCollection = this.demandaFisicaService.addDemandaFisicaToCollectionIfMissing(
      this.demandaFisicasSharedCollection,
      curso.demandaFisica
    );
    this.professorsSharedCollection = this.professorService.addProfessorToCollectionIfMissing(
      this.professorsSharedCollection,
      curso.professor
    );
    this.demandaJuridicasSharedCollection = this.demandaJuridicaService.addDemandaJuridicaToCollectionIfMissing(
      this.demandaJuridicasSharedCollection,
      curso.demandaJuridica
    );
  }

  protected loadRelationshipsOptions(): void {
    this.demandaFisicaService
      .query()
      .pipe(map((res: HttpResponse<IDemandaFisica[]>) => res.body ?? []))
      .pipe(
        map((demandaFisicas: IDemandaFisica[]) =>
          this.demandaFisicaService.addDemandaFisicaToCollectionIfMissing(demandaFisicas, this.editForm.get('demandaFisica')!.value)
        )
      )
      .subscribe((demandaFisicas: IDemandaFisica[]) => (this.demandaFisicasSharedCollection = demandaFisicas));

    this.professorService
      .query()
      .pipe(map((res: HttpResponse<IProfessor[]>) => res.body ?? []))
      .pipe(
        map((professors: IProfessor[]) =>
          this.professorService.addProfessorToCollectionIfMissing(professors, this.editForm.get('professor')!.value)
        )
      )
      .subscribe((professors: IProfessor[]) => (this.professorsSharedCollection = professors));

    this.demandaJuridicaService
      .query()
      .pipe(map((res: HttpResponse<IDemandaJuridica[]>) => res.body ?? []))
      .pipe(
        map((demandaJuridicas: IDemandaJuridica[]) =>
          this.demandaJuridicaService.addDemandaJuridicaToCollectionIfMissing(demandaJuridicas, this.editForm.get('demandaJuridica')!.value)
        )
      )
      .subscribe((demandaJuridicas: IDemandaJuridica[]) => (this.demandaJuridicasSharedCollection = demandaJuridicas));
  }

  protected createFromForm(): ICurso {
    return {
      ...new Curso(),
      id: this.editForm.get(['id'])!.value,
      nomeDoCurso: this.editForm.get(['nomeDoCurso'])!.value,
      turma: this.editForm.get(['turma'])!.value,
      demandaFisica: this.editForm.get(['demandaFisica'])!.value,
      professor: this.editForm.get(['professor'])!.value,
      demandaJuridica: this.editForm.get(['demandaJuridica'])!.value,
    };
  }
}
