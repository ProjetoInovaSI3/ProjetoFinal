package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DemandaJuridicaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DemandaJuridicaDTO.class);
        DemandaJuridicaDTO demandaJuridicaDTO1 = new DemandaJuridicaDTO();
        demandaJuridicaDTO1.setId(1L);
        DemandaJuridicaDTO demandaJuridicaDTO2 = new DemandaJuridicaDTO();
        assertThat(demandaJuridicaDTO1).isNotEqualTo(demandaJuridicaDTO2);
        demandaJuridicaDTO2.setId(demandaJuridicaDTO1.getId());
        assertThat(demandaJuridicaDTO1).isEqualTo(demandaJuridicaDTO2);
        demandaJuridicaDTO2.setId(2L);
        assertThat(demandaJuridicaDTO1).isNotEqualTo(demandaJuridicaDTO2);
        demandaJuridicaDTO1.setId(null);
        assertThat(demandaJuridicaDTO1).isNotEqualTo(demandaJuridicaDTO2);
    }
}
