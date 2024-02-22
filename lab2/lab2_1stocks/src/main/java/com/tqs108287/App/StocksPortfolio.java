package com.tqs108287.App;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class StocksPortfolio {

    private IStockmarketService stockmarket;
    private List<Stock> stocks;

    public StocksPortfolio(IStockmarketService stockmarket) {
        this.stockmarket = stockmarket;
        this.stocks = new ArrayList<>();
    }

    public void addStock(Stock stock){
        stocks.add(stock);
    }

    public double totalValue(){
        return stocks.stream().mapToDouble(stock -> stockmarket.lookUpPrice(stock.getLabel()) * stock.getQuantity()).sum();
    }
}
