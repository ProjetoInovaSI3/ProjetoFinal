import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DemandanteJuridicoDetailComponent } from './demandante-juridico-detail.component';

describe('Component Tests', () => {
  describe('DemandanteJuridico Management Detail Component', () => {
    let comp: DemandanteJuridicoDetailComponent;
    let fixture: ComponentFixture<DemandanteJuridicoDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [DemandanteJuridicoDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ demandanteJuridico: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(DemandanteJuridicoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DemandanteJuridicoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load demandanteJuridico on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.demandanteJuridico).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
