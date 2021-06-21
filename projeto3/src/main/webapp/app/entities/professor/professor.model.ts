import { ICurso } from 'app/entities/curso/curso.model';

export interface IProfessor {
  id?: number;
  curso?: string | null;
  nomeCompleto?: string | null;
  email?: string | null;
  telefone?: number | null;
  cursos?: ICurso[] | null;
}

export class Professor implements IProfessor {
  constructor(
    public id?: number,
    public curso?: string | null,
    public nomeCompleto?: string | null,
    public email?: string | null,
    public telefone?: number | null,
    public cursos?: ICurso[] | null
  ) {}
}

export function getProfessorIdentifier(professor: IProfessor): number | undefined {
  return professor.id;
}
