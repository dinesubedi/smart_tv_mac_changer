package com.dinesubedi.smarttvmacchanger;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import java.io.IOException;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;

/*
 * Main Activity class that loads {@link MainFragment}.
 */
public class MainActivity extends FragmentActivity {

    ListView listView;
    TextView textView3, eth0_IP_value, wlan0_IP_value;
    EditText eth0_label_value, wlan0_label_value;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HashMap<String, HashMap> macAddressDetail = getMacAddress();
        textView3 = findViewById(R.id.textView3);
        eth0_IP_value = findViewById(R.id.eth0_IP_value);
        wlan0_IP_value = findViewById(R.id.wlan0_IP_value);
        eth0_label_value = findViewById(R.id.eth0_label_value);
        wlan0_label_value = findViewById(R.id.wlan0_label_value);


        Log.d("menlist - ----------------- ",macAddressDetail.toString());

        String ip_data_eth0 = "--";
        String mac_data_eth0 = "--";
        String ip_data_wlan0 = "--";
        String mac_data_wlan0 = "--";

        if( macAddressDetail.get("eth0") != null ){
            ip_data_eth0 = (String) macAddressDetail.get("eth0").get("ip_addr");
            mac_data_eth0 = (String) macAddressDetail.get("eth0").get("mac_addr");
        }
        if( macAddressDetail.get("wlan0") != null ){
            ip_data_wlan0 = (String) macAddressDetail.get("wlan0").get("ip_addr");
            mac_data_wlan0 = (String) macAddressDetail.get("wlan0").get("mac_addr");
        }

        eth0_IP_value.setText(ip_data_eth0);
        eth0_label_value.setText(mac_data_eth0);
        wlan0_IP_value.setText(ip_data_wlan0);
        wlan0_label_value.setText(mac_data_wlan0);

    }

    private static HashMap<String, HashMap> getMacAddress() {
        HashMap<String, HashMap> map = new HashMap<>();
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();
                String lan_key_name = networkInterface.getName();

                String ip_addr = "";
                String mac_addr = "";
                HashMap<String, String> data_values = new HashMap<>();

                byte[] bytes = networkInterface.getHardwareAddress();
                if( bytes != null ){
                    StringBuilder builder = new StringBuilder();
                    for (byte b : bytes) {
                        builder.append(String.format("%02X:", b));
                    }
                    if (builder.length() > 0) {
                        builder.deleteCharAt(builder.length() - 1);
                        mac_addr = builder.toString();
                    }
                }

                for ( InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()){
                    if ( interfaceAddress.getAddress().isSiteLocalAddress()){
                        ip_addr = interfaceAddress.getAddress().getHostAddress();
                    }
                }
                data_values.put("ip_addr", ip_addr);
                data_values.put("mac_addr", mac_addr);
                map.put(lan_key_name, data_values);
            }
        } catch (SocketException e) {
            map.put("exception_error", null);
        }
        return map;
    }

    public void eth0_submit(View view) {

        String mac = "F6:B9:FB:20:3A:D6";
        System.out.println("eeeeeeeee");

        try {

            Process p = Runtime
                    .getRuntime() .exec("cmd /c start cmd.exe /K \"  \"reg add HKEY_LOCAL_MACHINE\\SYSTEM\\CurrentControlSet\\Control\\Class\\{4D36E972-E325-11CE-BFC1-08002BE10318}\\0007 /t REG_SZ /v NetworkAddress /d " + mac + " /f ");

//            Runtime.getRuntime().exec("busybox ifconfig eth0 hw ether F6:B9:FB:20:3A:D6");
            HashMap<String, HashMap> macAddressDetail = getMacAddress();
            Log.d("menlist - ----------------- ",macAddressDetail.toString());
        } catch (IOException e) {
            e.printStackTrace();
//            System.out.println("ggggggggggggggg "+e.printStackTrace());
        }


    }
}