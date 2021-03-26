package lab02;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StocksPortfolioTest {

    @Test
    void test_getTotalValue() {

        // 1. Prepare a mock to substitute the remote service (@Mock annotation)
        IStockMarket market = mock(IStockMarket.class);
        // 2. Create an instance of the subject under test (SuT) and use the mock to set the (remote) service instance.
        StocksPortfolio portfolio = new StocksPortfolio(market, "portfolio");
        // 3. Load the mock with the proper expectations (when...thenReturn)
        when(market.getPrice("MCS")).thenReturn(10.0);
        when(market.getPrice("APPLE")).thenReturn(5.0);

        // 4. Execute the test (use the service in the SuT)
        portfolio.addStock(new Stock("MCS", 3));
        portfolio.addStock(new Stock("APPLE", 2));
        assertEquals(40, portfolio.getTotalValue());
        assertThat(portfolio.getTotalValue(), equalTo(40));
        // 5. Verify the result (assert) and the use of the mock (verify)
        verify(market, times(2)).getPrice(anyString());



    }

    @Test
    void test_getTotalThrowsError() {

        IStockMarket market = mock(IStockMarket.class);
        StocksPortfolio portfolio = new StocksPortfolio(market, "portfolio");

        when(market.getPrice(anyString())).thenThrow(IllegalArgumentException.class);

        portfolio.addStock(new Stock("Apple", 4));
        portfolio.addStock(new Stock("MCS", 2));

        assertThrows(IllegalArgumentException.class, () -> portfolio.getTotalValue());

        verify(market, times(1)).getPrice(anyString());

    }
}