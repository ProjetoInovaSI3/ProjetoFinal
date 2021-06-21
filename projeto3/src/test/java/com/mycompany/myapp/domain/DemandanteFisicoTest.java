package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DemandanteFisicoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DemandanteFisico.class);
        DemandanteFisico demandanteFisico1 = new DemandanteFisico();
        demandanteFisico1.setId(1L);
        DemandanteFisico demandanteFisico2 = new DemandanteFisico();
        demandanteFisico2.setId(demandanteFisico1.getId());
        assertThat(demandanteFisico1).isEqualTo(demandanteFisico2);
        demandanteFisico2.setId(2L);
        assertThat(demandanteFisico1).isNotEqualTo(demandanteFisico2);
        demandanteFisico1.setId(null);
        assertThat(demandanteFisico1).isNotEqualTo(demandanteFisico2);
    }
}
