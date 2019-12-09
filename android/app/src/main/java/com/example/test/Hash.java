package com.example.test;

import android.content.Context;
import android.widget.Toast;

import java.security.MessageDigest;

public class Hash {
    public String Hash(Context context, String data)
    {
        try
        {
            MessageDigest program = MessageDigest.getInstance("MD5");
            program.update(data.getBytes());
            byte[] bytes = program.digest();
            StringBuilder builder = new StringBuilder();
            for(int i = 0;i< bytes.length;i++)
            {
                builder.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            return builder.toString();
        }
        catch (Exception e)
        {
            Toast.makeText(context,"System error",Toast.LENGTH_LONG).show();
        }
        return "";
    }
}
