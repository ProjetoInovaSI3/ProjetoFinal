import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDemandaFisica, DemandaFisica } from '../demanda-fisica.model';

import { DemandaFisicaService } from './demanda-fisica.service';

describe('Service Tests', () => {
  describe('DemandaFisica Service', () => {
    let service: DemandaFisicaService;
    let httpMock: HttpTestingController;
    let elemDefault: IDemandaFisica;
    let expectedResult: IDemandaFisica | IDemandaFisica[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(DemandaFisicaService);
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

      it('should create a DemandaFisica', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new DemandaFisica()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a DemandaFisica', () => {
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

      it('should partial update a DemandaFisica', () => {
        const patchObject = Object.assign(
          {
            descricao: 'BBBBBB',
            curso: 'BBBBBB',
          },
          new DemandaFisica()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of DemandaFisica', () => {
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

      it('should delete a DemandaFisica', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addDemandaFisicaToCollectionIfMissing', () => {
        it('should add a DemandaFisica to an empty array', () => {
          const demandaFisica: IDemandaFisica = { id: 123 };
          expectedResult = service.addDemandaFisicaToCollectionIfMissing([], demandaFisica);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(demandaFisica);
        });

        it('should not add a DemandaFisica to an array that contains it', () => {
          const demandaFisica: IDemandaFisica = { id: 123 };
          const demandaFisicaCollection: IDemandaFisica[] = [
            {
              ...demandaFisica,
            },
            { id: 456 },
          ];
          expectedResult = service.addDemandaFisicaToCollectionIfMissing(demandaFisicaCollection, demandaFisica);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a DemandaFisica to an array that doesn't contain it", () => {
          const demandaFisica: IDemandaFisica = { id: 123 };
          const demandaFisicaCollection: IDemandaFisica[] = [{ id: 456 }];
          expectedResult = service.addDemandaFisicaToCollectionIfMissing(demandaFisicaCollection, demandaFisica);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(demandaFisica);
        });

        it('should add only unique DemandaFisica to an array', () => {
          const demandaFisicaArray: IDemandaFisica[] = [{ id: 123 }, { id: 456 }, { id: 82631 }];
          const demandaFisicaCollection: IDemandaFisica[] = [{ id: 123 }];
          expectedResult = service.addDemandaFisicaToCollectionIfMissing(demandaFisicaCollection, ...demandaFisicaArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const demandaFisica: IDemandaFisica = { id: 123 };
          const demandaFisica2: IDemandaFisica = { id: 456 };
          expectedResult = service.addDemandaFisicaToCollectionIfMissing([], demandaFisica, demandaFisica2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(demandaFisica);
          expect(expectedResult).toContain(demandaFisica2);
        });

        it('should accept null and undefined values', () => {
          const demandaFisica: IDemandaFisica = { id: 123 };
          expectedResult = service.addDemandaFisicaToCollectionIfMissing([], null, demandaFisica, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(demandaFisica);
        });

        it('should return initial array if no DemandaFisica is added', () => {
          const demandaFisicaCollection: IDemandaFisica[] = [{ id: 123 }];
          expectedResult = service.addDemandaFisicaToCollectionIfMissing(demandaFisicaCollection, undefined, null);
          expect(expectedResult).toEqual(demandaFisicaCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
