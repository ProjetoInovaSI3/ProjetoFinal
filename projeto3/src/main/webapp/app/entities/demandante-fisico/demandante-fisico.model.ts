import { IDemandaFisica } from 'app/entities/demanda-fisica/demanda-fisica.model';

export interface IDemandanteFisico {
  id?: number;
  cpf?: number | null;
  nomeCompleto?: string | null;
  email?: string | null;
  telefone?: number | null;
  demanda?: IDemandaFisica | null;
}

export class DemandanteFisico implements IDemandanteFisico {
  constructor(
    public id?: number,
    public cpf?: number | null,
    public nomeCompleto?: string | null,
    public email?: string | null,
    public telefone?: number | null,
    public demanda?: IDemandaFisica | null
  ) {}
}

export function getDemandanteFisicoIdentifier(demandanteFisico: IDemandanteFisico): number | undefined {
  return demandanteFisico.id;
}
