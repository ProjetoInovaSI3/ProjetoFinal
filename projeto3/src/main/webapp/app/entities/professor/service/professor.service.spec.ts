import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IProfessor, Professor } from '../professor.model';

import { ProfessorService } from './professor.service';

describe('Service Tests', () => {
  describe('Professor Service', () => {
    let service: ProfessorService;
    let httpMock: HttpTestingController;
    let elemDefault: IProfessor;
    let expectedResult: IProfessor | IProfessor[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(ProfessorService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        curso: 'AAAAAAA',
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

      it('should create a Professor', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Professor()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Professor', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            curso: 'BBBBBB',
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

      it('should partial update a Professor', () => {
        const patchObject = Object.assign(
          {
            curso: 'BBBBBB',
            nomeCompleto: 'BBBBBB',
          },
          new Professor()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Professor', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            curso: 'BBBBBB',
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

      it('should delete a Professor', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addProfessorToCollectionIfMissing', () => {
        it('should add a Professor to an empty array', () => {
          const professor: IProfessor = { id: 123 };
          expectedResult = service.addProfessorToCollectionIfMissing([], professor);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(professor);
        });

        it('should not add a Professor to an array that contains it', () => {
          const professor: IProfessor = { id: 123 };
          const professorCollection: IProfessor[] = [
            {
              ...professor,
            },
            { id: 456 },
          ];
          expectedResult = service.addProfessorToCollectionIfMissing(professorCollection, professor);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Professor to an array that doesn't contain it", () => {
          const professor: IProfessor = { id: 123 };
          const professorCollection: IProfessor[] = [{ id: 456 }];
          expectedResult = service.addProfessorToCollectionIfMissing(professorCollection, professor);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(professor);
        });

        it('should add only unique Professor to an array', () => {
          const professorArray: IProfessor[] = [{ id: 123 }, { id: 456 }, { id: 15892 }];
          const professorCollection: IProfessor[] = [{ id: 123 }];
          expectedResult = service.addProfessorToCollectionIfMissing(professorCollection, ...professorArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const professor: IProfessor = { id: 123 };
          const professor2: IProfessor = { id: 456 };
          expectedResult = service.addProfessorToCollectionIfMissing([], professor, professor2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(professor);
          expect(expectedResult).toContain(professor2);
        });

        it('should accept null and undefined values', () => {
          const professor: IProfessor = { id: 123 };
          expectedResult = service.addProfessorToCollectionIfMissing([], null, professor, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(professor);
        });

        it('should return initial array if no Professor is added', () => {
          const professorCollection: IProfessor[] = [{ id: 123 }];
          expectedResult = service.addProfessorToCollectionIfMissing(professorCollection, undefined, null);
          expect(expectedResult).toEqual(professorCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
