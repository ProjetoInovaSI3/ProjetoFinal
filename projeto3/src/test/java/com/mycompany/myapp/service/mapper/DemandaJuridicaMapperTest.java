package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DemandaJuridicaMapperTest {

    private DemandaJuridicaMapper demandaJuridicaMapper;

    @BeforeEach
    public void setUp() {
        demandaJuridicaMapper = new DemandaJuridicaMapperImpl();
    }
}
