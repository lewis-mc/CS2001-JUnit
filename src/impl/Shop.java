package impl;

import java.util.ArrayList;

import exceptions.BarCodeAlreadyInUseException;
import exceptions.ProductNotRegisteredException;
import exceptions.StockUnavailableException;
import interfaces.IProduct;
import interfaces.IShop;
import interfaces.IStockRecord;

/**
 * This class represents a simple shop which can stock and sell products.
 *
 */
public class Shop implements IShop {

    private int numOfProducts;
    private ArrayList<IStockRecord> stockRecords;
    
    /**
     * Constructor for shop object that creates an empty shop with no products
     */
    public Shop() {
        this.numOfProducts = 0;
        this.stockRecords = new ArrayList<>();
    }


    /**
     * Method that registers a product to the shop
     * If the products barcode is unique it will be added to the shop and a stock record will be created for the product
     * If the barcode is already in use the exception will be thrown and handled
     * @param product the product to be registered to the shop
     * @throws BarCodeAlreadyInUseException if the bar code matches a product already registered
     */
    @Override
    public void registerProduct(IProduct product) throws BarCodeAlreadyInUseException {
        // TODO Auto-generated method stub

        boolean duplicate = false;
        try {
            if (product == null) {
                System.err.println("Null value for product");
                return;
            }
            for (int i = 0; i < stockRecords.size(); i++) {
                if (stockRecords.get(i).getProduct().getBarCode().equals(product.getBarCode())) { 
                    duplicate = true;
                }
            }
            if (duplicate) {
                throw new BarCodeAlreadyInUseException();
            } else {
                this.stockRecords.add(Factory.getInstance().makeStockRecord(product));
                this.numOfProducts++;
            }
        } catch (BarCodeAlreadyInUseException e) {
            System.err.println("Product with barcode already added");
        }
        
    }

    /**
     * This method removes a product from the shop
     * The product is checked to make sure it exists in the shop
     * If the product exists it is removed from the shop list of stock records
     * The exception will be thrown if an unknown product is attempted to be removed
     * @param product the product to be removed from the shops registered products
     * @throws ProductNotRegisteredException if the product to be unregistered isn't registered in the shop
     */
    @Override
    public void unregisterProduct(IProduct product) throws ProductNotRegisteredException {
        // TODO Auto-generated method stub
       
        boolean exists = false;
        try {
            if (product == null) {
                System.err.println("Null value for product");
                return;
            }
            for (int i = 0; i < this.stockRecords.size(); i++) {
                if (stockRecords.get(i).getProduct() == product) {
                    exists = true;
                }
            }
            if (!exists) {
                throw new ProductNotRegisteredException();
            } else {
                this.stockRecords.removeIf(n -> n.getProduct() == product);
                this.numOfProducts--;
            }
        } catch (ProductNotRegisteredException e) {
            System.err.println("Product trying to be removed isn't registered");
        }
        
    }

    /**
     * This method increments the anount of stock for the product with the barcode
     * If a product with a matching barcode is found the amount of stock of that product is incremented
     * If the barcode provided doesn't match the exception is thrown and handled
     * @param barCode a string of the barcode to have its stock incremented
     * @throws ProductNotRegisteredException if the product to add stock to isn't registered in the shop
     */
    @Override
    public void addStock(String barCode) throws ProductNotRegisteredException {
        // TODO Auto-generated method stub
        boolean exists = false;
        try {
            if(barCode == null) {
                System.err.println("Barcode has null value");
                return;
            }
            for (int i = 0; i < this.stockRecords.size(); i++) {
                if (barCode.equals(this.stockRecords.get(i).getProduct().getBarCode())) {
                    this.stockRecords.get(i).addStock();
                    exists = true;
                }
            }
            if (!exists) {
                throw new ProductNotRegisteredException();
            }
        } catch (ProductNotRegisteredException e) {
            System.err.print("Product doesn't exist");
        }
        

    }

    /**
     * This method allows a product to be bought
     * Makes sure the products exists, if it does exist then makes sure there is more than zero stock for that product
     * If the product does not exist the exception for a product that isn't registered is thrown amd handled
     * If the product does exist but there is less than 1 stock of that product then the unavailable stock exception is thrown and handled
     * The product has to exist and have at least one item of stock to be able to be bought
     * @param barCode the barcode of the product to be bought
     * @throws ProductNotRegisteredException if the product to be bought isn't registered in the shop
     * @throws StockUnavailableException if the product to be bought has no stock
     */
    @Override
    public void buyProduct(String barCode) throws StockUnavailableException, ProductNotRegisteredException {
        // TODO Auto-generated method stub
        boolean exists = false;
        boolean available = false;

        try {
            if(barCode == null) {
                System.err.println("Barcode has null value");
                return;
            }
            for (int i = 0; i < this.stockRecords.size(); i++) {
                if (barCode.equals(this.stockRecords.get(i).getProduct().getBarCode())) {
                    exists = true;
                    if (this.stockRecords.get(i).getStockCount() < 1) {
                        throw new StockUnavailableException();
                    } else {
                        available = true;
                        this.stockRecords.get(i).buyProduct();
                    }
                }
            }

            if (!exists) {
                throw new ProductNotRegisteredException();
            }
            if (!available) {
                throw new StockUnavailableException(); 
            }
            
        } catch (ProductNotRegisteredException e) {
            System.err.println("Product does not exist");
        } catch (StockUnavailableException e) {
            System.err.println("There is no stock of this product");
        }
    }

    /**
     * Method for getting the total number of products
     * @return the total number of products in the shop
     */
    @Override
    public int getNumberOfProducts() {
        // TODO Auto-generated method stub
        return this.numOfProducts;
    }

    /**
     * Method for getting the total amount of stock in the shop
     * @return the total amount of stock of all products in the shop
     */
    @Override
    public int getTotalStockCount() {
        // TODO Auto-generated method stub
        int total = 0;
        for (int i = 0; i < stockRecords.size(); i++) {
            total += this.stockRecords.get(i).getStockCount();
        }
        return total;
    }

    /**
     * Method for getting the amount of stock for a particular product
     * Finds the product that corresponds to the barcode passed in
     * When the index becomes more than the amount of products this means the product with the barcode doesn't exist as this is the end position of the list
     * If the product doesn't exist in the shop the exception is thrown and handled
     * @param barCode the barcode of the product to get the stock count for
     * @return the stock count for the product
     * @throws ProductNotRegisteredException if the product to get the stock count for isn't a registered product
     */
    @Override
    public int getStockCount(String barCode) throws ProductNotRegisteredException {
        // TODO Auto-generated method stub
        boolean found = false;
        int index = 0;
        int stockCount = 0;
        try {
            if(barCode == null) {
                System.err.println("Barcode has null value");
                return stockCount;
            }
            if (this.stockRecords.size() < 1) {
                throw new ProductNotRegisteredException();
            }
            while (!found) {
                if (barCode.equals(this.stockRecords.get(index).getProduct().getBarCode())) {
                    stockCount = this.stockRecords.get(index).getStockCount();
                    found = true;
                } else {
                    index++;
                }

                if (index > this.numOfProducts) {
                    throw new ProductNotRegisteredException();
                }
                return stockCount;
            }
        } catch (ProductNotRegisteredException e) {
            System.err.println("Product does not exist to get stock count");
        }
        
        return stockCount;
    }

    /**
     * Method that gets the sales for a product.
     * Finds the product with the same barcode as the barcode passed in.
     * If the product isn't found the exception is passed.
     * @param barCode the barcode of the product to get sales for
     * @return the number of sales for the product
     * @throws ProductNotRegisteredException if the product to get number of sales of isn't a registered product
     */
    @Override
    public int getNumberOfSales(String barCode) throws ProductNotRegisteredException {
        // TODO Auto-generated method stub
        int numberOfSales = 0;
        boolean exists = false;
        try {
            if (barCode == null) {
                System.err.println("Barcode has null value");
                return numberOfSales;
            }
            for (int i = 0; i < this.stockRecords.size(); i++) {
                if (barCode.equals(this.stockRecords.get(i).getProduct().getBarCode())) {
                    numberOfSales = this.stockRecords.get(i).getNumberOfSales();
                    exists = true;
                }
            }
            if (!exists) {
                throw new ProductNotRegisteredException();
            }
            return numberOfSales;
        } catch (ProductNotRegisteredException e) {
            System.err.println("Product not found");
        }
        return numberOfSales;
    }

    /**
     * Method that gets the product that has sold the most
     * Makes sure there is products in the shop, if not the exception is thrown and handled
     * Sets the first index of the stock records list to the most popular
     * Traverses the list and compares the most popular to index of list, if index of list has more sales it is set to the most popular
     * @returns the product of the product with the most sales/returns null if the exception is thrown as there are no products in shop
     * @throws ProductNotRegisteredException if there are no products registered in the shop
     */
    @Override
    public IProduct getMostPopular() throws ProductNotRegisteredException {
        // TODO Auto-generated method stub

        try {
            if (this.stockRecords.size() < 1) {
                throw new ProductNotRegisteredException();
            }
            IStockRecord mostPopular = this.stockRecords.get(0);
            for (int i = 1; i < this.stockRecords.size(); i++) {
                int mostPopularSales = mostPopular.getNumberOfSales();
                int indexSales = this.stockRecords.get(i).getNumberOfSales();
                if (mostPopularSales < indexSales) {
                    mostPopular = this.stockRecords.get(i);
                }
            }
            return mostPopular.getProduct();

        } catch (ProductNotRegisteredException e) {
            System.err.println("No products in the shop");
        }
        return null;
    }


}
