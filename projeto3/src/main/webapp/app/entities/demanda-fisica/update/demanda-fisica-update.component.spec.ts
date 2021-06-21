jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { DemandaFisicaService } from '../service/demanda-fisica.service';
import { IDemandaFisica, DemandaFisica } from '../demanda-fisica.model';
import { IEndereco } from 'app/entities/endereco/endereco.model';
import { EnderecoService } from 'app/entities/endereco/service/endereco.service';

import { DemandaFisicaUpdateComponent } from './demanda-fisica-update.component';

describe('Component Tests', () => {
  describe('DemandaFisica Management Update Component', () => {
    let comp: DemandaFisicaUpdateComponent;
    let fixture: ComponentFixture<DemandaFisicaUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let demandaFisicaService: DemandaFisicaService;
    let enderecoService: EnderecoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [DemandaFisicaUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(DemandaFisicaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DemandaFisicaUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      demandaFisicaService = TestBed.inject(DemandaFisicaService);
      enderecoService = TestBed.inject(EnderecoService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Endereco query and add missing value', () => {
        const demandaFisica: IDemandaFisica = { id: 456 };
        const enderecos: IEndereco[] = [{ id: 67738 }];
        demandaFisica.enderecos = enderecos;

        const enderecoCollection: IEndereco[] = [{ id: 56941 }];
        jest.spyOn(enderecoService, 'query').mockReturnValue(of(new HttpResponse({ body: enderecoCollection })));
        const additionalEnderecos = [...enderecos];
        const expectedCollection: IEndereco[] = [...additionalEnderecos, ...enderecoCollection];
        jest.spyOn(enderecoService, 'addEnderecoToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ demandaFisica });
        comp.ngOnInit();

        expect(enderecoService.query).toHaveBeenCalled();
        expect(enderecoService.addEnderecoToCollectionIfMissing).toHaveBeenCalledWith(enderecoCollection, ...additionalEnderecos);
        expect(comp.enderecosSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const demandaFisica: IDemandaFisica = { id: 456 };
        const enderecos: IEndereco = { id: 80593 };
        demandaFisica.enderecos = [enderecos];

        activatedRoute.data = of({ demandaFisica });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(demandaFisica));
        expect(comp.enderecosSharedCollection).toContain(enderecos);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<DemandaFisica>>();
        const demandaFisica = { id: 123 };
        jest.spyOn(demandaFisicaService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ demandaFisica });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: demandaFisica }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(demandaFisicaService.update).toHaveBeenCalledWith(demandaFisica);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<DemandaFisica>>();
        const demandaFisica = new DemandaFisica();
        jest.spyOn(demandaFisicaService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ demandaFisica });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: demandaFisica }));
        saveSubject.complete();

        // THEN
        expect(demandaFisicaService.create).toHaveBeenCalledWith(demandaFisica);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<DemandaFisica>>();
        const demandaFisica = { id: 123 };
        jest.spyOn(demandaFisicaService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ demandaFisica });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(demandaFisicaService.update).toHaveBeenCalledWith(demandaFisica);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackEnderecoById', () => {
        it('Should return tracked Endereco primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackEnderecoById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });

    describe('Getting selected relationships', () => {
      describe('getSelectedEndereco', () => {
        it('Should return option if no Endereco is selected', () => {
          const option = { id: 123 };
          const result = comp.getSelectedEndereco(option);
          expect(result === option).toEqual(true);
        });

        it('Should return selected Endereco for according option', () => {
          const option = { id: 123 };
          const selected = { id: 123 };
          const selected2 = { id: 456 };
          const result = comp.getSelectedEndereco(option, [selected2, selected]);
          expect(result === selected).toEqual(true);
          expect(result === selected2).toEqual(false);
          expect(result === option).toEqual(false);
        });

        it('Should return option if this Endereco is not selected', () => {
          const option = { id: 123 };
          const selected = { id: 456 };
          const result = comp.getSelectedEndereco(option, [selected]);
          expect(result === option).toEqual(true);
          expect(result === selected).toEqual(false);
        });
      });
    });
  });
});
