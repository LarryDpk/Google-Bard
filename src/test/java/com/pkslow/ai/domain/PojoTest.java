package com.pkslow.ai.domain;

import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsForAll;

public class PojoTest {
    @Test
    public void testPojo() {
        assertPojoMethodsForAll(BardRequest.class)
                .quickly()
                .testing(Method.CONSTRUCTOR)
                .testing(Method.GETTER)
                .testing(Method.SETTER)
                .testing(Method.EQUALS)
                .testing(Method.HASH_CODE)
                .testing(Method.TO_STRING)
                .areWellImplemented();

        assertPojoMethodsForAll(BardResponse.class)
                .quickly()
                .testing(Method.CONSTRUCTOR)
                .testing(Method.GETTER)
//                .testing(Method.SETTER)
//                .testing(Method.EQUALS)
//                .testing(Method.HASH_CODE)
//                .testing(Method.TO_STRING)
                .areWellImplemented();

        assertPojoMethodsForAll(Answer.class)
                .quickly()
                .testing(Method.CONSTRUCTOR)
                .testing(Method.GETTER)
                .testing(Method.TO_STRING)
                .areWellImplemented();

        assertPojoMethodsForAll(Image.class)
                .quickly()
                .testing(Method.CONSTRUCTOR)
                .testing(Method.GETTER)
                .testing(Method.TO_STRING)
                .areWellImplemented();
    }
}
