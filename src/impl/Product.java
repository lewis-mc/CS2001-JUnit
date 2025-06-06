package impl;

import interfaces.IProduct;

/**
 * This class represents products that can be stocked and sold in a shop.
 *
 */
public class Product implements IProduct {

    private String barcode;
    private String description;

    /**
     * Product constructor that instantiates the barcode and description of the product
     * @param barcode the string barcode of the product
     * @param description the string description of the product
     */
    public Product(String barcode, String description) {
        this.barcode = barcode;
        this.description = description;
    }

    /**
     * @return the barcode
     */
    @Override
    public String getBarCode() {
        // TODO Auto-generated method stub
        return this.barcode;
    }

    /**
     * @return the description
     */
    @Override
    public String getDescription() {
        // TODO Auto-generated method stub
        return this.description;
    }


}
