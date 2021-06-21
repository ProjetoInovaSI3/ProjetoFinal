import { ICurso } from 'app/entities/curso/curso.model';
import { IEndereco } from 'app/entities/endereco/endereco.model';

export interface IDemandaFisica {
  id?: number;
  descricao?: string | null;
  curso?: string | null;
  cursos?: ICurso[] | null;
  enderecos?: IEndereco[] | null;
}

export class DemandaFisica implements IDemandaFisica {
  constructor(
    public id?: number,
    public descricao?: string | null,
    public curso?: string | null,
    public cursos?: ICurso[] | null,
    public enderecos?: IEndereco[] | null
  ) {}
}

export function getDemandaFisicaIdentifier(demandaFisica: IDemandaFisica): number | undefined {
  return demandaFisica.id;
}
