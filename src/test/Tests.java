package test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import exceptions.BarCodeAlreadyInUseException;
import exceptions.ProductNotRegisteredException;
import exceptions.StockUnavailableException;
import impl.Factory;
import interfaces.IProduct;
import interfaces.IShop;
import interfaces.IFactory;
import interfaces.IStockRecord;

/**
 * This is a JUnit test class for the Shop ADT.
 */
public class Tests {

    /**
     * This checks that the factory was able to call a sensible constructor to get a non-null instance of IProduct.
     */
    @Test
    public void shopProductCreationNonNull() {
        IProduct product = Factory.getInstance().makeProduct("1234567", "Laptop Computer");
        assertNotNull(product);
    }

    /**
     * This checks that the factory was able to call a sensible constructor to get a non-null instance of IShop.
     */
    @Test
    public void shopCreationNonNull() {
        IShop shop = Factory.getInstance().makeShop();
        assertNotNull(shop);
    }
    /**
     * This checks that the factory was able to call a sensible constructor to get a non-null instance of IStockRecord.
     */
    @Test
    public void shopStockRecordCreationNonNull() {
        IProduct product = Factory.getInstance().makeProduct("12345678", "Laptop Computer");
        IStockRecord stockRecord = Factory.getInstance().makeStockRecord(product);
        assertNotNull(stockRecord);
    }


    /**
     * This checks that the get bar code method returns the barcode value string correctly.
     */
    @Test
    public void shopProductCorrectBarcode() {
        IProduct product = Factory.getInstance().makeProduct("121212", "Mobile Phone");
        assertEquals(product.getBarCode(), "121212");
    }

    /**
     * This checks that the get description method returns the correct description value for the product.
     */
    @Test
    public void shopProductCorrectDescription() {
        IProduct product = Factory.getInstance().makeProduct("11221", "Washing Machine");
        assertEquals(product.getDescription(), "Washing Machine");
    }

    /**
     * This checks a product could be registered in the shop.
     */
    @Test
    public void addProduct() throws BarCodeAlreadyInUseException {
        IShop shop = Factory.getInstance().makeShop();
        IProduct product = Factory.getInstance().makeProduct("1231212", "Mobile Phone");

        shop.registerProduct(product);    
        assertEquals(1, shop.getNumberOfProducts());
    }

    /**
     * This checks that if that a product with the same bar code as another product is atempted to be added it won't be added.
     */
    @Test
    public void addMultipleProductsSameBarcode() throws BarCodeAlreadyInUseException {
        IShop shop = Factory.getInstance().makeShop();
        IProduct product = Factory.getInstance().makeProduct("123422", "Mobile Phone");
        IProduct product2 = Factory.getInstance().makeProduct("123422", "Laptop Computer");

        shop.registerProduct(product);
        shop.registerProduct(product2);

        assertEquals(1, shop.getNumberOfProducts());
    }
    
    /**
     * This checks that a product is able to be registered and then unregistered from the shop.
     * As there isn't an easy way to access the stock records array list I used the most popular method to show there is no products in the shop.
     */
    @Test
    public void unregisterProduct() throws BarCodeAlreadyInUseException, ProductNotRegisteredException {
        IShop shop = Factory.getInstance().makeShop();
        IProduct product = Factory.getInstance().makeProduct("14563345", "Mobile Phone");

        shop.registerProduct(product);   
        shop.unregisterProduct(product); 
        
        assertEquals(null, shop.getMostPopular());
        
    }

    /**
     * This checks that stock is able to be added for a product and the correct amount of stock is recorded.
     */
    @Test
     public void addStockToProduct() throws ProductNotRegisteredException, BarCodeAlreadyInUseException{
        IShop shop = Factory.getInstance().makeShop();
        IProduct product = Factory.getInstance().makeProduct("1627252", "Mobile Phone");

        shop.registerProduct(product);
        shop.addStock(product.getBarCode());
        shop.addStock(product.getBarCode());

        assertEquals(2, shop.getStockCount(product.getBarCode()));

     }

     /**
      * This checks that stock cannot be added to a product that isn't registered in the shop.
      */
    @Test
      public void addStockToUnregisteredProduct() throws ProductNotRegisteredException {
        IShop shop = Factory.getInstance().makeShop();
        IProduct product = Factory.getInstance().makeProduct("543453223", "Mobile Phone");

        shop.addStock(product.getBarCode());

        assertEquals(0, shop.getStockCount(product.getBarCode()));
      }

     /**
      * This checks that a product is able to be bought and the stock count is correct.
      */
     @Test
     public void buyAProductCorrectStockCount() throws ProductNotRegisteredException, BarCodeAlreadyInUseException, StockUnavailableException {
        IShop shop = Factory.getInstance().makeShop();
        IProduct product = Factory.getInstance().makeProduct("53726352", "Mobile Phone");

        shop.registerProduct(product);

        shop.addStock(product.getBarCode());
        shop.addStock(product.getBarCode());
        shop.addStock(product.getBarCode());

        shop.buyProduct(product.getBarCode());

        assertEquals(2, shop.getStockCount(product.getBarCode()));
        

      }

      /**
       * This checks that a product can be bought and the number of sales is correct
       */
      @Test
       public void buyAProductCorrectNumberOfSales() throws BarCodeAlreadyInUseException, ProductNotRegisteredException, StockUnavailableException{
        IShop shop = Factory.getInstance().makeShop();
        IProduct product = Factory.getInstance().makeProduct("253453234", "Mobile Phone");

        shop.registerProduct(product);

        shop.addStock(product.getBarCode());
        shop.addStock(product.getBarCode());
        shop.addStock(product.getBarCode());

        shop.buyProduct(product.getBarCode());
        shop.buyProduct(product.getBarCode());
        
        assertEquals(2, shop.getNumberOfSales(product.getBarCode()));

       }

      /**
       * This checks a product cannot be bought if it has no stock, and stock remians at zero.
       */
      @Test
      public void buyAProductNoStock() throws ProductNotRegisteredException, BarCodeAlreadyInUseException, StockUnavailableException {
        IShop shop = Factory.getInstance().makeShop();
        IProduct product = Factory.getInstance().makeProduct("78363493", "Mobile Phone");

        shop.registerProduct(product);
        shop.buyProduct(product.getBarCode());

        assertEquals(0, shop.getStockCount(product.getBarCode()));
      }

      /**
       * This checks a product cannot be bought if it hasn't been registered in the shop.
       */
      @Test
      public void buyAUnregisteredProduct() throws ProductNotRegisteredException, BarCodeAlreadyInUseException, StockUnavailableException {
        IShop shop = Factory.getInstance().makeShop();
        IProduct product = Factory.getInstance().makeProduct("832737262", "Mobile Phone");

        shop.buyProduct(product.getBarCode());

        assertEquals(0, shop.getNumberOfSales(product.getBarCode()));
        
      }

      /**
       * This checks the total number of products in the shop is correct.
       */
      @Test
      public void totalNumberOfProducts () throws BarCodeAlreadyInUseException {
        IShop shop = Factory.getInstance().makeShop();
        IProduct product1 = Factory.getInstance().makeProduct("743867292", "Mobile Phone");
        IProduct product2 = Factory.getInstance().makeProduct("789473864", "Laptop Computer");
        IProduct product3 = Factory.getInstance().makeProduct("467363728", "Washing Machine");
        IProduct product4 = Factory.getInstance().makeProduct("127673527", "Desktop Computer");

        shop.registerProduct(product1);
        shop.registerProduct(product2);
        shop.registerProduct(product3);
        shop.registerProduct(product4);

        assertEquals(4, shop.getNumberOfProducts());

      }

      /**
       * This checks the total number of products in the shop is correct after removing products.
       */   
      @Test
      public void totalNumberOfProductsAfterRemoving() throws BarCodeAlreadyInUseException, ProductNotRegisteredException {
        IShop shop = Factory.getInstance().makeShop();
        IProduct product1 = Factory.getInstance().makeProduct("7878356356", "Mobile Phone");
        IProduct product2 = Factory.getInstance().makeProduct("473736290", "Laptop Computer");
        IProduct product3 = Factory.getInstance().makeProduct("276352927", "Washing Machine");
        IProduct product4 = Factory.getInstance().makeProduct("9246483484", "Desktop Computer");

        shop.registerProduct(product1);
        shop.registerProduct(product2);
        shop.registerProduct(product3);
        shop.registerProduct(product4);

        shop.unregisterProduct(product1);
        shop.unregisterProduct(product3);

        assertEquals(2, shop.getNumberOfProducts());
      }

      /**
       * This checks the total stock count of the shop is correct.
       */
      @Test
      public void totalStockCount() throws BarCodeAlreadyInUseException, ProductNotRegisteredException {
        IShop shop = Factory.getInstance().makeShop();
        IProduct product1 = Factory.getInstance().makeProduct("35345215", "Mobile Phone");
        IProduct product2 = Factory.getInstance().makeProduct("764245234", "Laptop Computer");
        IProduct product3 = Factory.getInstance().makeProduct("785432577", "Washing Machine");
        IProduct product4 = Factory.getInstance().makeProduct("2847462926", "Desktop Computer");

        shop.registerProduct(product1);
        shop.registerProduct(product2);
        shop.registerProduct(product3);
        shop.registerProduct(product4);

        shop.addStock(product1.getBarCode());
        shop.addStock(product1.getBarCode());
        shop.addStock(product1.getBarCode());
        shop.addStock(product1.getBarCode());

        shop.addStock(product2.getBarCode());
        shop.addStock(product2.getBarCode());
        shop.addStock(product2.getBarCode());

        shop.addStock(product3.getBarCode());
        shop.addStock(product3.getBarCode());
        shop.addStock(product3.getBarCode());

        shop.addStock(product4.getBarCode());
        shop.addStock(product4.getBarCode());

        assertEquals(12, shop.getTotalStockCount());

      }

      /**
       * This checks the total stock count of shop is correct after products have been bought.
       */
      @Test
      public void totalStockCountAfterBuyingProducts () throws BarCodeAlreadyInUseException, ProductNotRegisteredException, StockUnavailableException {
        IShop shop = Factory.getInstance().makeShop();
        IProduct product1 = Factory.getInstance().makeProduct("733826292", "Mobile Phone");
        IProduct product2 = Factory.getInstance().makeProduct("983638626", "Laptop Computer");
        IProduct product3 = Factory.getInstance().makeProduct("273532926", "Washing Machine");
        IProduct product4 = Factory.getInstance().makeProduct("226373286", "Desktop Computer");

        shop.registerProduct(product1);
        shop.registerProduct(product2);
        shop.registerProduct(product3);
        shop.registerProduct(product4);

        shop.addStock(product1.getBarCode());
        shop.addStock(product1.getBarCode());
        shop.addStock(product1.getBarCode());
        shop.addStock(product1.getBarCode());

        shop.addStock(product2.getBarCode());
        shop.addStock(product2.getBarCode());
        shop.addStock(product2.getBarCode());

        shop.addStock(product3.getBarCode());
        shop.addStock(product3.getBarCode());
        shop.addStock(product3.getBarCode());

        shop.addStock(product4.getBarCode());
        shop.addStock(product4.getBarCode());

        shop.buyProduct(product1.getBarCode());
        shop.buyProduct(product1.getBarCode());

        shop.buyProduct(product3.getBarCode());

        assertEquals(9, shop.getTotalStockCount());

      }
      
      /**
       * This checks the stock count for a particular product is correct.
       */
      @Test
      public void stockCountParticularProduct() throws BarCodeAlreadyInUseException, ProductNotRegisteredException {
        IShop shop = Factory.getInstance().makeShop();
        IProduct product1 = Factory.getInstance().makeProduct("693640272", "Mobile Phone");
        IProduct product2 = Factory.getInstance().makeProduct("792626392", "Laptop Computer");

        shop.registerProduct(product1);
        shop.registerProduct(product2);
       
        shop.addStock(product1.getBarCode());
        shop.addStock(product1.getBarCode());
        shop.addStock(product1.getBarCode());

        shop.addStock(product2.getBarCode());
        shop.addStock(product2.getBarCode());

        assertEquals(3, shop.getStockCount(product1.getBarCode()));

      }

      /**
       * This checks the stock count for a particular product is correct after the product has been bought.
       */
      @Test
      public void stockCountParticularProductAfterBuying () throws ProductNotRegisteredException, StockUnavailableException, BarCodeAlreadyInUseException {
        IShop shop = Factory.getInstance().makeShop();
        IProduct product1 = Factory.getInstance().makeProduct("783643786", "Mobile Phone");
        IProduct product2 = Factory.getInstance().makeProduct("278327226", "Laptop Computer");

        shop.registerProduct(product1);
        shop.registerProduct(product2);
       
        shop.addStock(product1.getBarCode());
        shop.addStock(product1.getBarCode());
        shop.addStock(product1.getBarCode());
        shop.addStock(product1.getBarCode());

        shop.addStock(product2.getBarCode());
        shop.addStock(product2.getBarCode());

        shop.buyProduct(product1.getBarCode());
        shop.buyProduct(product1.getBarCode());


        assertEquals(2, shop.getStockCount(product1.getBarCode()));

      }

      /**
       * This checks if the products barcode given isn't in the shop then the stock count is returned as zero.
       */
      @Test
      public void unregisteredProductStockCount() throws BarCodeAlreadyInUseException, ProductNotRegisteredException {
        IShop shop = Factory.getInstance().makeShop();
        IProduct product1 = Factory.getInstance().makeProduct("3726373452", "Mobile Phone");
        IProduct product2 = Factory.getInstance().makeProduct("2674529278", "Laptop Computer");

        shop.registerProduct(product1);

        assertEquals(0, shop.getStockCount(product2.getBarCode()));
      }

      /**
       * This checks the number of sales for a partiuclar product is correct.
       */
      @Test
      public void numberOfSalesParticularProduct() throws BarCodeAlreadyInUseException, ProductNotRegisteredException, StockUnavailableException {
        IShop shop = Factory.getInstance().makeShop();
        IProduct product1 = Factory.getInstance().makeProduct("27437483", "Mobile Phone");
        IProduct product2 = Factory.getInstance().makeProduct("23374972", "Laptop Computer");

        shop.registerProduct(product1);
        shop.registerProduct(product2);

        shop.addStock(product1.getBarCode());
        shop.addStock(product1.getBarCode());
        shop.addStock(product1.getBarCode());
        shop.addStock(product1.getBarCode());
        shop.addStock(product1.getBarCode());

        shop.buyProduct(product1.getBarCode());
        shop.buyProduct(product1.getBarCode());
        shop.buyProduct(product1.getBarCode());

        assertEquals(3, shop.getNumberOfSales(product1.getBarCode()));


      }

      /**
       * This checks if the products barcode given isn't in the shop then the number of sales is returned as zero.
       */
      @Test
      public void numberOfSalesUnregisteredProduct() throws ProductNotRegisteredException, BarCodeAlreadyInUseException, StockUnavailableException {
        IShop shop = Factory.getInstance().makeShop();
        IProduct product1 = Factory.getInstance().makeProduct("98352825", "Mobile Phone");
        IProduct product2 = Factory.getInstance().makeProduct("275592746", "Laptop Computer");

        shop.registerProduct(product1);
       
        assertEquals(0, shop.getNumberOfSales(product2.getBarCode()));

      }

      /**
       * This checks the product with the most sales is returned as the most popular.
       */
      @Test
      public void mostPopularProduct() throws ProductNotRegisteredException, BarCodeAlreadyInUseException, StockUnavailableException {
        IShop shop = Factory.getInstance().makeShop();
        IProduct product1 = Factory.getInstance().makeProduct("473735292", "Mobile Phone");
        IProduct product2 = Factory.getInstance().makeProduct("23374972", "Laptop Computer");
        IProduct product3 = Factory.getInstance().makeProduct("883272926", "Washing Machine");
        IProduct product4 = Factory.getInstance().makeProduct("872545225", "Desktop Computer");

        shop.registerProduct(product1);
        shop.registerProduct(product2);
        shop.registerProduct(product3);
        shop.registerProduct(product4);

        //5
        shop.addStock(product1.getBarCode());
        shop.addStock(product1.getBarCode());
        shop.addStock(product1.getBarCode());
        shop.addStock(product1.getBarCode());
        shop.addStock(product1.getBarCode());
        //4
        shop.addStock(product2.getBarCode());
        shop.addStock(product2.getBarCode());
        shop.addStock(product2.getBarCode());
        shop.addStock(product2.getBarCode());
        //3
        shop.addStock(product3.getBarCode());
        shop.addStock(product3.getBarCode());
        shop.addStock(product3.getBarCode());
        //9
        shop.addStock(product4.getBarCode());
        shop.addStock(product4.getBarCode());
        shop.addStock(product4.getBarCode());
        shop.addStock(product4.getBarCode());
        shop.addStock(product4.getBarCode());
        shop.addStock(product4.getBarCode());
        shop.addStock(product4.getBarCode());
        shop.addStock(product4.getBarCode());
        shop.addStock(product4.getBarCode());

        //4
        shop.buyProduct(product1.getBarCode());
        shop.buyProduct(product1.getBarCode());
        shop.buyProduct(product1.getBarCode());
        shop.buyProduct(product1.getBarCode());
        //3
        shop.buyProduct(product2.getBarCode());
        shop.buyProduct(product2.getBarCode());
        shop.buyProduct(product2.getBarCode());
        //7
        shop.buyProduct(product4.getBarCode());
        shop.buyProduct(product4.getBarCode());
        shop.buyProduct(product4.getBarCode());
        shop.buyProduct(product4.getBarCode());
        shop.buyProduct(product4.getBarCode());
        shop.buyProduct(product4.getBarCode());
        shop.buyProduct(product4.getBarCode());

        assertEquals(product4, shop.getMostPopular());

      }

      /**
       * This checks if there are no products in the shop then null is returned as most popular product.
       */
      @Test
      public void mostPopularNoRegisteredProducts() throws ProductNotRegisteredException {
        IShop shop = Factory.getInstance().makeShop();
        IProduct product1 = Factory.getInstance().makeProduct("8736262", "Mobile Phone");
        IProduct product2 = Factory.getInstance().makeProduct("8736496", "Laptop Computer");

        assertEquals(null, shop.getMostPopular());

      }

      /**
       * This checks the correct product is returned from stock record.
       */
      @Test
      public void stockRecordCorrectProduct() {
        IShop shop = Factory.getInstance().makeShop();
        IProduct product = Factory.getInstance().makeProduct("8736262", "Mobile Phone");
        IStockRecord stock = Factory.getInstance().makeStockRecord(product);

        assertEquals(product, stock.getProduct());

      }

      /**
       * This checks the register product can handle a null value
       */
      @Test
      public void registerProductNull() throws BarCodeAlreadyInUseException {
        IShop shop = Factory.getInstance().makeShop();

        shop.registerProduct(null);

        assertEquals(0, shop.getNumberOfProducts());

      }

      /**
       * This checks the unregister product can handle a null value
       */
      @Test
      public void unregisterProductNull() throws BarCodeAlreadyInUseException, ProductNotRegisteredException {
        IShop shop = Factory.getInstance().makeShop();

        shop.unregisterProduct(null);

        assertEquals(0, shop.getNumberOfProducts());
        
      }

      /**
       * This checks the add stock method handles an null value correctly
       */
      @Test
      public void addStockNullBarcode() throws BarCodeAlreadyInUseException, ProductNotRegisteredException {
        IShop shop = Factory.getInstance().makeShop();
        IProduct product = Factory.getInstance().makeProduct("74493638", "Mobile Phone");

        shop.registerProduct(product);

        shop.addStock(null);

        assertEquals(0, shop.getTotalStockCount());

      }

      /**
       * This checks if a null value is passed for buy product it doesn't alter anything
       */
      @Test
      public void buyProductNullBarcode() throws BarCodeAlreadyInUseException, ProductNotRegisteredException, StockUnavailableException {
        IShop shop = Factory.getInstance().makeShop();
        IProduct product = Factory.getInstance().makeProduct("892635278", "Mobile Phone");

        shop.registerProduct(product);

        shop.addStock(product.getBarCode());

        shop.buyProduct(null);

        assertEquals(0, shop.getNumberOfSales(product.getBarCode()));
      }

      /**
       * This checks if a null value is passed for getting the stock count of a particular product it handles it correctly
       */
      @Test
      public void stockCountNullBarcode() throws ProductNotRegisteredException {
        IShop shop = Factory.getInstance().makeShop();
        IProduct product = Factory.getInstance().makeProduct("87825392", "Mobile Phone");

        assertEquals(0, shop.getStockCount(null));
      }

      /**
       * This checks if a null values is passed for getting the number of sales for a particular product.
       */
      @Test
      public void numberOfSalesNullBarcode() throws ProductNotRegisteredException {
        IShop shop = Factory.getInstance().makeShop();
        IProduct product = Factory.getInstance().makeProduct("756845664", "Mobile Phone");

        assertEquals(0, shop.getNumberOfSales(null));
      }

      


}
