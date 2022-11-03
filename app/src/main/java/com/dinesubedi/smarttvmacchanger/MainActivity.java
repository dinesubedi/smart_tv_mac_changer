package com.dinesubedi.smarttvmacchanger;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;

/*
 * Main Activity class that loads {@link MainFragment}.
 */
public class MainActivity extends FragmentActivity {

    ListView listView;
    TextView textView3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HashMap<String, String> macAddressDetail = getMacAddress();

        listView = findViewById(R.id.listView);
        textView3 = findViewById(R.id.textView3);




//        String getMac
        textView3.setText(macAddressDetail.get("eth0"));
        Log.d("MyMacIS ---- >>>>>>>", macAddressDetail.get("eth0"));
    }

    private static HashMap<String, String> getMacAddress() {
        HashMap<String, String> map = new HashMap<>();
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();
                String lan_key_value = "";
                String lan_key_name = networkInterface.getName();
                byte[] bytes = networkInterface.getHardwareAddress();
                if( bytes != null ){

                    StringBuilder builder = new StringBuilder();
                    for (byte b : bytes) {
                        builder.append(String.format("%02X:", b));
                    }
                    if (builder.length() > 0) {
                        builder.deleteCharAt(builder.length() - 1);
                        lan_key_value = builder.toString();
                    }
                }

                Log.d("---- >>>>>>>", lan_key_name+" ------- "+lan_key_value);

                map.put(lan_key_name, lan_key_value);
            }
        } catch (SocketException e) {
            map.put("exception_error", e.getMessage());
        }
        return map;
    }
}