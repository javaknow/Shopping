package com.shopping.swb.shopping.db;

import android.provider.BaseColumns;

public class CollectContract {
    public static class CollectEntry implements BaseColumns {
        public static final String DATABASE_NAME = "CollectDB";
        public static final int DATABAE_VERSION = 1;
        public static final String DATABASE_TABLE_COLLECT = "collect";
        public static final String COLUMNS_NUM_ID = "num_id";
        public static final String COLUMNS_TITLE = "title";
        public static final String COLUMNS_NOW_PRICE = "now_price";
        public static final String COLUMNS_ORIGIN_PRICE = "origin_price";
        public static final String COLUMNS_DISCOUNT = "discount";
        public static final String COLUMNS_SOLD = "sold";
        public static final String COLUMNS_PIC_URL = "pic_url";
    }
}
