package com.doram;

import android.provider.BaseColumns;

public final class FeedReaderContract {
	// To prevent someone from accidentally instantiating the contract class.
	public FeedReaderContract(){}
	
	/* Inner class that defines the table contents */
	public static abstract class FeedEntry implements BaseColumns {
		public static final String TABLE_NAME = "factions";
		public static final String COLUMN_NAME_id = "id";
		public static final String COLUMN_NAME_name = "name";
		public static final String COLUMN_NAME_description = "description";
		
	}
}
