package com.vaadin.flow.connect;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
// https://github.com/vaadin/flow/issues/9792
public class SpringBootTestIT {

    @Test
    public void hello() {
        // To test if @SpringBootTest works
        Assert.assertTrue(true);
    }
}
