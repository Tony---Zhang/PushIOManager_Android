package com.nrma.omc

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * This is hack for PUSH IO Database to solve no tables issue
 *
 * After they add database migration steps, this hack will be useless
 *
 * See also [issue](https://github.com/pushio/PushIOManager_Android)
 *
 */

object PushIODBStoreContract {

    object InboxMessageEntry {
        const val TABLE_NAME = "InboxMessage"
        const val COLUMN_ID = "id"
        const val COLUMN_SUBJECT = "subject"
        const val COLUMN_MESSAGE = "message"
        const val COLUMN_ICON_URL = "icon_url"
        const val COLUMN_MESSAGE_CENTER_NAME = "message_center_name"
        const val COLUMN_DEEPLINK_URL = "deeplink_url"
        const val COLUMN_RICHMESSAGE_HTML = "richmessage_html"
        const val COLUMN_RICHMESSAGE_URL = "richmessage_url"
        const val COLUMN_SENT_TS = "sent_ts"
        const val COLUMN_EXPIRY_TS = "expiry_ts"
        const val COLUMN_USER_ID = "user_id"
        const val COLUMN_DEVICE_ID = "device_id"
        const val COLUMN_EXPIRY_DATETIME = "expiry_datetime"
        const val COLUMN_FETCH_DATETIME = "fetch_datetime"
    }

    object FormLinkEntry {
        const val TABLE_NAME = "FormLink"
        const val COLUMN_MESSAGEID = "messageID"
        const val COLUMN_FORM_LINK = "form_link"
        const val COLUMN_EXPIRY_DATETIME = "expiry_datetime"
    }

    const val SQL_CREATE_INBOX_MESSAGE_ENTRY =
            "CREATE TABLE IF NOT EXISTS '${InboxMessageEntry.TABLE_NAME}' (" +
                    "${InboxMessageEntry.COLUMN_ID} TEXT PRIMARY KEY NOT NULL," +
                    "${InboxMessageEntry.COLUMN_SUBJECT} TEXT," +
                    "${InboxMessageEntry.COLUMN_MESSAGE} TEXT," +
                    "${InboxMessageEntry.COLUMN_ICON_URL} TEXT," +
                    "${InboxMessageEntry.COLUMN_MESSAGE_CENTER_NAME} TEXT," +
                    "${InboxMessageEntry.COLUMN_DEEPLINK_URL} TEXT," +
                    "${InboxMessageEntry.COLUMN_RICHMESSAGE_HTML} TEXT," +
                    "${InboxMessageEntry.COLUMN_RICHMESSAGE_URL} TEXT," +
                    "${InboxMessageEntry.COLUMN_SENT_TS} TEXT," +
                    "${InboxMessageEntry.COLUMN_EXPIRY_TS} TEXT," +
                    "${InboxMessageEntry.COLUMN_USER_ID} TEXT," +
                    "${InboxMessageEntry.COLUMN_DEVICE_ID} TEXT," +
                    "${InboxMessageEntry.COLUMN_EXPIRY_DATETIME} TEXT," +
                    "${InboxMessageEntry.COLUMN_FETCH_DATETIME} TEXT)"

    const val SQL_CREATE_FORM_LINK_ENTRY =
            "CREATE TABLE IF NOT EXISTS '${FormLinkEntry.TABLE_NAME}' (" +
                    "${FormLinkEntry.COLUMN_MESSAGEID} TEXT PRIMARY KEY REFERENCES ${InboxMessageEntry.TABLE_NAME} (${InboxMessageEntry.COLUMN_ID})," +
                    "${FormLinkEntry.COLUMN_FORM_LINK} TEXT," +
                    "${FormLinkEntry.COLUMN_EXPIRY_DATETIME} TEXT)"
}

class PushIODBStoreHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
    }

    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "PushIOManager.db"
    }
}

class PushIODBStoreRepo(context: Context) {

    private var dbHelper: PushIODBStoreHelper = PushIODBStoreHelper(context)

    fun createCacheTables() {
        val database = dbHelper.writableDatabase
        database.execSQL(PushIODBStoreContract.SQL_CREATE_INBOX_MESSAGE_ENTRY)
        database.execSQL(PushIODBStoreContract.SQL_CREATE_FORM_LINK_ENTRY)
    }
}
