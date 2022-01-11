Feature: This is a module feature

  Scenario: This is "A" scenario
    Given A transaction that is not stored in our system
    When I check the status from any channel
    Then The system returns the status 'INVALID'

        #  Example payload:
        #    {
        #    "reference":"XXXXXX",
        #    "channel":"CLIENT"
        #    }
        #    Example response:
        #    {
        #    "reference":"XXXXXX",
        #    "status":"INVALID"
        #    }

  Scenario: This is "B" scenario
    Given A transaction that is stored in our system and the transaction date is before today
    When I check the status of reference from 'CLIENT' channel
    Then The system returns the status 'SETTLED'
    And the amount subtracting the fee

        ##  Example payload:
        ##    {
        ##    "reference":"12345A",
        ##    "channel":"CLIENT"
        ##    }
        ##    Example response:
        ##    {
        ##    "reference":"12345A",
        ##    "status":"SETTLED",
        #     "amount":190.20
        #    }

  Scenario: This is "B" with ATM scenario
    Given A transaction that is stored in our system and the transaction date is before today
    When I check the status of reference from 'ATM' channel
    Then The system returns the status 'SETTLED'
    And the amount subtracting the fee

        ##  Example payload:
        ##    {
        ##    "reference":"12345A",
        ##    "channel":"CLIENT"
        ##    }
        ##    Example response:
        ##    {
        ##    "reference":"12345A",
        ##    "status":"SETTLED",
        #     "amount":190.20
        #    }

  Scenario: This is "C" scenario
    Given A transaction that is stored in our system and the transaction date is before today
    When I check the status of reference from 'INTERNAL' channel
    Then The system returns the status 'SETTLED'
    And the amount
    And the fee

        #  Example payload:
        #    {
        #    "reference":"12345A",
        #    "channel":"INTERNAL"
        #    }
        #    Example response:
        #    {
        #    "reference":"12345A",
        #    "status":"SETTLED",
        #    "amount":193.38,
        #    "fee":3.18
        #    }

  Scenario: This is "D" scenario
    Given A transaction that is stored in our system and the transaction date is equals to today
    When I check the status of reference from 'ATM' channel
    Then The system returns the status 'PENDING'
    And the amount subtracting the fee

        #  Example payload:
        #    {
        #    "reference":"12345A",
        #    "channel":"ATM"
        #    }
        #    Example response:
        #    {
        #    "reference":"12345A",
        #    "status":"PENDING",
        #    "amount":190.20
        #    }

  Scenario: This is "D" with CLIENT scenario
    Given A transaction that is stored in our system and the transaction date is equals to today
    When I check the status of reference from 'CLIENT' channel
    Then The system returns the status 'PENDING'
    And the amount subtracting the fee

        #  Example payload:
        #    {
        #    "reference":"12345A",
        #    "channel":"ATM"
        #    }
        #    Example response:
        #    {
        #    "reference":"12345A",
        #    "status":"PENDING",
        #    "amount":190.20
        #    }

  Scenario: This is "E" scenario
    Given A transaction that is stored in our system and the transaction date is equals to today
    When I check the status of reference from 'INTERNAL' channel
    Then The system returns the status 'PENDING'
    And the amount
    And the fee

        #  Example payload:
        #    {
        #    "reference":"12345A",
        #    "channel":"INTERNAL"
        #    }
        #    Example response:
        #    {
        #    "reference":"12345A",
        #    "status":"PENDING",
        #    "amount":193.38,
        #    "fee":3.18
        #    }

  Scenario: This is "F" scenario
    Given A transaction that is stored in our system and the transaction date is greater than today
    When I check the status of reference from 'CLIENT' channel
    Then The system returns the status 'FUTURE'
    And the amount subtracting the fee

        #  Example payload:
        #    {
        #    "reference":"12345A",
        #    "channel":"CLIENT"
        #    }
        #    Example response:
        #    {
        #    "reference":"12345A",
        #    "status":"FUTURE",
        #    "amount":190.20
        #    }

  Scenario: This is "G" scenario
    Given A transaction that is stored in our system and the transaction date is greater than today
    When I check the status of reference from 'ATM' channel
    Then The system returns the status 'PENDING'
    And the amount subtracting the fee

        #  Example payload:
        #    {
        #    "reference":"12345A",
        #    "channel":"ATM"
        #    }
        #    Example response:
        #    {
        #    "reference":"12345A",
        #    "status":"PENDING",
        #    "amount":190.20
        #    }

  Scenario: This is "H" scenario
    Given A transaction that is stored in our system and the transaction date is greater than today
    When I check the status of reference from 'INTERNAL' channel
    Then The system returns the status 'FUTURE'
    And the amount
    And the fee

        #  Example payload:
        #    {
        #    "reference":"12345A",
        #    "channel":"INTERNAL"
        #    }
        #    Example response:
        #    {
        #    "reference":"12345A",
        #    "status":"FUTURE",
        #    "amount":193.38,
        #    "fee":3.18
        #    }
