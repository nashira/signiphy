package xyz.rthqks.signiphy.data;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public abstract class SigDao {

    @Query("SELECT * FROM Gif")
    public abstract DataSource.Factory<Integer, Gif> getGifs();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(List<Gif> gifs);

    @Query("DELETE FROM Gif")
    public abstract void deleteAll();

    @Query("SELECT * FROM Gif WHERE id = :id")
    public abstract Gif getGit(String id);
}
