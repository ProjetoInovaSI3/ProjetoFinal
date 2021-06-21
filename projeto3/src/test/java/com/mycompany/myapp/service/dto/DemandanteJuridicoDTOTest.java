package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DemandanteJuridicoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DemandanteJuridicoDTO.class);
        DemandanteJuridicoDTO demandanteJuridicoDTO1 = new DemandanteJuridicoDTO();
        demandanteJuridicoDTO1.setId(1L);
        DemandanteJuridicoDTO demandanteJuridicoDTO2 = new DemandanteJuridicoDTO();
        assertThat(demandanteJuridicoDTO1).isNotEqualTo(demandanteJuridicoDTO2);
        demandanteJuridicoDTO2.setId(demandanteJuridicoDTO1.getId());
        assertThat(demandanteJuridicoDTO1).isEqualTo(demandanteJuridicoDTO2);
        demandanteJuridicoDTO2.setId(2L);
        assertThat(demandanteJuridicoDTO1).isNotEqualTo(demandanteJuridicoDTO2);
        demandanteJuridicoDTO1.setId(null);
        assertThat(demandanteJuridicoDTO1).isNotEqualTo(demandanteJuridicoDTO2);
    }
}
