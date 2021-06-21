jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IDemandanteJuridico, DemandanteJuridico } from '../demandante-juridico.model';
import { DemandanteJuridicoService } from '../service/demandante-juridico.service';

import { DemandanteJuridicoRoutingResolveService } from './demandante-juridico-routing-resolve.service';

describe('Service Tests', () => {
  describe('DemandanteJuridico routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: DemandanteJuridicoRoutingResolveService;
    let service: DemandanteJuridicoService;
    let resultDemandanteJuridico: IDemandanteJuridico | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(DemandanteJuridicoRoutingResolveService);
      service = TestBed.inject(DemandanteJuridicoService);
      resultDemandanteJuridico = undefined;
    });

    describe('resolve', () => {
      it('should return IDemandanteJuridico returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDemandanteJuridico = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultDemandanteJuridico).toEqual({ id: 123 });
      });

      it('should return new IDemandanteJuridico if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDemandanteJuridico = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultDemandanteJuridico).toEqual(new DemandanteJuridico());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as DemandanteJuridico })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDemandanteJuridico = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultDemandanteJuridico).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
