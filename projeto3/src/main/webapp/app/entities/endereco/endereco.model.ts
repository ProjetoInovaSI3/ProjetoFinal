import { IDemandaFisica } from 'app/entities/demanda-fisica/demanda-fisica.model';

export interface IEndereco {
  id?: number;
  cep?: number | null;
  logradouro?: string | null;
  bairro?: string | null;
  numero?: string | null;
  uf?: string | null;
  ddd?: number | null;
  demandas?: IDemandaFisica[] | null;
}

export class Endereco implements IEndereco {
  constructor(
    public id?: number,
    public cep?: number | null,
    public logradouro?: string | null,
    public bairro?: string | null,
    public numero?: string | null,
    public uf?: string | null,
    public ddd?: number | null,
    public demandas?: IDemandaFisica[] | null
  ) {}
}

export function getEnderecoIdentifier(endereco: IEndereco): number | undefined {
  return endereco.id;
}
