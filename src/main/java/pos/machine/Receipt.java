package pos.machine;

import java.util.List;

public class Receipt {
    private List<ReceiptItem> receiptItems;
    private int totalPrice;

    public void setReceiptItems(List<ReceiptItem> receiptItems) {
        this.receiptItems = receiptItems;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public List<ReceiptItem> getReceiptItems() {
        return receiptItems;
    }
}
