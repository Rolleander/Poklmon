package com.broll.poklmon.desktop;

import com.broll.poklmon.network.AddressProvider;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Roland on 26.10.2017.
 */

public class DesktopIpProvider implements AddressProvider{
    @Override
    public String getIpAddress() throws Exception {
        return InetAddress.getLocalHost().getHostAddress();
    }
}
