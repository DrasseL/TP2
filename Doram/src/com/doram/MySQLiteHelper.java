package com.doram;

import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class MySQLiteHelper extends SQLiteOpenHelper {
	//Database Version
	private static final int DATABASE_VERSION = 1;
	//Database Name
	private static final String DATABASE_NAME = "DoramDB.db";
	
	//Table names
	private static final String TABLE_FACTIONS = "factions";
	private static final String TABLE_USERS = "users";
	private static final String TABLE_RANKS = "ranks";
	//Factions Table Columns names
	private static final String FACTIONS_ID = "id";
	private static final String FACTIONS_NAME = "name";
	private static final String FACTIONS_DESCRIPTION = "description";
	//Users Table Columns names
	private static final String USERS_ID = "id";
	private static final String USERS_USERNAME = "username";
	private static final String USERS_PASSWORD = "password";
	private static final String USERS_FKFACTION = "fkfaction";
	private static final String USERS_FKRANK = "fkrank";
	//Ranks Table Columns names
	private static final String RANKS_ID = "id";
	private static final String RANKS_NAME = "name";
	
	private static final String[] FACTIONS_COLUMNS = {FACTIONS_ID,FACTIONS_NAME,FACTIONS_DESCRIPTION};
	private static final String[] USERS_COLUMNS = {USERS_ID,USERS_USERNAME,USERS_PASSWORD,USERS_FKFACTION,USERS_FKRANK};
	private static final String[] RANKS_COLUMNS = {RANKS_ID,RANKS_NAME};
	
	public MySQLiteHelper(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db){
		// SQL Statement to create tables
		String CREATE_FACTION_TABLE = "CREATE TABLE factions (" + 
		"id INTEGER PRIMARY KEY AUTOINCREMENT, " +
		"name TEXT, " +
		"description TEXT )";
		
		String CREATE_USER_TABLE = "CREATE TABLE users (" +
		"id INTEGER PRIMARY KEY AUTOINCREMENT," + 
		"username TEXT, " +
		"password TEXT, " +
		"fkfaction int, " +
		"fkrank int, " +
		"FOREIGN KEY ("+USERS_FKFACTION+") REFERENCES "+ TABLE_FACTIONS +" ("+FACTIONS_ID+")," +
		"FOREIGN KEY ("+USERS_FKRANK+") REFERENCES "+ TABLE_RANKS +" ("+RANKS_ID+");";
		
		String CREATE_RANK_TABLE = "CREATE TABLE ranks (" +
		"id INTEGER PRIMARY KEY AUTOINCREMENT," +
		"name TEXT )";
		
		db.execSQL(CREATE_FACTION_TABLE);
		db.execSQL(CREATE_USER_TABLE);
		db.execSQL(CREATE_RANK_TABLE);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		// Drop older tables if existed
		db.execSQL("DROP TABLE IF EXISTS factions");
		db.execSQL("DROP TABLE IF EXISTS users");
		db.execSQL("DROP TABLE IF EXISTS ranks");
		// Create fresh factions table
		this.onCreate(db);
	}
	
	// ************************************************************************************
	// ************************************************************************************
    // **********************************CRUD FACTIONS*************************************
	// ************************************************************************************
	// ************************************************************************************
	
	public void addFaction(Faction faction){
		Log.d("addFaction", faction.toString());
		
		//1. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		
		//2. create ContentValues to add key "column"/value
		ContentValues values = new ContentValues();
		//get name
		values.put(FACTIONS_NAME, faction.getName());
		values.put(FACTIONS_DESCRIPTION, faction.getDescription());
		
		//3. insert
		db.insert(TABLE_FACTIONS, null, values);
		
		//4. close
		db.close();
	}
	
	public Faction getFaction(int id){
		// 1. get reference to readable DB
		SQLiteDatabase db = this.getReadableDatabase();
		// 2. build query
		Cursor cursor = 
				db.query(TABLE_FACTIONS, // a. Table
				FACTIONS_COLUMNS, 				 // b. Columns
				"id = ?", 				 // c. selections
				new String[] {String.valueOf(id)}, // d. selections args
				null, // e. group by
				null, // f. having
				null, // g. order by
				null); // h. limit
		// 3. if we got results get the first one
		if (cursor != null){
			cursor.moveToFirst();
		}
		
		// 4. build Faction object
		Faction faction = new Faction();
		faction.setId(Integer.parseInt(cursor.getString(0)));
		faction.setName(cursor.getString(1));
		faction.setDescription(cursor.getString(2));
		
		Log.d("getFaction("+id+")", faction.toString());
		
		// 5. Return Faction
		return faction;
	}
	
	public List<Faction> getAllFactions(){
		List<Faction> factions = new LinkedList<Faction>();
		
		//1. Build the query
		String query = "SELECT * FROM " + TABLE_FACTIONS;
		
		//2. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		
		//3. Go over each row, build faction and add it to list
		Faction faction = null;
		if(cursor.moveToFirst()){
			do {
				faction = new Faction();
				faction.setId(Integer.parseInt(cursor.getString(0)));
				faction.setName(cursor.getString(1));
				faction.setDescription(cursor.getString(2));
				
				//Add faction to factions
				factions.add(faction);
			} while (cursor.moveToNext());
		}
		Log.d("getAllFactions()", factions.toString());
		
		// return factions
		return factions;
	}
	
	public int updateFaction(Faction faction){
		// 1. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		
		// 2. create ContentValues to add key "column/value"
		ContentValues values = new ContentValues();
		values.put("name", faction.getName());
		values.put("description", faction.getDescription());
		
		// 3. updating row
		int i = db.update(TABLE_FACTIONS,
				values,
				FACTIONS_ID+" = ?",
				new String[] {String.valueOf(faction.getId()) });
		// 4. close
		db.close();
		
		return i;
	}
	
	public void deleteFaction(Faction faction){
		// 1. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		
		// 2. delete
		db.delete(TABLE_FACTIONS,
				FACTIONS_ID+" = ?",
				new String[] {String.valueOf(faction.getId()) });
		
		// 3. close
		db.close();
		
		Log.d("deleteFaction", faction.toString());
	}
	
	public void deleteFactions(){
		SQLiteDatabase db = this.getWritableDatabase();
		
		db.execSQL("delete from " + TABLE_FACTIONS);
	}
	
		// ************************************************************************************
		// ************************************************************************************
	    // **********************************CRUD USERS****************************************
		// ************************************************************************************
		// ************************************************************************************
	
	public void addUser(User user){
		Log.d("addUser", user.toString());
		
		//1. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		
		//2. create ContentValues to add key "column"/value
		ContentValues values = new ContentValues();
		//get name
		values.put(USERS_USERNAME, user.getUsername());
		values.put(USERS_PASSWORD, user.getPassword());
		values.put(USERS_FKFACTION, user.getFkFaction());
		values.put(USERS_FKRANK, user.getFkRank());
		//3. insert
		db.insert(TABLE_USERS, null, values);
		
		//4. close
		db.close();
	}
	
	public User getUser(int id){
		// 1. get reference to readable DB
		SQLiteDatabase db = this.getReadableDatabase();
		// 2. build query
		Cursor cursor = 
				db.query(TABLE_USERS, // a. Table
				USERS_COLUMNS, 				 // b. Columns
				"id = ?", 				 // c. selections
				new String[] {String.valueOf(id)}, // d. selections args
				null, // e. group by
				null, // f. having
				null, // g. order by
				null); // h. limit
		// 3. if we got results get the first one
		if (cursor != null){
			cursor.moveToFirst();
		}
		
		// 4. build Faction object
		User user = new User();
		user.setId(Integer.parseInt(cursor.getString(0)));
		user.setUsername(cursor.getString(1));
		user.setPassword(cursor.getString(2));
		user.setFkFaction(Integer.parseInt(cursor.getString(3)));
		user.setFkRank(Integer.parseInt(cursor.getString(4)));
		
		Log.d("getUser("+id+")", user.toString());
		
		// 5. Return Faction
		return user;
	}
	
	public List<User> getAllUsers(){
		List<User> users = new LinkedList<User>();
		
		//1. Build the query
		String query = "SELECT * FROM " + TABLE_USERS;
		
		//2. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		
		//3. Go over each row, build user and add it to list
		User user = null;
		if(cursor.moveToFirst()){
			do {
				user = new User();
				user.setId(Integer.parseInt(cursor.getString(0)));
				user.setUsername(cursor.getString(1));
				user.setPassword(cursor.getString(2));
				user.setFkFaction(Integer.parseInt(cursor.getString(3)));
				user.setFkRank(Integer.parseInt(cursor.getString(4)));
				
				//Add faction to factions
				users.add(user);
			} while (cursor.moveToNext());
		}
		Log.d("getAllUsers()", users.toString());
		
		// return users
		return users;
	}
	
	public int updateUser(User user){
		// 1. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		
		// 2. create ContentValues to add key "column/value"
		ContentValues values = new ContentValues();
		values.put(USERS_USERNAME, user.getUsername());
		values.put(USERS_PASSWORD, user.getPassword());
		values.put(USERS_FKFACTION, user.getFkFaction());
		values.put(USERS_FKRANK, user.getFkRank());
		
		// 3. updating row
		int i = db.update(TABLE_USERS,
				values,
				USERS_ID+" = ?",
				new String[] {String.valueOf(user.getId()) });
		// 4. close
		db.close();
		
		return i;
	}
	
	public void deleteUser(User user){
		// 1. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		
		// 2. delete
		db.delete(TABLE_USERS,
				USERS_ID+" = ?",
				new String[] {String.valueOf(user.getId()) });
		
		// 3. close
		db.close();
		
		Log.d("deleteUser", user.toString());
	}
	
			// ************************************************************************************
			// ************************************************************************************
		    // **********************************CRUD RANKS****************************************
			// ************************************************************************************
			// ************************************************************************************
	
	public void addRanks(Rank rank){
		Log.d("addRank", rank.toString());
		
		//1. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		
		//2. create ContentValues to add key "column"/value
		ContentValues values = new ContentValues();
		//get name
		values.put(RANKS_NAME, rank.getName());
		//3. insert
		db.insert(TABLE_RANKS, null, values);
		
		//4. close
		db.close();
	}
	
	public Rank getRank(int id){
		// 1. get reference to readable DB
		SQLiteDatabase db = this.getReadableDatabase();
		// 2. build query
		Cursor cursor = 
				db.query(TABLE_RANKS, // a. Table
				RANKS_COLUMNS, 				 // b. Columns
				"id = ?", 				 // c. selections
				new String[] {String.valueOf(id)}, // d. selections args
				null, // e. group by
				null, // f. having
				null, // g. order by
				null); // h. limit
		// 3. if we got results get the first one
		if (cursor != null){
			cursor.moveToFirst();
		}
		
		// 4. build Faction object
		Rank rank = new Rank();
		rank.setId(Integer.parseInt(cursor.getString(0)));
		rank.setName(cursor.getString(1));
		
		Log.d("getRank("+id+")", rank.toString());
		
		// 5. Return Faction
		return rank;
	}
	
	public List<Rank> getAllRanks(){
		List<Rank> ranks = new LinkedList<Rank>();
		
		//1. Build the query
		String query = "SELECT * FROM " + TABLE_RANKS;
		
		//2. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		
		//3. Go over each row, build user and add it to list
		Rank rank = null;
		if(cursor.moveToFirst()){
			do {
				rank = new Rank();
				rank.setId(Integer.parseInt(cursor.getString(0)));
				rank.setName(cursor.getString(1));
				
				
				ranks.add(rank);
			} while (cursor.moveToNext());
		}
		Log.d("getAllRanks()", ranks.toString());
		
		// return users
		return ranks;
	}
	
	public int updateRank(Rank rank){
		// 1. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		
		// 2. create ContentValues to add key "column/value"
		ContentValues values = new ContentValues();
		values.put(RANKS_NAME, rank.getName());
		
		// 3. updating row
		int i = db.update(TABLE_RANKS,
				values,
				RANKS_ID+" = ?",
				new String[] {String.valueOf(rank.getId()) });
		// 4. close
		db.close();
		
		return i;
	}
	
	public void deleteRank(Rank rank){
		// 1. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		
		// 2. delete
		db.delete(TABLE_RANKS,
				RANKS_ID+" = ?",
				new String[] {String.valueOf(rank.getId()) });
		
		// 3. close
		db.close();
		
		Log.d("deleteUser", rank.toString());
	}
}

