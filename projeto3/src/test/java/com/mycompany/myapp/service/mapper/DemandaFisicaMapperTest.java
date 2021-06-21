package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DemandaFisicaMapperTest {

    private DemandaFisicaMapper demandaFisicaMapper;

    @BeforeEach
    public void setUp() {
        demandaFisicaMapper = new DemandaFisicaMapperImpl();
    }
}
