import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDemandaJuridica, getDemandaJuridicaIdentifier } from '../demanda-juridica.model';

export type EntityResponseType = HttpResponse<IDemandaJuridica>;
export type EntityArrayResponseType = HttpResponse<IDemandaJuridica[]>;

@Injectable({ providedIn: 'root' })
export class DemandaJuridicaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/demanda-juridicas');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(demandaJuridica: IDemandaJuridica): Observable<EntityResponseType> {
    return this.http.post<IDemandaJuridica>(this.resourceUrl, demandaJuridica, { observe: 'response' });
  }

  update(demandaJuridica: IDemandaJuridica): Observable<EntityResponseType> {
    return this.http.put<IDemandaJuridica>(
      `${this.resourceUrl}/${getDemandaJuridicaIdentifier(demandaJuridica) as number}`,
      demandaJuridica,
      { observe: 'response' }
    );
  }

  partialUpdate(demandaJuridica: IDemandaJuridica): Observable<EntityResponseType> {
    return this.http.patch<IDemandaJuridica>(
      `${this.resourceUrl}/${getDemandaJuridicaIdentifier(demandaJuridica) as number}`,
      demandaJuridica,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDemandaJuridica>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDemandaJuridica[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDemandaJuridicaToCollectionIfMissing(
    demandaJuridicaCollection: IDemandaJuridica[],
    ...demandaJuridicasToCheck: (IDemandaJuridica | null | undefined)[]
  ): IDemandaJuridica[] {
    const demandaJuridicas: IDemandaJuridica[] = demandaJuridicasToCheck.filter(isPresent);
    if (demandaJuridicas.length > 0) {
      const demandaJuridicaCollectionIdentifiers = demandaJuridicaCollection.map(
        demandaJuridicaItem => getDemandaJuridicaIdentifier(demandaJuridicaItem)!
      );
      const demandaJuridicasToAdd = demandaJuridicas.filter(demandaJuridicaItem => {
        const demandaJuridicaIdentifier = getDemandaJuridicaIdentifier(demandaJuridicaItem);
        if (demandaJuridicaIdentifier == null || demandaJuridicaCollectionIdentifiers.includes(demandaJuridicaIdentifier)) {
          return false;
        }
        demandaJuridicaCollectionIdentifiers.push(demandaJuridicaIdentifier);
        return true;
      });
      return [...demandaJuridicasToAdd, ...demandaJuridicaCollection];
    }
    return demandaJuridicaCollection;
  }
}
