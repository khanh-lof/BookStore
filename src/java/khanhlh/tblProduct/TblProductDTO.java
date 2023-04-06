/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khanhlh.tblProduct;

import java.io.Serializable;

/**
 *
 * @author ADMIN
 */
public class TblProductDTO implements Serializable{
    private String sku;
    private String name;
    private String descript;
    private int quantity;
    private float price;
    private boolean status;

    public TblProductDTO() {
    }

    public TblProductDTO(String sku, String name, String descript, int quantity, float price, boolean status) {
        this.sku = sku;
        this.name = name;
        this.descript = descript;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
    }

    /**
     * @return the sku
     */
    public String getSku() {
        return sku;
    }

    /**
     * @param sku the sku to set
     */
    public void setSku(String sku) {
        this.sku = sku;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the Descript
     */
    public String getDescript() {
        return descript;
    }

    /**
     * @param Descript the Descript to set
     */
    public void setDescript(String Descript) {
        this.descript = Descript;
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the price
     */
    public float getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     * @return the status
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(boolean status) {
        this.status = status;
    }
    
}
