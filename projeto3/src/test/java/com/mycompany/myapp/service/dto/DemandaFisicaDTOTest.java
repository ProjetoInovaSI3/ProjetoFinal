package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DemandaFisicaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DemandaFisicaDTO.class);
        DemandaFisicaDTO demandaFisicaDTO1 = new DemandaFisicaDTO();
        demandaFisicaDTO1.setId(1L);
        DemandaFisicaDTO demandaFisicaDTO2 = new DemandaFisicaDTO();
        assertThat(demandaFisicaDTO1).isNotEqualTo(demandaFisicaDTO2);
        demandaFisicaDTO2.setId(demandaFisicaDTO1.getId());
        assertThat(demandaFisicaDTO1).isEqualTo(demandaFisicaDTO2);
        demandaFisicaDTO2.setId(2L);
        assertThat(demandaFisicaDTO1).isNotEqualTo(demandaFisicaDTO2);
        demandaFisicaDTO1.setId(null);
        assertThat(demandaFisicaDTO1).isNotEqualTo(demandaFisicaDTO2);
    }
}
