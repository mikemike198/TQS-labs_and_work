package lab02;

import java.util.ArrayList;
import java.util.List;

public class StocksPortfolio {
    String name;
    ArrayList<Stock> stocks;
    IStockMarket market;

    public StocksPortfolio(IStockMarket market, String name) {
        this.market = market;
        stocks = new ArrayList<Stock>();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(ArrayList<Stock> stocks) {
        this.stocks = stocks;
    }

    public IStockMarket getMarket() {
        return market;
    }

    public void setMarket(IStockMarket market) {
        this.market = market;
    }

    public double getTotalValue() {
        double total = 0;
        for (Stock stock : stocks ) {
            total = total + (market.getPrice(stock.getName()) * stock.getQuantity());
        }
        return total;
    }

    public void addStock(Stock stock) {
        stocks.add(stock);
    }
}
