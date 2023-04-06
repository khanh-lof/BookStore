/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khanhlh.order;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author ADMIN
 */
public class OrderDTO implements Serializable{
    int id;
    Date dateBy;
    float total;

    public OrderDTO() {
    }

    public OrderDTO(int id, Date dateBy, float total) {
        this.id = id;
        this.dateBy = dateBy;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public Date getDateBy() {
        return dateBy;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
    
}
