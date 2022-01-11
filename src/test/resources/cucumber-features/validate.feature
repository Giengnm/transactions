Feature: This is a validate feature for functionality

  Scenario: client makes call to Root URI
    When the client calls Root URI
    Then the client receives response root

  Scenario: client makes call to GET /health and success
    When the client calls GET '/actuator/health'
    Then the client receives response status code of 200

  Scenario: client makes call to GET /fail and return 4xx error
    When the client calls GET '/actuator/fail'
    Then the client receives response status code of 404

  Scenario: client makes call to GET /api/v1/transaction with params and return 4xx error
    When the client calls GET '/api/v1/transaction' with '?account-iban=account-iban' and '&order=DESC'
    Then the client receives response status code of 404

  Scenario: client makes call to GET /api/v1/transaction without params and return 4xx error
    When the client calls GET '/api/v1/transaction'
    Then the client receives response status code of 400

  Scenario: client makes call to POST /api/v1/transaction without body and return 200
    When the client calls POST '/api/v1/transaction' with body
    Then the client receives response status code of 200

  Scenario: client makes call to GET /api/v1/transaction with params and return 200
    When the client calls GET '/api/v1/transaction' with '?account-iban=ES9820385778983000760236' and '&order=DESC'
    Then the client receives response status code of 200

  Scenario: client makes call to GET /api/v1/transaction with params and return 200
    When the client calls GET '/api/v1/transaction/status' with '?reference=A12345' and '&channel=INTERNAL'
    Then the client receives response status code of 200

  Scenario: client makes call to GET /api/v1/transaction with reference XXXXXX and return 200
    When the client calls GET '/api/v1/transaction/status' with '?reference=XXXXXX' and '&channel=INTERNAL'
    Then the client receives response status code of 200

  Scenario: client makes call to POST /api/v1/transaction with string body and return 409
    When the client calls POST '/api/v1/transaction' with string body '{"account_iban": "ES9820385778983000760236"}'
    Then the client receives response status code of 409

  Scenario: client makes call to POST /api/v1/transaction with string body and return 409
    When the client calls POST '/api/v1/transaction' with string body '{"amount": "192.32"}'
    Then the client receives response status code of 409

  Scenario: client makes call to POST /api/v1/transaction with string body and return 200
    When the client calls POST '/api/v1/transaction' with string body '{"account_iban": "ES9820385778983000760236", "amount": "192.32"}'
    Then the client receives response status code of 200

  Scenario: client makes call to GET /api/v1/transaction with reference A12345 and return 200
    When the client calls GET '/api/v1/transaction/status' with '?reference=A12345' and '&channel=ATM'
    Then the client receives response status code of 200