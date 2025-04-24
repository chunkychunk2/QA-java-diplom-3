package ru.praktikum.services.utils;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static ru.praktikum.services.utils.Constants.BASEURL;

public class Specification {

    public static RequestSpecification RequestSpec() {
        return new RequestSpecBuilder()
                .log(LogDetail.METHOD)
                .log(LogDetail.URI)
                .log(LogDetail.PARAMS)
                .log(LogDetail.HEADERS)
                .log(LogDetail.BODY)
                .setBaseUri(BASEURL)
                .addFilter(new AllureRestAssured())
                .setContentType(ContentType.JSON)
                .build();
    }

    public static void installRequestSpec(RequestSpecification requestSpec) {
        RestAssured.requestSpecification = requestSpec;
    }
}