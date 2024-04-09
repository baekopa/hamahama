package com.baekopa.backend.practice;

import org.junit.jupiter.api.*;

class MultiFlexApiApplicationTests {

    @Test
    void 두_숫자의_합_테스트() {
        System.out.println("[+] @test assertEquals Annotation");
        int a = 5;
        int b = 7;
        int expectedSum = 12;
        int actualSum = a + b;
        Assertions.assertEquals(expectedSum, actualSum);
    }

    @Test
    void Object_NULL_여부_테스트() {
        Object obj = null;
        Assertions.assertNull(obj);
        System.out.println("[+] @test assertNull Annotation");
    }

    // 각 테스트 메소드 실행 전에 실행되는 메소드를 정의하는 어노테이션
    @BeforeEach
    void setUp() {
        System.out.println("@BeforeEach Annotation");
    }

    // 각 테스트 메소드 실행 후에 실행되는 메소드를 정의하는 어노테이션
    @AfterEach
    void cleanUp() {
        System.out.println("@AfterEach Annotation");
    }

    // 클래스 내의 모든 테스트 메서드 실행 전에 한 번 호출되는 메서드
    @BeforeAll
    static void setUpBeforeClass() {
        System.out.println("@BeforeAll Annotation");
    }

    // 클래스 내의 모든 테스트 메서드 실행 후에 한 번 호출되는 메서드
    @AfterAll
    static void cleanUpAfterClass() {
        System.out.println("@AfterAll Annotation");
    }
}
