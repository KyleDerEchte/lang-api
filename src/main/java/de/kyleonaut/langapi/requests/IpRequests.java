package de.kyleonaut.langapi.requests;

import de.kyleonaut.langapi.entity.IpData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author kyleonaut
 * @version 1.0.0
 * created at 25.12.2021
 */
public interface IpRequests {
    @GET("json/{ip}")
    Call<IpData> getIpData(@Path("ip") String ip);
}
