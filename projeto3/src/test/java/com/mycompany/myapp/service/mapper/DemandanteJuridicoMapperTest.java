package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DemandanteJuridicoMapperTest {

    private DemandanteJuridicoMapper demandanteJuridicoMapper;

    @BeforeEach
    public void setUp() {
        demandanteJuridicoMapper = new DemandanteJuridicoMapperImpl();
    }
}
