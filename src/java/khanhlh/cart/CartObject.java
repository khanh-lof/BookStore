/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khanhlh.cart;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import khanhlh.tblProduct.TblProductDTO;

/**
 *
 * @author ADMIN
 */
public class CartObject implements Serializable{
    private Map<String, Integer> items;

    public Map<String, Integer> getItems() {
        return items;
    }
    public void addItemCart(String sku, int quantity)
                        throws SQLException, NamingException{
        if (sku == null){
            return;
        }
        if(sku.trim().isEmpty()){
            return;
        }
        if(quantity == 0){
            return;
        }
        if(this.items == null){
            this.items = new HashMap<>();
        }//items has not existed
        if(this.items.containsKey(sku)){
            quantity += this.items.get(sku);
        }//end item has existed
        //update items
        this.items.put(sku, quantity);
    }
    public void removeItemFromCart(String sku)
                        throws SQLException, NamingException{
        if( sku == null){
            return;
        }
        if(sku.trim().isEmpty()){
            return;
        }
        if(this.items == null){
            return;
        }
        if(this.items.containsKey(sku)){
            this.items.remove(sku);
            if(this.items.isEmpty()){
                this.items = null;
            }
        }
    }
    public float getTotalItem(TblProductDTO item){
        return item.getPrice()*this.items.get(item.getSku());
    }
    public float getTotalOrders(Map<String,TblProductDTO> storage){
        float total = 0;
        for (String sku : this.items.keySet()) {
            total += getTotalItem(storage.get(sku));
        }
        return total;
    }
}
