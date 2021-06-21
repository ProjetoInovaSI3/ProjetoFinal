package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DemandaJuridicaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DemandaJuridica.class);
        DemandaJuridica demandaJuridica1 = new DemandaJuridica();
        demandaJuridica1.setId(1L);
        DemandaJuridica demandaJuridica2 = new DemandaJuridica();
        demandaJuridica2.setId(demandaJuridica1.getId());
        assertThat(demandaJuridica1).isEqualTo(demandaJuridica2);
        demandaJuridica2.setId(2L);
        assertThat(demandaJuridica1).isNotEqualTo(demandaJuridica2);
        demandaJuridica1.setId(null);
        assertThat(demandaJuridica1).isNotEqualTo(demandaJuridica2);
    }
}
