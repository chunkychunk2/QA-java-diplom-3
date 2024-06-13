package ru.praktikum.services;

import io.restassured.RestAssured;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.After;
import org.junit.Before;

import static ru.praktikum.services.utils.Specification.RequestSpec;
import static ru.praktikum.services.utils.Specification.installRequestSpec;

public class Hooks {

    @Before
    public void getRequestSpecification() {
        installRequestSpec(RequestSpec());
    }

    @Before
    public void getResponseSpecification() {
        RestAssured.filters(new ResponseLoggingFilter());
    }

    @After
    public void resetSpecification() {
        RestAssured.reset();
    }
}
