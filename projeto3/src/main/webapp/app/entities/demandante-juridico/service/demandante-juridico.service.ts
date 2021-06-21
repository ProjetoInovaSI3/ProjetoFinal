import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDemandanteJuridico, getDemandanteJuridicoIdentifier } from '../demandante-juridico.model';

export type EntityResponseType = HttpResponse<IDemandanteJuridico>;
export type EntityArrayResponseType = HttpResponse<IDemandanteJuridico[]>;

@Injectable({ providedIn: 'root' })
export class DemandanteJuridicoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/demandante-juridicos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(demandanteJuridico: IDemandanteJuridico): Observable<EntityResponseType> {
    return this.http.post<IDemandanteJuridico>(this.resourceUrl, demandanteJuridico, { observe: 'response' });
  }

  update(demandanteJuridico: IDemandanteJuridico): Observable<EntityResponseType> {
    return this.http.put<IDemandanteJuridico>(
      `${this.resourceUrl}/${getDemandanteJuridicoIdentifier(demandanteJuridico) as number}`,
      demandanteJuridico,
      { observe: 'response' }
    );
  }

  partialUpdate(demandanteJuridico: IDemandanteJuridico): Observable<EntityResponseType> {
    return this.http.patch<IDemandanteJuridico>(
      `${this.resourceUrl}/${getDemandanteJuridicoIdentifier(demandanteJuridico) as number}`,
      demandanteJuridico,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDemandanteJuridico>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDemandanteJuridico[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDemandanteJuridicoToCollectionIfMissing(
    demandanteJuridicoCollection: IDemandanteJuridico[],
    ...demandanteJuridicosToCheck: (IDemandanteJuridico | null | undefined)[]
  ): IDemandanteJuridico[] {
    const demandanteJuridicos: IDemandanteJuridico[] = demandanteJuridicosToCheck.filter(isPresent);
    if (demandanteJuridicos.length > 0) {
      const demandanteJuridicoCollectionIdentifiers = demandanteJuridicoCollection.map(
        demandanteJuridicoItem => getDemandanteJuridicoIdentifier(demandanteJuridicoItem)!
      );
      const demandanteJuridicosToAdd = demandanteJuridicos.filter(demandanteJuridicoItem => {
        const demandanteJuridicoIdentifier = getDemandanteJuridicoIdentifier(demandanteJuridicoItem);
        if (demandanteJuridicoIdentifier == null || demandanteJuridicoCollectionIdentifiers.includes(demandanteJuridicoIdentifier)) {
          return false;
        }
        demandanteJuridicoCollectionIdentifiers.push(demandanteJuridicoIdentifier);
        return true;
      });
      return [...demandanteJuridicosToAdd, ...demandanteJuridicoCollection];
    }
    return demandanteJuridicoCollection;
  }
}
