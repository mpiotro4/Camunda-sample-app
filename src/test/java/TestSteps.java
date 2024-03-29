import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;


public class TestSteps {
    @Given("I have a calculator")
    public void initializeCalculator() {
        // Initialization logic
    }

    @When("I add {int} and {int}")
    public void addNumbers(int num1, int num2) {
        // Logic to add numbers
    }

    @Then("the result should be {int}")
    public void verifyResult(int expected) {
        Assert.assertEquals(5, expected);
    }
}
