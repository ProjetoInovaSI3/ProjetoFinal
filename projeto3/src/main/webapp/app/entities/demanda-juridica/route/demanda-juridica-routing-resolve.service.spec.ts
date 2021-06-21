jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IDemandaJuridica, DemandaJuridica } from '../demanda-juridica.model';
import { DemandaJuridicaService } from '../service/demanda-juridica.service';

import { DemandaJuridicaRoutingResolveService } from './demanda-juridica-routing-resolve.service';

describe('Service Tests', () => {
  describe('DemandaJuridica routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: DemandaJuridicaRoutingResolveService;
    let service: DemandaJuridicaService;
    let resultDemandaJuridica: IDemandaJuridica | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(DemandaJuridicaRoutingResolveService);
      service = TestBed.inject(DemandaJuridicaService);
      resultDemandaJuridica = undefined;
    });

    describe('resolve', () => {
      it('should return IDemandaJuridica returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDemandaJuridica = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultDemandaJuridica).toEqual({ id: 123 });
      });

      it('should return new IDemandaJuridica if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDemandaJuridica = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultDemandaJuridica).toEqual(new DemandaJuridica());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as DemandaJuridica })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDemandaJuridica = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultDemandaJuridica).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
