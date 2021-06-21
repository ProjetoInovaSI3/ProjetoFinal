import { ICurso } from 'app/entities/curso/curso.model';

export interface IDemandaJuridica {
  id?: number;
  descricao?: string | null;
  curso?: string | null;
  cursos?: ICurso[] | null;
}

export class DemandaJuridica implements IDemandaJuridica {
  constructor(public id?: number, public descricao?: string | null, public curso?: string | null, public cursos?: ICurso[] | null) {}
}

export function getDemandaJuridicaIdentifier(demandaJuridica: IDemandaJuridica): number | undefined {
  return demandaJuridica.id;
}
