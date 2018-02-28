package asuper.maathis.maathai;



public class Cart_data {
    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItems_ordered() {
        return items_ordered;
    }

    public void setItems_ordered(String items_ordered) {
        this.items_ordered = items_ordered;
    }

    public String getItem_price() {
        return item_price;
    }

    public void setItem_price(String item_price) {
        this.item_price = item_price;
    }

    public String item_id;

    public Cart_data(String item_id, String item_name, String items_ordered, String item_price) {
        this.item_id = item_id;
        this.item_name = item_name;
        this.items_ordered = items_ordered;
        this.item_price = item_price;
    }

    public String item_name;
    public String items_ordered;
    public String item_price;

}
