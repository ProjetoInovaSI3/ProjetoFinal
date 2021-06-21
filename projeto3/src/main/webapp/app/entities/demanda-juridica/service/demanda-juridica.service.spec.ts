import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDemandaJuridica, DemandaJuridica } from '../demanda-juridica.model';

import { DemandaJuridicaService } from './demanda-juridica.service';

describe('Service Tests', () => {
  describe('DemandaJuridica Service', () => {
    let service: DemandaJuridicaService;
    let httpMock: HttpTestingController;
    let elemDefault: IDemandaJuridica;
    let expectedResult: IDemandaJuridica | IDemandaJuridica[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(DemandaJuridicaService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        descricao: 'AAAAAAA',
        curso: 'AAAAAAA',
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a DemandaJuridica', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new DemandaJuridica()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a DemandaJuridica', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            descricao: 'BBBBBB',
            curso: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a DemandaJuridica', () => {
        const patchObject = Object.assign(
          {
            descricao: 'BBBBBB',
            curso: 'BBBBBB',
          },
          new DemandaJuridica()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of DemandaJuridica', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            descricao: 'BBBBBB',
            curso: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a DemandaJuridica', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addDemandaJuridicaToCollectionIfMissing', () => {
        it('should add a DemandaJuridica to an empty array', () => {
          const demandaJuridica: IDemandaJuridica = { id: 123 };
          expectedResult = service.addDemandaJuridicaToCollectionIfMissing([], demandaJuridica);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(demandaJuridica);
        });

        it('should not add a DemandaJuridica to an array that contains it', () => {
          const demandaJuridica: IDemandaJuridica = { id: 123 };
          const demandaJuridicaCollection: IDemandaJuridica[] = [
            {
              ...demandaJuridica,
            },
            { id: 456 },
          ];
          expectedResult = service.addDemandaJuridicaToCollectionIfMissing(demandaJuridicaCollection, demandaJuridica);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a DemandaJuridica to an array that doesn't contain it", () => {
          const demandaJuridica: IDemandaJuridica = { id: 123 };
          const demandaJuridicaCollection: IDemandaJuridica[] = [{ id: 456 }];
          expectedResult = service.addDemandaJuridicaToCollectionIfMissing(demandaJuridicaCollection, demandaJuridica);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(demandaJuridica);
        });

        it('should add only unique DemandaJuridica to an array', () => {
          const demandaJuridicaArray: IDemandaJuridica[] = [{ id: 123 }, { id: 456 }, { id: 1336 }];
          const demandaJuridicaCollection: IDemandaJuridica[] = [{ id: 123 }];
          expectedResult = service.addDemandaJuridicaToCollectionIfMissing(demandaJuridicaCollection, ...demandaJuridicaArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const demandaJuridica: IDemandaJuridica = { id: 123 };
          const demandaJuridica2: IDemandaJuridica = { id: 456 };
          expectedResult = service.addDemandaJuridicaToCollectionIfMissing([], demandaJuridica, demandaJuridica2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(demandaJuridica);
          expect(expectedResult).toContain(demandaJuridica2);
        });

        it('should accept null and undefined values', () => {
          const demandaJuridica: IDemandaJuridica = { id: 123 };
          expectedResult = service.addDemandaJuridicaToCollectionIfMissing([], null, demandaJuridica, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(demandaJuridica);
        });

        it('should return initial array if no DemandaJuridica is added', () => {
          const demandaJuridicaCollection: IDemandaJuridica[] = [{ id: 123 }];
          expectedResult = service.addDemandaJuridicaToCollectionIfMissing(demandaJuridicaCollection, undefined, null);
          expect(expectedResult).toEqual(demandaJuridicaCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
