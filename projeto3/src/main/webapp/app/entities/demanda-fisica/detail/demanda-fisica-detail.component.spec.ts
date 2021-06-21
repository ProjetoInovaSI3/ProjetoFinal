import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DemandaFisicaDetailComponent } from './demanda-fisica-detail.component';

describe('Component Tests', () => {
  describe('DemandaFisica Management Detail Component', () => {
    let comp: DemandaFisicaDetailComponent;
    let fixture: ComponentFixture<DemandaFisicaDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [DemandaFisicaDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ demandaFisica: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(DemandaFisicaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DemandaFisicaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load demandaFisica on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.demandaFisica).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
