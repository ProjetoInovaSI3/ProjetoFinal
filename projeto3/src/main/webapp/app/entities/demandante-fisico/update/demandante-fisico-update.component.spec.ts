jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { DemandanteFisicoService } from '../service/demandante-fisico.service';
import { IDemandanteFisico, DemandanteFisico } from '../demandante-fisico.model';
import { IDemandaFisica } from 'app/entities/demanda-fisica/demanda-fisica.model';
import { DemandaFisicaService } from 'app/entities/demanda-fisica/service/demanda-fisica.service';

import { DemandanteFisicoUpdateComponent } from './demandante-fisico-update.component';

describe('Component Tests', () => {
  describe('DemandanteFisico Management Update Component', () => {
    let comp: DemandanteFisicoUpdateComponent;
    let fixture: ComponentFixture<DemandanteFisicoUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let demandanteFisicoService: DemandanteFisicoService;
    let demandaFisicaService: DemandaFisicaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [DemandanteFisicoUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(DemandanteFisicoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DemandanteFisicoUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      demandanteFisicoService = TestBed.inject(DemandanteFisicoService);
      demandaFisicaService = TestBed.inject(DemandaFisicaService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call demanda query and add missing value', () => {
        const demandanteFisico: IDemandanteFisico = { id: 456 };
        const demanda: IDemandaFisica = { id: 89726 };
        demandanteFisico.demanda = demanda;

        const demandaCollection: IDemandaFisica[] = [{ id: 41644 }];
        jest.spyOn(demandaFisicaService, 'query').mockReturnValue(of(new HttpResponse({ body: demandaCollection })));
        const expectedCollection: IDemandaFisica[] = [demanda, ...demandaCollection];
        jest.spyOn(demandaFisicaService, 'addDemandaFisicaToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ demandanteFisico });
        comp.ngOnInit();

        expect(demandaFisicaService.query).toHaveBeenCalled();
        expect(demandaFisicaService.addDemandaFisicaToCollectionIfMissing).toHaveBeenCalledWith(demandaCollection, demanda);
        expect(comp.demandasCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const demandanteFisico: IDemandanteFisico = { id: 456 };
        const demanda: IDemandaFisica = { id: 43800 };
        demandanteFisico.demanda = demanda;

        activatedRoute.data = of({ demandanteFisico });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(demandanteFisico));
        expect(comp.demandasCollection).toContain(demanda);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<DemandanteFisico>>();
        const demandanteFisico = { id: 123 };
        jest.spyOn(demandanteFisicoService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ demandanteFisico });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: demandanteFisico }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(demandanteFisicoService.update).toHaveBeenCalledWith(demandanteFisico);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<DemandanteFisico>>();
        const demandanteFisico = new DemandanteFisico();
        jest.spyOn(demandanteFisicoService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ demandanteFisico });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: demandanteFisico }));
        saveSubject.complete();

        // THEN
        expect(demandanteFisicoService.create).toHaveBeenCalledWith(demandanteFisico);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<DemandanteFisico>>();
        const demandanteFisico = { id: 123 };
        jest.spyOn(demandanteFisicoService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ demandanteFisico });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(demandanteFisicoService.update).toHaveBeenCalledWith(demandanteFisico);
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
    });
  });
});
