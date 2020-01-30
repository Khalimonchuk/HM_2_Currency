package com.lits.maksymuk.calculator;

import com.lits.calculator.AddOperation;
import com.lits.calculator.Calculator;
import com.lits.calculator.Operation;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.List;

public class CalculatorAddOperationTest {

    private Calculator calculator = new Calculator();

    @Test(expectedExceptions = { AssertionError.class })
    public void testExpectedException() {

        calculator.reset();

        calculator.setValue(BigDecimal.valueOf(20.0));

        Assert.assertEquals(calculator.getCurrentAmount(), BigDecimal.ZERO);
    }

    @Test(groups = {"calculator", "simple-operations", "add"},
            description = "Verify that add operation is being performed correctly")
    public void testAdd () {

        // GIVEN
        BigDecimal a = BigDecimal.valueOf(0.1);
        BigDecimal b = BigDecimal.valueOf(0.2);

        // WHEN
        calculator.setValue(a);

        // THEN
        Assert.assertEquals(calculator.getCurrentAmount(), a);

        // WHEN
        calculator.add(b);
        calculator.add(b);
        calculator.add(b);

        List<Operation> operationsHistory = calculator.getOperationsHistory();

        for (Operation operation : operationsHistory) {
            Assert.assertTrue(operation instanceof AddOperation);
        }

        // THEN
        Assert.assertEquals(calculator.getCurrentAmount(), BigDecimal.valueOf(0.3));
    }
}
