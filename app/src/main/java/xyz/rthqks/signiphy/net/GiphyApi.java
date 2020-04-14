package xyz.rthqks.signiphy.net;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GiphyApi {
    @GET("/v1/gifs/trending")
    Single<GiphyResponse> getTrending(@Query("offset") int offset, @Query("limit") int limit);

    @GET("/v1/gifs/search")
    Single<GiphyResponse> getSearch(@Query("q") String query, @Query("offset") int offset, @Query("limit") int limit);
}
