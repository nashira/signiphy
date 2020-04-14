package xyz.rthqks.signiphy.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(
        entities = {
                Gif.class
        },
        exportSchema = false,
        version = SigDb.VERSION
)
public abstract class SigDb extends RoomDatabase {
    public static final int VERSION = 3;

    public abstract SigDao dao();
}
