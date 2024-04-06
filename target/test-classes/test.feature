Feature: Test Camunda Process with Cucumber

  Scenario: Calculate the sum of two numbers
    Given I have a calculator
    When I add 2 and 3
    Then the result should be 5

  Scenario: High
    When the task_process.bpmn is started
    Then process is awaiting at create new task
    When task is completed with high
    Then process has passed assign-high
    And process has passed task-created
    And process has ended

  Scenario: Low
    When the task_process.bpmn is started
    Then process is awaiting at create new task
    When task is completed with Low
    Then process has passed assign-low
    And process has passed task-created
    And process has ended