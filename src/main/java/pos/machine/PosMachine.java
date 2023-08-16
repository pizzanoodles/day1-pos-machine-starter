package pos.machine;

import java.util.ArrayList;
import java.util.List;

public class PosMachine {
    //TODO: use business terms in commit message instead of function name
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

    private String renderReceipt(Receipt receipt) {
        String itemsReceipt = generateItemsReceipt(receipt);
        return generateReceipt(itemsReceipt, receipt.getTotalPrice());
    }

    private String generateReceipt(String itemsReceipt, int totalPrice) {
        return "***<store earning no money>Receipt***" + "\n" +
                itemsReceipt +
                "----------------------" + "\n" +
                "Total: " + totalPrice + " (yuan)" + "\n" +
                "**********************";
    }

    private String generateItemsReceipt(Receipt receipt) {
        StringBuilder itemsReceipt = new StringBuilder();
        receipt.getReceiptItems().forEach(receiptItem -> itemsReceipt.append("Name: ").append(receiptItem.getName())
                .append(", Quantity: ").append(receiptItem.getQuantity())
                .append(", Unit price: ").append(receiptItem.getUnitPrice()).append(" (yuan)")
                .append(", Subtotal: ").append(receiptItem.getSubTotal()).append(" (yuan)").append("\n"));
        return itemsReceipt.toString();
    }
}
