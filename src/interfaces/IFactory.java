package interfaces;


/**
 * Interface for a factory abstracting over instantiation of other interface types.
 *
 */
public interface IFactory {

    /**
     * Creates an instance of {@link IProduct} that can be stocked and sold in a shop.
     * @param barCode the bar code of the shop product
     * @param description the description of the shop product
     * @return the product
     */
    IProduct makeProduct(String barCode, String description);


    /**
     * Creates an instance of {@link IStockRecord} for a specified shop product.
     * Initial stock count and sales information should be set to zero.
     * @param product the shop product to use for this stock record
     * @return the stock record
     */
    IStockRecord makeStockRecord(IProduct product);


    /**
     * Creates an instance of an empty {@link IShop} for which no products have yet been registered.
     *
     * @return the shop
     */
    IShop makeShop();


}
