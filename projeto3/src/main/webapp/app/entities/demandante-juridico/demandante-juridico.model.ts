import { IDemandaJuridica } from 'app/entities/demanda-juridica/demanda-juridica.model';

export interface IDemandanteJuridico {
  id?: number;
  cnpj?: number | null;
  nomeDaEmpresa?: string | null;
  nomefantasia?: string | null;
  email?: string | null;
  telefone?: number | null;
  demanda?: IDemandaJuridica | null;
}

export class DemandanteJuridico implements IDemandanteJuridico {
  constructor(
    public id?: number,
    public cnpj?: number | null,
    public nomeDaEmpresa?: string | null,
    public nomefantasia?: string | null,
    public email?: string | null,
    public telefone?: number | null,
    public demanda?: IDemandaJuridica | null
  ) {}
}

export function getDemandanteJuridicoIdentifier(demandanteJuridico: IDemandanteJuridico): number | undefined {
  return demandanteJuridico.id;
}
