import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DemandaJuridicaDetailComponent } from './demanda-juridica-detail.component';

describe('Component Tests', () => {
  describe('DemandaJuridica Management Detail Component', () => {
    let comp: DemandaJuridicaDetailComponent;
    let fixture: ComponentFixture<DemandaJuridicaDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [DemandaJuridicaDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ demandaJuridica: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(DemandaJuridicaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DemandaJuridicaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load demandaJuridica on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.demandaJuridica).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
