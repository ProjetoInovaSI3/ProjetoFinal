import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDemandanteFisico, DemandanteFisico } from '../demandante-fisico.model';

import { DemandanteFisicoService } from './demandante-fisico.service';

describe('Service Tests', () => {
  describe('DemandanteFisico Service', () => {
    let service: DemandanteFisicoService;
    let httpMock: HttpTestingController;
    let elemDefault: IDemandanteFisico;
    let expectedResult: IDemandanteFisico | IDemandanteFisico[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(DemandanteFisicoService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        cpf: 0,
        nomeCompleto: 'AAAAAAA',
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

      it('should create a DemandanteFisico', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new DemandanteFisico()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a DemandanteFisico', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            cpf: 1,
            nomeCompleto: 'BBBBBB',
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

      it('should partial update a DemandanteFisico', () => {
        const patchObject = Object.assign(
          {
            cpf: 1,
            email: 'BBBBBB',
          },
          new DemandanteFisico()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of DemandanteFisico', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            cpf: 1,
            nomeCompleto: 'BBBBBB',
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

      it('should delete a DemandanteFisico', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addDemandanteFisicoToCollectionIfMissing', () => {
        it('should add a DemandanteFisico to an empty array', () => {
          const demandanteFisico: IDemandanteFisico = { id: 123 };
          expectedResult = service.addDemandanteFisicoToCollectionIfMissing([], demandanteFisico);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(demandanteFisico);
        });

        it('should not add a DemandanteFisico to an array that contains it', () => {
          const demandanteFisico: IDemandanteFisico = { id: 123 };
          const demandanteFisicoCollection: IDemandanteFisico[] = [
            {
              ...demandanteFisico,
            },
            { id: 456 },
          ];
          expectedResult = service.addDemandanteFisicoToCollectionIfMissing(demandanteFisicoCollection, demandanteFisico);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a DemandanteFisico to an array that doesn't contain it", () => {
          const demandanteFisico: IDemandanteFisico = { id: 123 };
          const demandanteFisicoCollection: IDemandanteFisico[] = [{ id: 456 }];
          expectedResult = service.addDemandanteFisicoToCollectionIfMissing(demandanteFisicoCollection, demandanteFisico);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(demandanteFisico);
        });

        it('should add only unique DemandanteFisico to an array', () => {
          const demandanteFisicoArray: IDemandanteFisico[] = [{ id: 123 }, { id: 456 }, { id: 5785 }];
          const demandanteFisicoCollection: IDemandanteFisico[] = [{ id: 123 }];
          expectedResult = service.addDemandanteFisicoToCollectionIfMissing(demandanteFisicoCollection, ...demandanteFisicoArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const demandanteFisico: IDemandanteFisico = { id: 123 };
          const demandanteFisico2: IDemandanteFisico = { id: 456 };
          expectedResult = service.addDemandanteFisicoToCollectionIfMissing([], demandanteFisico, demandanteFisico2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(demandanteFisico);
          expect(expectedResult).toContain(demandanteFisico2);
        });

        it('should accept null and undefined values', () => {
          const demandanteFisico: IDemandanteFisico = { id: 123 };
          expectedResult = service.addDemandanteFisicoToCollectionIfMissing([], null, demandanteFisico, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(demandanteFisico);
        });

        it('should return initial array if no DemandanteFisico is added', () => {
          const demandanteFisicoCollection: IDemandanteFisico[] = [{ id: 123 }];
          expectedResult = service.addDemandanteFisicoToCollectionIfMissing(demandanteFisicoCollection, undefined, null);
          expect(expectedResult).toEqual(demandanteFisicoCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
