jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { DemandanteJuridicoService } from '../service/demandante-juridico.service';
import { IDemandanteJuridico, DemandanteJuridico } from '../demandante-juridico.model';
import { IDemandaJuridica } from 'app/entities/demanda-juridica/demanda-juridica.model';
import { DemandaJuridicaService } from 'app/entities/demanda-juridica/service/demanda-juridica.service';

import { DemandanteJuridicoUpdateComponent } from './demandante-juridico-update.component';

describe('Component Tests', () => {
  describe('DemandanteJuridico Management Update Component', () => {
    let comp: DemandanteJuridicoUpdateComponent;
    let fixture: ComponentFixture<DemandanteJuridicoUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let demandanteJuridicoService: DemandanteJuridicoService;
    let demandaJuridicaService: DemandaJuridicaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [DemandanteJuridicoUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(DemandanteJuridicoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DemandanteJuridicoUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      demandanteJuridicoService = TestBed.inject(DemandanteJuridicoService);
      demandaJuridicaService = TestBed.inject(DemandaJuridicaService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call demanda query and add missing value', () => {
        const demandanteJuridico: IDemandanteJuridico = { id: 456 };
        const demanda: IDemandaJuridica = { id: 13099 };
        demandanteJuridico.demanda = demanda;

        const demandaCollection: IDemandaJuridica[] = [{ id: 5765 }];
        jest.spyOn(demandaJuridicaService, 'query').mockReturnValue(of(new HttpResponse({ body: demandaCollection })));
        const expectedCollection: IDemandaJuridica[] = [demanda, ...demandaCollection];
        jest.spyOn(demandaJuridicaService, 'addDemandaJuridicaToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ demandanteJuridico });
        comp.ngOnInit();

        expect(demandaJuridicaService.query).toHaveBeenCalled();
        expect(demandaJuridicaService.addDemandaJuridicaToCollectionIfMissing).toHaveBeenCalledWith(demandaCollection, demanda);
        expect(comp.demandasCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const demandanteJuridico: IDemandanteJuridico = { id: 456 };
        const demanda: IDemandaJuridica = { id: 8935 };
        demandanteJuridico.demanda = demanda;

        activatedRoute.data = of({ demandanteJuridico });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(demandanteJuridico));
        expect(comp.demandasCollection).toContain(demanda);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<DemandanteJuridico>>();
        const demandanteJuridico = { id: 123 };
        jest.spyOn(demandanteJuridicoService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ demandanteJuridico });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: demandanteJuridico }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(demandanteJuridicoService.update).toHaveBeenCalledWith(demandanteJuridico);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<DemandanteJuridico>>();
        const demandanteJuridico = new DemandanteJuridico();
        jest.spyOn(demandanteJuridicoService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ demandanteJuridico });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: demandanteJuridico }));
        saveSubject.complete();

        // THEN
        expect(demandanteJuridicoService.create).toHaveBeenCalledWith(demandanteJuridico);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<DemandanteJuridico>>();
        const demandanteJuridico = { id: 123 };
        jest.spyOn(demandanteJuridicoService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ demandanteJuridico });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(demandanteJuridicoService.update).toHaveBeenCalledWith(demandanteJuridico);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
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
