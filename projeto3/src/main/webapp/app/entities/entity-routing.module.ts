import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'endereco',
        data: { pageTitle: 'Enderecos' },
        loadChildren: () => import('./endereco/endereco.module').then(m => m.EnderecoModule),
      },
      {
        path: 'demandante-juridico',
        data: { pageTitle: 'DemandanteJuridicos' },
        loadChildren: () => import('./demandante-juridico/demandante-juridico.module').then(m => m.DemandanteJuridicoModule),
      },
      {
        path: 'demandante-fisico',
        data: { pageTitle: 'DemandanteFisicos' },
        loadChildren: () => import('./demandante-fisico/demandante-fisico.module').then(m => m.DemandanteFisicoModule),
      },
      {
        path: 'curso',
        data: { pageTitle: 'Cursos' },
        loadChildren: () => import('./curso/curso.module').then(m => m.CursoModule),
      },
      {
        path: 'professor',
        data: { pageTitle: 'Professors' },
        loadChildren: () => import('./professor/professor.module').then(m => m.ProfessorModule),
      },
      {
        path: 'demanda-fisica',
        data: { pageTitle: 'DemandaFisicas' },
        loadChildren: () => import('./demanda-fisica/demanda-fisica.module').then(m => m.DemandaFisicaModule),
      },
      {
        path: 'demanda-juridica',
        data: { pageTitle: 'DemandaJuridicas' },
        loadChildren: () => import('./demanda-juridica/demanda-juridica.module').then(m => m.DemandaJuridicaModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
