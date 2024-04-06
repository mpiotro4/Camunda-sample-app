import com.mpio.workflow.delegate.AssignHighPriority;
import com.mpio.workflow.delegate.AssignLowPriority;
import com.mpio.workflow.repository.TaskRepository;
import com.mpio.workflow.service.ToDoTaskService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.DeploymentBuilder;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.camunda.community.process_test_coverage.junit4.platform7.rules.TestCoverageProcessEngineRuleBuilder;
import org.junit.*;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.*;
@org.camunda.bpm.engine.test.Deployment(resources = "task_process.bpmn")
public class CamundaSteps {
    @Rule
    @ClassRule
    public static final ProcessEngineRule processEngineRule = TestCoverageProcessEngineRuleBuilder.create().build();

    private ProcessInstance instance;

    @Before
    public void setUp() {
        TaskRepository taskRepositoryMock = Mockito.mock(TaskRepository.class);
        ToDoTaskService toDoTaskServiceMock = new ToDoTaskService(taskRepositoryMock);
        Mocks.register("assignHighPriority", new AssignHighPriority(toDoTaskServiceMock));
        Mocks.register("assignLowPriority", new AssignLowPriority(toDoTaskServiceMock));
    }
    @Test
    public void low() {
        final ProcessInstance instance = runtimeService().startProcessInstanceByKey("camunda_task_process");

        assertThat(instance).isWaitingAt("create-new-task");

        Map<String, Object> variables = new HashMap<>();
        variables.put("priority", "low");
        variables.put("description", "xD");
        variables.put("taskName", "xD");
        complete(task(), variables);

        assertThat(instance)
                .hasPassed("assign-low");

        assertThat(instance)
                .hasPassed("task-created")
                .isEnded();
    }

    @Test
    public void high() {
        final ProcessInstance instance = runtimeService().startProcessInstanceByKey("camunda_task_process");

        assertThat(instance).isWaitingAt("create-new-task");

        Map<String, Object> variables = new HashMap<>();
        variables.put("priority", "high");
        variables.put("description", "xD");
        variables.put("taskName", "xD");
        complete(task(), variables);

        assertThat(instance)
                .hasPassed("assign-high");

        assertThat(instance)
                .hasPassed("task-created")
                .isEnded();
    }

    @When("the task_process.bpmn is started")
    public void startProcess() {
        instance = runtimeService().startProcessInstanceByKey("camunda_task_process");
    }

    @Then("process is awaiting at create new task")
    public void processIsAwaitingAtCreateNewTask() {
        assertThat(instance).isWaitingAt("create-new-task");
    }

    @When("task is completed with high")
    public void completeTaskHigh() {
        Map<String, Object> variables = new HashMap<>();
        variables.put("priority", "high");
        variables.put("description", "xD");
        variables.put("taskName", "xD");
        complete(task(), variables);
    }

    @When("task is completed with Low")
    public void completeTaskLow() {
        Map<String, Object> variables = new HashMap<>();
        variables.put("priority", "low");
        variables.put("description", "xD");
        variables.put("taskName", "xD");
        complete(task(), variables);
    }

    @Then("process has passed assign-low")
    public void processHasPassedAssignLow() {
        assertThat(instance)
                .hasPassed("assign-low");
    }

    @Then("process has passed assign-high")
    public void processHasPassedAssignHigh() {
        assertThat(instance)
                .hasPassed("assign-high");
    }

    @And("process has passed task-created")
    public void processHasPassedTaskCreated() {
        assertThat(instance)
                .hasPassed("task-created")
                .isEnded();
    }

    @And("process has ended")
    public void processHasEnded() {
        assertThat(instance)
                .isEnded();
    }
}
