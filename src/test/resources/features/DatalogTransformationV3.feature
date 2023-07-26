Feature: Transform datalogs from version 2 to version 3

  Scenario: Transform existing records
    Given the following old datalogs exist:
      | sessionId                            | epochMilliseconds | longitude          | latitude            | altitude | intakeAirTemperature | boostPressure | coolantTemperature | engineRpm | speed | throttlePosition | airFuelRatio |
      | c61cc339-f93d-45a4-aa2b-923f0482b97f | 1663524947968     | -86.14170333333335 | 42.406800000000004  | 188.4    | 100                  | 16.5          | 95                 | 4500      | 74    | 5.6              | 14.7         |
      | 8e4eef4d-82b5-4a48-9590-796f1f4278a4 | 1663524948962     | 86.14162999999999  | -42.406816666666664 | 188.0    | 130                  | 9.0           | 98                 | 1500      | 79    | 7.0              |              |
    When the datalogs are transformed
    Then the following new datalogs will exist:
      | sessionId                            | epochMilliseconds | longitude          | latitude            | altitude | intakeAirTemperature | boostPressure | coolantTemperature | engineRpm | speed | throttlePosition | airFuelRatio | trackName  | trackLatitude | trackLongitude | firstName | lastName | email         |
      | c61cc339-f93d-45a4-aa2b-923f0482b97f | 1663524947968     | -86.14170333333335 | 42.406800000000004  | 188.4    | 100                  | 16.5          | 95                 | 4500      | 74    | 5.6              | 14.7         | Test Track | 42.4086       | -86.1374       | test      | tester   | test@test.com |
      | 8e4eef4d-82b5-4a48-9590-796f1f4278a4 | 1663524948962     | 86.14162999999999  | -42.406816666666664 | 188.0    | 130                  | 9.0           | 98                 | 1500      | 79    | 7.0              |              | Test Track | 42.4086       | -86.1374       | test      | tester   | test@test.com |
    And the application will log the following messages:
      | level | message                         |
      | INFO  | Beginning to transform records  |
      | INFO  | Record transformation completed |