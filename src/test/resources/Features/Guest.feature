Feature: Guest Management
  As a hotel administrator
  I want to manage guest information
  So that I can keep guest records up to date

  Scenario: Create a new guest
  Given the system does not have a guest with identification "1003250818"
  When I create a guest with the following details:
    | Identification  | Name                    | Email              |
    | 1003250818      | Angie Laireth  | angielaireth4@gmail.com |
  Then the guest should be created successfully
  And the guest identification should be "1003250818"

