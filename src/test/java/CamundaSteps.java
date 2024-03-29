import com.mpio.workflow.delegate.AssignHighPriority;
import com.mpio.workflow.delegate.AssignLowPriority;
import com.mpio.workflow.service.ToDoTaskService;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.DeploymentBuilder;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.junit.Assert;
import java.util.List;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.*;
public class CamundaSteps {
    @Before
    public void setUp() {
        Mocks.register("assignHighPriority", new AssignHighPriority());
        Mocks.register("assignLowPriority", new AssignLowPriority());
    }
    private ProcessEngine processEngine;

    private Deployment deployment;
    private ProcessInstance instance;

    private static final String PROCESS_KEY = "camunda_task_process";
    private String deploymentId;

    @Given("a Camunda process engine is set up")
    public void setupProcessEngine() {
        processEngine = ProcessEngines.getDefaultProcessEngine();
    }

    @When("the process is deployed")
    public void deployProcess() {
        DeploymentBuilder deploymentBuilder = processEngine.getRepositoryService()
                .createDeployment()
                .addClasspathResource("task_process.bpmn");

        deployment = deploymentBuilder.deploy();
        deploymentId = deployment.getId();
    }

    @When("process instance is started")
    public void instantiateProcess() {
        instance = processEngine.getRuntimeService()
                .startProcessInstanceByKey(PROCESS_KEY);
    }

    @Then("the process should be deployed")
    public void checkIfDeployed() {
        Assert.assertNotNull("Process should be deployed", deployment);
    }

    @Then("the process should be available")
    public void checkProcessAvailability() {
//        ProcessDefinition processDefinition = processEngine.getRepositoryService()
//                .createProcessDefinitionQuery()
//                .processDefinitionKey(PROCESS_KEY)
//                .singleResult();
//
//        Assert.assertNotNull("Process should be available", processDefinition);
    }

    @Given("User wants to create a new task")
    public void userWantsToCreateNewTask(){
        setupProcessEngine();
        deployProcess();
        checkIfDeployed();
        instantiateProcess();
        checkProcessAvailability();

        assertThat(instance)
                .isActive()
                .hasPassed("StartEvent_1")
                .isWaitingAtExactly("say-hello")
                .task().isNotAssigned();
    }

    @When("Create new task")
    public void createNewTask(){
        complete(task(), withVariables(
                "taskName", "",
                "description", "",
                "priority", "high"
        ));
    }
    @Then("Priority of the task priority = ?")
    public void whatIsPriorityOfTheTask()  {

    }

    @When("^Assign to high priority queue priority = (.*)$")
    public void assignToHighPriorityQueue(String priority) {

    }

    @When("^Assign to low priority queue priority = (.*)$")
    public void assignToLowPriorityQueue(String priority) {

    }

    @Then("Priority assigned")
    public void priorityAssigned(){

    }

    @And("Task created")
    public void taskCreated() {

    }
}
