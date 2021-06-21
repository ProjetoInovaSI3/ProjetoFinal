import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDemandaFisica, getDemandaFisicaIdentifier } from '../demanda-fisica.model';

export type EntityResponseType = HttpResponse<IDemandaFisica>;
export type EntityArrayResponseType = HttpResponse<IDemandaFisica[]>;

@Injectable({ providedIn: 'root' })
export class DemandaFisicaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/demanda-fisicas');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(demandaFisica: IDemandaFisica): Observable<EntityResponseType> {
    return this.http.post<IDemandaFisica>(this.resourceUrl, demandaFisica, { observe: 'response' });
  }

  update(demandaFisica: IDemandaFisica): Observable<EntityResponseType> {
    return this.http.put<IDemandaFisica>(`${this.resourceUrl}/${getDemandaFisicaIdentifier(demandaFisica) as number}`, demandaFisica, {
      observe: 'response',
    });
  }

  partialUpdate(demandaFisica: IDemandaFisica): Observable<EntityResponseType> {
    return this.http.patch<IDemandaFisica>(`${this.resourceUrl}/${getDemandaFisicaIdentifier(demandaFisica) as number}`, demandaFisica, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDemandaFisica>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDemandaFisica[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDemandaFisicaToCollectionIfMissing(
    demandaFisicaCollection: IDemandaFisica[],
    ...demandaFisicasToCheck: (IDemandaFisica | null | undefined)[]
  ): IDemandaFisica[] {
    const demandaFisicas: IDemandaFisica[] = demandaFisicasToCheck.filter(isPresent);
    if (demandaFisicas.length > 0) {
      const demandaFisicaCollectionIdentifiers = demandaFisicaCollection.map(
        demandaFisicaItem => getDemandaFisicaIdentifier(demandaFisicaItem)!
      );
      const demandaFisicasToAdd = demandaFisicas.filter(demandaFisicaItem => {
        const demandaFisicaIdentifier = getDemandaFisicaIdentifier(demandaFisicaItem);
        if (demandaFisicaIdentifier == null || demandaFisicaCollectionIdentifiers.includes(demandaFisicaIdentifier)) {
          return false;
        }
        demandaFisicaCollectionIdentifiers.push(demandaFisicaIdentifier);
        return true;
      });
      return [...demandaFisicasToAdd, ...demandaFisicaCollection];
    }
    return demandaFisicaCollection;
  }
}
