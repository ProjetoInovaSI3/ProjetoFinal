package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DemandanteJuridicoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DemandanteJuridico.class);
        DemandanteJuridico demandanteJuridico1 = new DemandanteJuridico();
        demandanteJuridico1.setId(1L);
        DemandanteJuridico demandanteJuridico2 = new DemandanteJuridico();
        demandanteJuridico2.setId(demandanteJuridico1.getId());
        assertThat(demandanteJuridico1).isEqualTo(demandanteJuridico2);
        demandanteJuridico2.setId(2L);
        assertThat(demandanteJuridico1).isNotEqualTo(demandanteJuridico2);
        demandanteJuridico1.setId(null);
        assertThat(demandanteJuridico1).isNotEqualTo(demandanteJuridico2);
    }
}
