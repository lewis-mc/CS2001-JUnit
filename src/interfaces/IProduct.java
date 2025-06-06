package interfaces;

/**
 * Interface for shop products that can be stocked and sold in a shop.
 *
 */
public interface IProduct {

    /**
     * This method returns the product's bar code.
     * Bar codes can be any valid String in this system.
     * @return the bar code for this shop product
     */
    String getBarCode();

    /**
     * This method returns the product description.
     * @return the description of the shop product
     */
    String getDescription();

}
