package reen.com.firebaseapp;


//bit.ly/2JUIsWu
import android.os.AsyncTask;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Network {
    public  boolean isInternetAvailable() {
        try {
            String ipAddr=new  NetTask().execute("www.google.com").get();
            return !ipAddr.equals("");
        } catch (Exception e) {
            return false;
        }
    }

    public class NetTask extends AsyncTask<String, Integer, String>
    {
        @Override
        protected String doInBackground(String... params)
        {
            InetAddress addr = null;
            try
            {
                addr = InetAddress.getByName(params[0]);
            }

            catch (UnknownHostException e)
            {
                e.printStackTrace();
            }
            return addr.getHostAddress();
        }
    }
}
