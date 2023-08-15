package pos.machine;

import java.util.ArrayList;
import java.util.List;

public class PosMachine {
    public String printReceipt(List<String> barcodes) {
        List<ReceiptItem> receiptItems = decodeToItems(barcodes);
        Receipt receipt = calculateCost(receiptItems);
        return renderReceipt(receipt);
    }

    private List<ReceiptItem> decodeToItems(List<String> barcodes) {
        List<Item> items = ItemsLoader.loadAllItems();
        List<ReceiptItem> receiptItems = new ArrayList<>();
        items.forEach(item -> {
            ReceiptItem receiptItem = new ReceiptItem();
            int count = (int) barcodes.stream()
                    .filter(barcode ->
                            barcode.equals(item.getBarcode())).count();
            receiptItem.setQuantity(count);
            receiptItem.setUnitPrice(item.getPrice());
            receiptItem.setName(item.getName());
            receiptItems.add(receiptItem);
        });
        return receiptItems;
    }

    private Receipt calculateCost(List<ReceiptItem> receiptItems) {
        List<ReceiptItem> receiptItemsWithSubtotal = calculatePerItem(receiptItems);
        Receipt receipt = new Receipt();
        receipt.setReceiptItems(receiptItemsWithSubtotal);
        receipt.setTotalPrice(calculateTotalPrice(receiptItemsWithSubtotal));
        return receipt;
    }

    private List<ReceiptItem> calculatePerItem(List<ReceiptItem> receiptItems) {
        receiptItems.forEach(receiptItem -> receiptItem.setSubTotal(receiptItem.getQuantity() * receiptItem.getUnitPrice()));
        return receiptItems;
    }

    private int calculateTotalPrice(List<ReceiptItem> receiptItems) {
        return receiptItems.stream().mapToInt(ReceiptItem::getSubTotal).sum();
    }
}
