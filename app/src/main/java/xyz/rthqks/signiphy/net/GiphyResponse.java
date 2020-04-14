package xyz.rthqks.signiphy.net;

import java.util.List;
import java.util.Map;

public class GiphyResponse {
    public List<Gif> data;
    public Meta meta;
    public Pagination pagination;

    public static class Gif {
        public String id;
        public String title;
        public Map<String, Image> images;

        public String urlFor(String size) {
            return images.get(size).url;
        }
    }

    public static class Image {
        public int width;
        public int height;
        public String url;
    }

    public static class Meta {}
    public static class Pagination {}
}
