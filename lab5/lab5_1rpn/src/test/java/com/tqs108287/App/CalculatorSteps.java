package com.tqs108287.App;

import static java.lang.invoke.MethodHandles.lookup;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CalculatorSteps {

    static final Logger log = getLogger(lookup().lookupClass());

    private Calculator calc;

    @Given("^a calculator I just turned on$")
    public void setup() {
        calc = new Calculator();
    }

    @When("I add {double} and {double}")
    public void add(double arg1, double arg2) {
        log.debug("Adding {} and {}", arg1, arg2);
        calc.push(arg1);
        calc.push(arg2);
        calc.push("+");
    }

    @When("I substract {double} to {double}")
    public void substract(double arg1, double arg2) {
        log.debug("Substracting {} and {}", arg1, arg2);
        calc.push(arg1);
        calc.push(arg2);
        calc.push("-");
    }


    @When("I multiply {double} and {double}")
    public void iMultiplyAnd(double arg0, double arg1) {
        log.debug("Multiplying {} and {}", arg0, arg1);
        calc.push(arg0);
        calc.push(arg1);
        calc.push("*");
    }

    @When("I divide {double} by {double}")
    public void iDivideBy(double arg0, double arg1) {
        log.debug("Dividing {} by {}", arg0, arg1);
        calc.push(arg0);
        calc.push(arg1);
        calc.push("/");
    }

    @Then("the result is {double}")
    public void the_result_is(double expected) {
        Number value = calc.value();
        log.debug("Result: {} (expected {})", value, expected);
        assertEquals(expected, value);
    }

    @Then("the result is Infinity")
    public void the_result_is_infinity() {
        double value = calc.value().doubleValue();
        log.debug("Result: {} (is infinite)", value);
        assertTrue(Double.isInfinite(value));
    }
}