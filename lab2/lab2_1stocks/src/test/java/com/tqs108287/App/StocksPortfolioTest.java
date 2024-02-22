package com.tqs108287.App;


import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

// Expectations por em excessso dá um erro. Podemos alivir isso com o que está na seguinte linha:
// @MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
public class StocksPortfolioTest {

    // 1. Prepare a mock to substitute the remote stockmarket service (@Mock annotation)
    @Mock
    private IStockmarketService market;

    // 2. Create an instance of the subject under test (SuT) and use the mock to set the (remote) service instance.
    @InjectMocks
    StocksPortfolio portfolio;

    @Test
    @DisplayName("Calculation of Total Value according to existing stocks and current price. (using annotations)")
    void calcTotalValueAnnotations() {
        // 3. Load the mock with the proper expectations (when...thenReturn)
        when(market.lookUpPrice("EBAY")).thenReturn(3d);
        when(market.lookUpPrice("MSFT")).thenReturn(5d);

        // 4. Execute the test (use the service in the SuT)
        portfolio.addStock(new Stock("EBAY", 2));
        portfolio.addStock(new Stock("MSFT", 5));
        double result = portfolio.totalValue();

        // 5. Verify the result (assert) and the use of the mock (verify)
        assertEquals(result, 31.0);     // JUnit
        assertThat(result, equalTo(result));   // Hamcrest
        verify(market, times(2)).lookUpPrice(anyString());
        // verificar que o lookup price foi chamado exatamente duas vezes (neste caso quando executa totalValue)
    }

    // less preferred
    @Test
    @DisplayName("Calculation of Total Value according to existing stocks and current price. (without annotations)")
    void calcTotalValue() {
        // 1. Prepare a mock to substitute the remote stockmarket service (@Mock annotation)
        IStockmarketService market = mock(IStockmarketService.class);

        // 2. Create an instance of the subject under test (SuT) and use the mock to set the (remote) service instance.
        StocksPortfolio portfolio = new StocksPortfolio(market);

        // 3. Load the mock with the proper expectations (when...thenReturn)
        when(market.lookUpPrice("EBAY")).thenReturn(3d);
        when(market.lookUpPrice("MSFT")).thenReturn(5d);

        // 4. Execute the test (use the service in the SuT)
        portfolio.addStock(new Stock("EBAY", 2));
        portfolio.addStock(new Stock("MSFT", 5));
        double result = portfolio.totalValue();

        // 5. Verify the result (assert) and the use of the mock (verify)
        assertEquals(result, 31.0);     // JUnit
        assertThat(result, equalTo(result));   // Hamcrest
        verify(market, times(2)).lookUpPrice(anyString());
    }

}