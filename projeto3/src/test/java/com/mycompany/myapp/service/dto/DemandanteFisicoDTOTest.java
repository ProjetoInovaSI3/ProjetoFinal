package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DemandanteFisicoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DemandanteFisicoDTO.class);
        DemandanteFisicoDTO demandanteFisicoDTO1 = new DemandanteFisicoDTO();
        demandanteFisicoDTO1.setId(1L);
        DemandanteFisicoDTO demandanteFisicoDTO2 = new DemandanteFisicoDTO();
        assertThat(demandanteFisicoDTO1).isNotEqualTo(demandanteFisicoDTO2);
        demandanteFisicoDTO2.setId(demandanteFisicoDTO1.getId());
        assertThat(demandanteFisicoDTO1).isEqualTo(demandanteFisicoDTO2);
        demandanteFisicoDTO2.setId(2L);
        assertThat(demandanteFisicoDTO1).isNotEqualTo(demandanteFisicoDTO2);
        demandanteFisicoDTO1.setId(null);
        assertThat(demandanteFisicoDTO1).isNotEqualTo(demandanteFisicoDTO2);
    }
}
