package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DemandaFisicaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DemandaFisica.class);
        DemandaFisica demandaFisica1 = new DemandaFisica();
        demandaFisica1.setId(1L);
        DemandaFisica demandaFisica2 = new DemandaFisica();
        demandaFisica2.setId(demandaFisica1.getId());
        assertThat(demandaFisica1).isEqualTo(demandaFisica2);
        demandaFisica2.setId(2L);
        assertThat(demandaFisica1).isNotEqualTo(demandaFisica2);
        demandaFisica1.setId(null);
        assertThat(demandaFisica1).isNotEqualTo(demandaFisica2);
    }
}
