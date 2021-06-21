import { IDemandaFisica } from 'app/entities/demanda-fisica/demanda-fisica.model';
import { IProfessor } from 'app/entities/professor/professor.model';
import { IDemandaJuridica } from 'app/entities/demanda-juridica/demanda-juridica.model';

export interface ICurso {
  id?: number;
  nomeDoCurso?: string | null;
  turma?: string | null;
  demandaFisica?: IDemandaFisica | null;
  professor?: IProfessor | null;
  demandaJuridica?: IDemandaJuridica | null;
}

export class Curso implements ICurso {
  constructor(
    public id?: number,
    public nomeDoCurso?: string | null,
    public turma?: string | null,
    public demandaFisica?: IDemandaFisica | null,
    public professor?: IProfessor | null,
    public demandaJuridica?: IDemandaJuridica | null
  ) {}
}

export function getCursoIdentifier(curso: ICurso): number | undefined {
  return curso.id;
}
