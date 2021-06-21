import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DemandanteFisicoDetailComponent } from './demandante-fisico-detail.component';

describe('Component Tests', () => {
  describe('DemandanteFisico Management Detail Component', () => {
    let comp: DemandanteFisicoDetailComponent;
    let fixture: ComponentFixture<DemandanteFisicoDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [DemandanteFisicoDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ demandanteFisico: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(DemandanteFisicoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DemandanteFisicoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load demandanteFisico on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.demandanteFisico).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
