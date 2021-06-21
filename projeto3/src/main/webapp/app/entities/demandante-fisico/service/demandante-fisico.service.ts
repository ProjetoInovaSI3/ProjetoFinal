import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDemandanteFisico, getDemandanteFisicoIdentifier } from '../demandante-fisico.model';

export type EntityResponseType = HttpResponse<IDemandanteFisico>;
export type EntityArrayResponseType = HttpResponse<IDemandanteFisico[]>;

@Injectable({ providedIn: 'root' })
export class DemandanteFisicoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/demandante-fisicos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(demandanteFisico: IDemandanteFisico): Observable<EntityResponseType> {
    return this.http.post<IDemandanteFisico>(this.resourceUrl, demandanteFisico, { observe: 'response' });
  }

  update(demandanteFisico: IDemandanteFisico): Observable<EntityResponseType> {
    return this.http.put<IDemandanteFisico>(
      `${this.resourceUrl}/${getDemandanteFisicoIdentifier(demandanteFisico) as number}`,
      demandanteFisico,
      { observe: 'response' }
    );
  }

  partialUpdate(demandanteFisico: IDemandanteFisico): Observable<EntityResponseType> {
    return this.http.patch<IDemandanteFisico>(
      `${this.resourceUrl}/${getDemandanteFisicoIdentifier(demandanteFisico) as number}`,
      demandanteFisico,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDemandanteFisico>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDemandanteFisico[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDemandanteFisicoToCollectionIfMissing(
    demandanteFisicoCollection: IDemandanteFisico[],
    ...demandanteFisicosToCheck: (IDemandanteFisico | null | undefined)[]
  ): IDemandanteFisico[] {
    const demandanteFisicos: IDemandanteFisico[] = demandanteFisicosToCheck.filter(isPresent);
    if (demandanteFisicos.length > 0) {
      const demandanteFisicoCollectionIdentifiers = demandanteFisicoCollection.map(
        demandanteFisicoItem => getDemandanteFisicoIdentifier(demandanteFisicoItem)!
      );
      const demandanteFisicosToAdd = demandanteFisicos.filter(demandanteFisicoItem => {
        const demandanteFisicoIdentifier = getDemandanteFisicoIdentifier(demandanteFisicoItem);
        if (demandanteFisicoIdentifier == null || demandanteFisicoCollectionIdentifiers.includes(demandanteFisicoIdentifier)) {
          return false;
        }
        demandanteFisicoCollectionIdentifiers.push(demandanteFisicoIdentifier);
        return true;
      });
      return [...demandanteFisicosToAdd, ...demandanteFisicoCollection];
    }
    return demandanteFisicoCollection;
  }
}
