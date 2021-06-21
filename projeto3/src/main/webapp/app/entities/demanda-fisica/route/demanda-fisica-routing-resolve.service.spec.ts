jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IDemandaFisica, DemandaFisica } from '../demanda-fisica.model';
import { DemandaFisicaService } from '../service/demanda-fisica.service';

import { DemandaFisicaRoutingResolveService } from './demanda-fisica-routing-resolve.service';

describe('Service Tests', () => {
  describe('DemandaFisica routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: DemandaFisicaRoutingResolveService;
    let service: DemandaFisicaService;
    let resultDemandaFisica: IDemandaFisica | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(DemandaFisicaRoutingResolveService);
      service = TestBed.inject(DemandaFisicaService);
      resultDemandaFisica = undefined;
    });

    describe('resolve', () => {
      it('should return IDemandaFisica returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDemandaFisica = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultDemandaFisica).toEqual({ id: 123 });
      });

      it('should return new IDemandaFisica if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDemandaFisica = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultDemandaFisica).toEqual(new DemandaFisica());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as DemandaFisica })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDemandaFisica = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultDemandaFisica).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
