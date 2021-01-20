@e2e
Feature: Basic Navigation

    # re-login at each scenario
    Background:
        Given I go on the Gaia login page
        And I login with user 'admin' and password 'admin123'

    Scenario: View Dashboard
        When I arrive on the Dashboard page
        Then Percy takes a snapshot named 'Dashboard'

    Scenario: View modules
        When I go on the modules page
        Then Percy takes a snapshot named 'Modules'

    Scenario: View module details
        When I go on the module 'e01f9925-a559-45a2-8a55-f93dc434c676' page
        Then Percy takes a snapshot named 'Module Details'

    Scenario: View module description
        When I go on the module 'e01f9925-a559-45a2-8a55-f93dc434c676' description page
        Then Module description readme contains 'A sample terraform module for running a mongodb database inside a docker container'
        Then Percy takes a snapshot named 'Module Description'

    Scenario: View stack details
        When I go on the stack 'de28a01f-257a-448d-8e1b-00e4e3a41db2' page
        Then Percy takes a snapshot named 'Stack Details'

    Scenario: View job details
        When I go on the job '5e856dc7-6bed-465f-abf1-02980206ab2a' for stack 'de28a01f-257a-448d-8e1b-00e4e3a41db2' page
        Then Percy takes a snapshot named 'Job Details'

    Scenario: View users list
        When I go on the users page
        Then I can see 5 users
        Then Percy takes a snapshot named 'Users'
