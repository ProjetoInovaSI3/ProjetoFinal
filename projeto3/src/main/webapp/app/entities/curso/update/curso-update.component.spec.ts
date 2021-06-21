jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { CursoService } from '../service/curso.service';
import { ICurso, Curso } from '../curso.model';
import { IDemandaFisica } from 'app/entities/demanda-fisica/demanda-fisica.model';
import { DemandaFisicaService } from 'app/entities/demanda-fisica/service/demanda-fisica.service';
import { IProfessor } from 'app/entities/professor/professor.model';
import { ProfessorService } from 'app/entities/professor/service/professor.service';
import { IDemandaJuridica } from 'app/entities/demanda-juridica/demanda-juridica.model';
import { DemandaJuridicaService } from 'app/entities/demanda-juridica/service/demanda-juridica.service';

import { CursoUpdateComponent } from './curso-update.component';

describe('Component Tests', () => {
  describe('Curso Management Update Component', () => {
    let comp: CursoUpdateComponent;
    let fixture: ComponentFixture<CursoUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let cursoService: CursoService;
    let demandaFisicaService: DemandaFisicaService;
    let professorService: ProfessorService;
    let demandaJuridicaService: DemandaJuridicaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CursoUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(CursoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CursoUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      cursoService = TestBed.inject(CursoService);
      demandaFisicaService = TestBed.inject(DemandaFisicaService);
      professorService = TestBed.inject(ProfessorService);
      demandaJuridicaService = TestBed.inject(DemandaJuridicaService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call DemandaFisica query and add missing value', () => {
        const curso: ICurso = { id: 456 };
        const demandaFisica: IDemandaFisica = { id: 35520 };
        curso.demandaFisica = demandaFisica;

        const demandaFisicaCollection: IDemandaFisica[] = [{ id: 80189 }];
        jest.spyOn(demandaFisicaService, 'query').mockReturnValue(of(new HttpResponse({ body: demandaFisicaCollection })));
        const additionalDemandaFisicas = [demandaFisica];
        const expectedCollection: IDemandaFisica[] = [...additionalDemandaFisicas, ...demandaFisicaCollection];
        jest.spyOn(demandaFisicaService, 'addDemandaFisicaToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ curso });
        comp.ngOnInit();

        expect(demandaFisicaService.query).toHaveBeenCalled();
        expect(demandaFisicaService.addDemandaFisicaToCollectionIfMissing).toHaveBeenCalledWith(
          demandaFisicaCollection,
          ...additionalDemandaFisicas
        );
        expect(comp.demandaFisicasSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Professor query and add missing value', () => {
        const curso: ICurso = { id: 456 };
        const professor: IProfessor = { id: 85189 };
        curso.professor = professor;

        const professorCollection: IProfessor[] = [{ id: 42138 }];
        jest.spyOn(professorService, 'query').mockReturnValue(of(new HttpResponse({ body: professorCollection })));
        const additionalProfessors = [professor];
        const expectedCollection: IProfessor[] = [...additionalProfessors, ...professorCollection];
        jest.spyOn(professorService, 'addProfessorToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ curso });
        comp.ngOnInit();

        expect(professorService.query).toHaveBeenCalled();
        expect(professorService.addProfessorToCollectionIfMissing).toHaveBeenCalledWith(professorCollection, ...additionalProfessors);
        expect(comp.professorsSharedCollection).toEqual(expectedCollection);
      });

      it('Should call DemandaJuridica query and add missing value', () => {
        const curso: ICurso = { id: 456 };
        const demandaJuridica: IDemandaJuridica = { id: 56619 };
        curso.demandaJuridica = demandaJuridica;

        const demandaJuridicaCollection: IDemandaJuridica[] = [{ id: 24938 }];
        jest.spyOn(demandaJuridicaService, 'query').mockReturnValue(of(new HttpResponse({ body: demandaJuridicaCollection })));
        const additionalDemandaJuridicas = [demandaJuridica];
        const expectedCollection: IDemandaJuridica[] = [...additionalDemandaJuridicas, ...demandaJuridicaCollection];
        jest.spyOn(demandaJuridicaService, 'addDemandaJuridicaToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ curso });
        comp.ngOnInit();

        expect(demandaJuridicaService.query).toHaveBeenCalled();
        expect(demandaJuridicaService.addDemandaJuridicaToCollectionIfMissing).toHaveBeenCalledWith(
          demandaJuridicaCollection,
          ...additionalDemandaJuridicas
        );
        expect(comp.demandaJuridicasSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const curso: ICurso = { id: 456 };
        const demandaFisica: IDemandaFisica = { id: 10440 };
        curso.demandaFisica = demandaFisica;
        const professor: IProfessor = { id: 26749 };
        curso.professor = professor;
        const demandaJuridica: IDemandaJuridica = { id: 71373 };
        curso.demandaJuridica = demandaJuridica;

        activatedRoute.data = of({ curso });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(curso));
        expect(comp.demandaFisicasSharedCollection).toContain(demandaFisica);
        expect(comp.professorsSharedCollection).toContain(professor);
        expect(comp.demandaJuridicasSharedCollection).toContain(demandaJuridica);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Curso>>();
        const curso = { id: 123 };
        jest.spyOn(cursoService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ curso });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: curso }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(cursoService.update).toHaveBeenCalledWith(curso);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Curso>>();
        const curso = new Curso();
        jest.spyOn(cursoService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ curso });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: curso }));
        saveSubject.complete();

        // THEN
        expect(cursoService.create).toHaveBeenCalledWith(curso);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Curso>>();
        const curso = { id: 123 };
        jest.spyOn(cursoService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ curso });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(cursoService.update).toHaveBeenCalledWith(curso);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackDemandaFisicaById', () => {
        it('Should return tracked DemandaFisica primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackDemandaFisicaById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackProfessorById', () => {
        it('Should return tracked Professor primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackProfessorById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackDemandaJuridicaById', () => {
        it('Should return tracked DemandaJuridica primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackDemandaJuridicaById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
