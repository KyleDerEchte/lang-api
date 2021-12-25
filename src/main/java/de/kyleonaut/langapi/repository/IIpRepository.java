package de.kyleonaut.langapi.repository;

import com.google.inject.ImplementedBy;
import de.kyleonaut.langapi.entity.IpData;

/**
 * @author kyleonaut
 * @version 1.0.0
 * created at 25.12.2021
 */
@ImplementedBy(IpRepository.class)
public interface IIpRepository {
    IpData getIpData(String ip);
}
