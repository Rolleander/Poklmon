package com.broll.poklmon.network;

import java.net.UnknownHostException;

/**
 * Created by Roland on 26.10.2017.
 */

public interface AddressProvider {
    public String getIpAddress() throws UnknownHostException, Exception;
}
