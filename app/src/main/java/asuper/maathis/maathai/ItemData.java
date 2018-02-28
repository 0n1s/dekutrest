package asuper.maathis.maathai;

import java.security.PublicKey;
import java.util.HashMap;

/**
 * Created by jjmomanyis on 6/7/17.
 */

public class ItemData {

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String category;

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_description() {
        return item_description;
    }

    public void setItem_description(String item_description) {
        this.item_description = item_description;
    }

    public String getItem_price() {
        return item_price;
    }

    public void setItem_price(String item_price) {
        this.item_price = item_price;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public ItemData(String item_price, String image_url, String item_name, String item_description, String category, String quantity_remaining, String qntytype) {
        this.item_price = item_price;
        this.image_url = image_url;
        this.item_name = item_name;
        this.quantity_remaining=quantity_remaining;
        this.category=category;
        this.item_description = item_description;
        this.qntytype = qntytype;
    }

    public String item_price;
    public String image_url;

    public String getQuantity_remaining() {
        return quantity_remaining;
    }

    public void setQuantity_remaining(String quantity_remaining) {
        this.quantity_remaining = quantity_remaining;
    }

    public String quantity_remaining;
    public String item_name;
    public String item_description;

    public String getQntytype() {
        return qntytype;
    }

    public void setQntytype(String qntytype) {
        this.qntytype = qntytype;
    }

    public String  qntytype;





}