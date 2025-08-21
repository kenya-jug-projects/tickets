package com.owino;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
public class TitheCalculatorTest {
    @Test
    public void shouldCalculateTitheTest(){
        var titheCalculatorCore = new TitheCalculatorCore();
        // 1. Get member income
        // 2. Derive 10% of income
        // 3. Return as tithe amount
        var income = 100.0; //100 Ksh
        var actual = titheCalculatorCore.getTithe(income);
        var expected = 10.0;
        assertThat(actual).isEqualTo(expected);
    }
}
