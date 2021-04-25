Feature: Book Search

  Scenario: Search books by publication year
    Given a book with the title 'One good book', written by 'Anonymous', published in 14/03/2013
      And another book with the title 'Some other book', written by 'Tim Tomson', published in 23/08/2014
      And another book with the title 'How to cook a dino', written by 'Fred Flintstone', published in 01/01/2012
    When the customer searches for books published between 2013 and 2014
    Then 3 books should have been found
      And Book 1 should have the title 'Some other book'
      And Book 2 should have the title 'How to cook a dino'
