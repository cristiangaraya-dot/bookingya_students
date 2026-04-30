Feature: Room Management
  As a hotel administrator
  I want to manage room information
  So that I can keep room records up to date

  Scenario: Create a new room
    Given the system does not have a room with code "02"
    When I create a room with the following details:
      | Code | Name                | City      | MaxGuest | Price | Available |
      | 02   | Habitación Sencilla | Aguachica | 1        | 50000 | true      |
    Then the room should be created successfully
    And the room code should be "02"

