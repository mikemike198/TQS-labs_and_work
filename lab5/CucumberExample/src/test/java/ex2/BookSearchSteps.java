package ex2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class BookSearchSteps {

    Library library = new Library();
    List<Book> searchResult;

    @ParameterType("([0-3][0-9])/([0-1][0-9])/([0-9]{4})")
    public Date date(String day, String month, String year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
        calendar.set(Calendar.MONTH, Integer.parseInt(month));
        calendar.set(Calendar.YEAR, Integer.parseInt(year));
        Date date = calendar.getTime();
        return date;
    }


    @Given("a book with the title {string}, written by {string}, published in {date}")
    public void setupABook(String bookName, String authorName, Date date) {
       library.addBook(new Book(bookName, authorName, date));                      
    }

    @Given("another book with the title {string}, written by {string}, published in {date}")
    public void setupAnotherBook(String bookName, String authorName, Date date) {
        library.addBook(new Book(bookName, authorName, date));
    }


    @When("the customer searches for books published between {int} and {int}")
    public void bookSearch(int year1, int year2) {
         Calendar calendar = Calendar.getInstance();
         calendar.clear();
         calendar.set(Calendar.YEAR, year1);
         Date from = calendar.getTime();
         calendar.clear();
         calendar.set(Calendar.YEAR, year2);
         Date to = calendar.getTime();
         searchResult = library.findBook(from, to);
    }

    @Then("{int} books should have been found")
    public void length(int size) {
        assertEquals(size, searchResult.size() +1);
    }

    @Then("Book {int} should have the title {string}")
    public void checkIfTitleMatches(int index, String bookName) {
          assertEquals(searchResult.get(index).getTitle(), bookName);
    }
}
