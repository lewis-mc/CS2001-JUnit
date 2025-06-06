package impl;

import interfaces.IFactory;
import interfaces.IProduct;
import interfaces.IShop;
import interfaces.IStockRecord;


/**
 * This class implements a singleton factory.
 *
 */
public final class Factory implements IFactory {

    private static IFactory factoryInstance = null;
    private IProduct product;
    private IStockRecord stockRecord;
    private IShop shop;

    private Factory() {

    }

    /**
     * Method which returns an instance of the singleton Factory class.
     * Example use: From other classes, you can call Factory.getInstance().makeShop();
     * for example to create an instance of IShop, and similarly for the other "make" methods.
     * @return the instance of the Factory
     */
    public static IFactory getInstance() {
        if (factoryInstance == null) {
            factoryInstance = new Factory();
        }
        return factoryInstance;
    }

    /**
     * Method that creates a product object that stores a barcode and a description
     * @return the product object
     * @param barCode
     * @param description
     */
    @Override
    public IProduct makeProduct(String barCode, String description) {
        // TODO Auto-generated method stub
        product = new Product(barCode, description);
        return product;
    }

    /**
     * Method that creates a stock record object that stores the product object for that record
     * @return the stock record object
     * @param product
     */
    @Override
    public IStockRecord makeStockRecord(IProduct product) {
        // TODO Auto-generated method stub
        stockRecord = new StockRecord(product);
        return stockRecord;
    }

    /**
     * Method that creates a blank shop object
     * @return the shop object
     */
    @Override
    public IShop makeShop() {
        // TODO Auto-generated method stub
        shop = new Shop();
        return shop;
    }

}
