## PushIOManager database upgrade issue

In version `6.38.1`, push is is supported for `message center cache`
It added two new **tables(InboxMessage & FormLink)** to support this feature. This feature is great, **but what a pity, they forget to do database migration.**

If you upgrade your sdk version to this new version, you can't fetch any messages from message center because no data in the two tables.
 
Here are the best way to solve this problem:

1. Upgrade their database from version 1 to version 2
2. Add two lines following to `public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)` method in `PIODBStore`

```java
db.execSQL("CREATE TABLE InboxMessage (id TEXT PRIMARY KEY NOT NULL, subject TEXT, message TEXT, icon_url TEXT, message_center_name TEXT, deeplink_url TEXT, richmessage_html TEXT, richmessage_url TEXT, sent_ts TEXT, expiry_ts TEXT, user_id TEXT, device_id TEXT, expiry_datetime TEXT, fetch_datetime TEXT)");
db.execSQL("CREATE TABLE FormLink (messageID TEXT PRIMARY KEY REFERENCES InboxMessage (id), form_link TEXT, expiry_datetime TEXT)");
```

### What's more
#### If you are using 6.38.1 right now, here are some hack code to do this

[Hack code](PushIODBStore.kt)

You need to call

```kotlin
PushIODBStoreRepo(context).createCacheTables()
```

After you `registerApp` in your database