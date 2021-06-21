import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDemandanteJuridico, DemandanteJuridico } from '../demandante-juridico.model';

import { DemandanteJuridicoService } from './demandante-juridico.service';

describe('Service Tests', () => {
  describe('DemandanteJuridico Service', () => {
    let service: DemandanteJuridicoService;
    let httpMock: HttpTestingController;
    let elemDefault: IDemandanteJuridico;
    let expectedResult: IDemandanteJuridico | IDemandanteJuridico[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(DemandanteJuridicoService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        cnpj: 0,
        nomeDaEmpresa: 'AAAAAAA',
        nomefantasia: 'AAAAAAA',
        email: 'AAAAAAA',
        telefone: 0,
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

      it('should create a DemandanteJuridico', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new DemandanteJuridico()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a DemandanteJuridico', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            cnpj: 1,
            nomeDaEmpresa: 'BBBBBB',
            nomefantasia: 'BBBBBB',
            email: 'BBBBBB',
            telefone: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a DemandanteJuridico', () => {
        const patchObject = Object.assign(
          {
            nomefantasia: 'BBBBBB',
          },
          new DemandanteJuridico()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of DemandanteJuridico', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            cnpj: 1,
            nomeDaEmpresa: 'BBBBBB',
            nomefantasia: 'BBBBBB',
            email: 'BBBBBB',
            telefone: 1,
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

      it('should delete a DemandanteJuridico', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addDemandanteJuridicoToCollectionIfMissing', () => {
        it('should add a DemandanteJuridico to an empty array', () => {
          const demandanteJuridico: IDemandanteJuridico = { id: 123 };
          expectedResult = service.addDemandanteJuridicoToCollectionIfMissing([], demandanteJuridico);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(demandanteJuridico);
        });

        it('should not add a DemandanteJuridico to an array that contains it', () => {
          const demandanteJuridico: IDemandanteJuridico = { id: 123 };
          const demandanteJuridicoCollection: IDemandanteJuridico[] = [
            {
              ...demandanteJuridico,
            },
            { id: 456 },
          ];
          expectedResult = service.addDemandanteJuridicoToCollectionIfMissing(demandanteJuridicoCollection, demandanteJuridico);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a DemandanteJuridico to an array that doesn't contain it", () => {
          const demandanteJuridico: IDemandanteJuridico = { id: 123 };
          const demandanteJuridicoCollection: IDemandanteJuridico[] = [{ id: 456 }];
          expectedResult = service.addDemandanteJuridicoToCollectionIfMissing(demandanteJuridicoCollection, demandanteJuridico);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(demandanteJuridico);
        });

        it('should add only unique DemandanteJuridico to an array', () => {
          const demandanteJuridicoArray: IDemandanteJuridico[] = [{ id: 123 }, { id: 456 }, { id: 69639 }];
          const demandanteJuridicoCollection: IDemandanteJuridico[] = [{ id: 123 }];
          expectedResult = service.addDemandanteJuridicoToCollectionIfMissing(demandanteJuridicoCollection, ...demandanteJuridicoArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const demandanteJuridico: IDemandanteJuridico = { id: 123 };
          const demandanteJuridico2: IDemandanteJuridico = { id: 456 };
          expectedResult = service.addDemandanteJuridicoToCollectionIfMissing([], demandanteJuridico, demandanteJuridico2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(demandanteJuridico);
          expect(expectedResult).toContain(demandanteJuridico2);
        });

        it('should accept null and undefined values', () => {
          const demandanteJuridico: IDemandanteJuridico = { id: 123 };
          expectedResult = service.addDemandanteJuridicoToCollectionIfMissing([], null, demandanteJuridico, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(demandanteJuridico);
        });

        it('should return initial array if no DemandanteJuridico is added', () => {
          const demandanteJuridicoCollection: IDemandanteJuridico[] = [{ id: 123 }];
          expectedResult = service.addDemandanteJuridicoToCollectionIfMissing(demandanteJuridicoCollection, undefined, null);
          expect(expectedResult).toEqual(demandanteJuridicoCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
