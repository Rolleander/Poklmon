package com.broll.poklmon;

import android.content.Context;
import android.net.wifi.WifiManager;

import com.broll.poklmon.network.AddressProvider;
import com.esotericsoftware.minlog.Log;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteOrder;

/**
 * Created by Roland on 26.10.2017.
 */

public class AndroidIpProvider implements AddressProvider {

    private Context context;

    public AndroidIpProvider(Context context){
        this.context=context;
    }

    @Override
    public String getIpAddress() throws UnknownHostException, Exception {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        int ipAddress = wifiManager.getConnectionInfo().getIpAddress();

        // Convert little-endian to big-endian if needed
        if (ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN)) {
            ipAddress = Integer.reverseBytes(ipAddress);
        }

        byte[] ipByteArray = BigInteger.valueOf(ipAddress).toByteArray();

        String ipAddressString;
        try {
            ipAddressString = InetAddress.getByAddress(ipByteArray).getHostAddress();
        } catch (UnknownHostException ex) {
            Log.error("Unable to get host address.",ex);
            ipAddressString = null;
        }
        return ipAddressString;
    }
}
