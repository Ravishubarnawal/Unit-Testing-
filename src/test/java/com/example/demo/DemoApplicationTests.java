package com.example.demo;

import ch.qos.logback.classic.spi.PackagingDataCalculator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class DemoApplicationTests {
	Calculator underTest = new Calculator();

	@Test
	void itShouldAddTwoNumbers() {
		//given
		int n1 = 20;
		int n2 = 30;
		//when
		int result = underTest.add(n1, n2);
		//then
		int expectedValue =50;
        assertThat(result).isEqualTo(expectedValue);
	}
   class Calculator {
		int add(int a, int b){
			return a+b;
		}
   }
}
