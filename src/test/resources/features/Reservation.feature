Feature: Reservation Management
  As a booking administrator
  I want to manage reservations
  So that I can assign guests to rooms

  Scenario: Create a new reservation
    Given the system does not have a guest with identification "49659342"
    And the system does not have a room with code "03"
    And I create a guest with the following details:
      | Identification | Name           | Email                     |
      | 49659342       | Lucel Alvernia | lucelalvernia@booking.com |
    And I create a room with the following details:
      | Code | Name                | City   | MaxGuest | Price   | Available |
      | 03   | Habitación Familiar | Bogota | 10       | 2500000 | true      |
    When I create a reservation with the following details:
      | check_in         | check_out        | Identification | guests_count | notes                       | Code |
      | 2026-05-10 14:00 | 2026-05-15 10:00 | 49659342       | 2            | Reserva Habitación Familiar | 03   |
    Then the reservation should be created successfully