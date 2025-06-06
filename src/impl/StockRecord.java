package impl;

import exceptions.StockUnavailableException;
import interfaces.IProduct;
import interfaces.IStockRecord;

/**
 * This class represents a record held by the shop for a particular product.
 *
 */
public class StockRecord implements IStockRecord {

    private IProduct product;
    private int stockCount;
    private int numberOfSales;

    /**
     * Stock record constructor that instantiates a object for a product
     * Instantiates the counter variables to zero
     * @param product the product to make the stock record for
     */
    public StockRecord(IProduct product) {
        this.product = product;
        this.stockCount = 0;
        this.numberOfSales = 0;
    }
    
    /**
     * @return the product of the stock record
     */
    @Override
    public IProduct getProduct() {
        // TODO Auto-generated method stub
        return this.product;
    }

    /**
     * @return the stock count of the product
     */
    @Override
    public int getStockCount() {
        // TODO Auto-generated method stub
        return this.stockCount;
    }

    /**
     * @return the number of sales of the product
     */
    @Override
    public int getNumberOfSales() {
        // TODO Auto-generated method stub
        return this.numberOfSales;
    }

    /**
     * Increments the amount of stock for this product
     */
    @Override
    public void addStock() {
        // TODO Auto-generated method stub
        this.stockCount++;
    }

    /**
     * This method is for the purchase of a singular amount of this product
     * Buying a product means the amount of stock goes down by one, and the number of sales goes up by one
     * If there is no stock of this product left the exception is thrown and handled
     * @throws StockUnavailableException if the product trying to be bought has no stock
     */
    @Override
    public void buyProduct() throws StockUnavailableException {
        // TODO Auto-generated method stub

        try {
            if (this.stockCount < 1) {
                throw new StockUnavailableException();
            } else {
                this.stockCount--;
                this.numberOfSales++;
            }
        } catch(StockUnavailableException e) {
            System.err.println("There is no stock of this product");
        }
        
    }

}
