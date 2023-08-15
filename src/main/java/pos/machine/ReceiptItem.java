package pos.machine;

public class ReceiptItem {
    private String name;
    private int quantity;
    private int unitPrice;
    private int subTotal;

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setSubTotal(int subTotal) {
        this.subTotal = subTotal;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getSubTotal() {
        return subTotal;
    }

    public String getName() {
        return name;
    }
}
