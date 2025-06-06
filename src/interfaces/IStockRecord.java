package interfaces;

import exceptions.StockUnavailableException;

/**
 * This is the interface for a StockRecord for a particular shop product.
 *
 */
public interface IStockRecord {

    /**
     * Returns the shop product associated with this IStockRecord.
     * @return the product related to this stock record
     */
    IProduct getProduct();


    /**
     * Returns the stock count for this shop product.
     * @return the stock level for the product
     */
    int getStockCount();


    /**
     * Returns the number of times this shop product has been bought.
     * @return the number of sales for the product
     */
    int getNumberOfSales();


    /**
     * Adds one item of stock for this shop product.
     */
    void addStock();


    /**
     * Processes the purchase of one shop product from the stock.
     * @throws StockUnavailableException when the product is not currently in stock
     */
    void buyProduct() throws StockUnavailableException;


}
