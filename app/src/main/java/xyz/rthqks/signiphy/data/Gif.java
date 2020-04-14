package xyz.rthqks.signiphy.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

import xyz.rthqks.signiphy.net.GiphyResponse;

@Entity
public class Gif {
    @PrimaryKey
    @NonNull
    public String id;
    public String title;
    public String url;

    public Gif(@NonNull String id, String title, String url) {
        this.id = id;
        this.title = title;
        this.url = url;
    }

    public static Gif from(GiphyResponse.Gif g) {
        return new Gif(g.id, g.title, g.urlFor("downsized_large"));
    }

    @Override
    public String toString() {
        return "Gif{" +
                "id='" + id + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gif gif = (Gif) o;
        return id.equals(gif.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
