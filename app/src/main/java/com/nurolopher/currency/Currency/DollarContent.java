package com.nurolopher.currency.Currency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DollarContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    static {
        // Add 3 sample items.
        addItem(new DummyItem("НБКР", 58.00, 60.20));
        addItem(new DummyItem("Айыл", 59.00, 61.20));
        addItem(new DummyItem("Деминбанк", 59.00, 61.20));
        addItem(new DummyItem("Экоисламик", 58.00, 60.20));
        addItem(new DummyItem("KICB", 58.00, 60.20));
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.title, item);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public String title;
        public double buy;
        public double sell;

        public DummyItem(String title, double buy, double sell) {
            this.title = title;
            this.buy = buy;
            this.sell = sell;
        }

        @Override
        public String toString() {
            return title;
        }
    }
}
