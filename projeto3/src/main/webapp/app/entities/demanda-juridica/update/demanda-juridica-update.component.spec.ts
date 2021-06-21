jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { DemandaJuridicaService } from '../service/demanda-juridica.service';
import { IDemandaJuridica, DemandaJuridica } from '../demanda-juridica.model';

import { DemandaJuridicaUpdateComponent } from './demanda-juridica-update.component';

describe('Component Tests', () => {
  describe('DemandaJuridica Management Update Component', () => {
    let comp: DemandaJuridicaUpdateComponent;
    let fixture: ComponentFixture<DemandaJuridicaUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let demandaJuridicaService: DemandaJuridicaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [DemandaJuridicaUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(DemandaJuridicaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DemandaJuridicaUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      demandaJuridicaService = TestBed.inject(DemandaJuridicaService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const demandaJuridica: IDemandaJuridica = { id: 456 };

        activatedRoute.data = of({ demandaJuridica });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(demandaJuridica));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<DemandaJuridica>>();
        const demandaJuridica = { id: 123 };
        jest.spyOn(demandaJuridicaService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ demandaJuridica });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: demandaJuridica }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(demandaJuridicaService.update).toHaveBeenCalledWith(demandaJuridica);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<DemandaJuridica>>();
        const demandaJuridica = new DemandaJuridica();
        jest.spyOn(demandaJuridicaService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ demandaJuridica });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: demandaJuridica }));
        saveSubject.complete();

        // THEN
        expect(demandaJuridicaService.create).toHaveBeenCalledWith(demandaJuridica);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<DemandaJuridica>>();
        const demandaJuridica = { id: 123 };
        jest.spyOn(demandaJuridicaService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ demandaJuridica });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(demandaJuridicaService.update).toHaveBeenCalledWith(demandaJuridica);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
