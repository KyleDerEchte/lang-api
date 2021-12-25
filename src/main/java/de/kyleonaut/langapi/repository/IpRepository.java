package de.kyleonaut.langapi.repository;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.kyleonaut.langapi.entity.IpData;
import de.kyleonaut.langapi.requests.IpRequests;
import lombok.RequiredArgsConstructor;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

/**
 * @author kyleonaut
 * @version 1.0.0
 * created at 25.12.2021
 */
@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class IpRepository implements IIpRepository {
    private final IpRequests ipRequests;

    @Override
    public IpData getIpData(String ip) {
        final Call<IpData> ipData = ipRequests.getIpData(ip);
        try {
            final Response<IpData> response = ipData.execute();
            if (response.isSuccessful()) {
                return response.body();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
