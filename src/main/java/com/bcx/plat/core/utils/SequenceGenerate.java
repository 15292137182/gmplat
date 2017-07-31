package com.bcx.plat.core.utils;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Went on 2017/7/30.
 */

public class SequenceGenerate {
    public static void main(String [] args) throws IOException {
        
        Properties properties = new Properties();
        properties.load(SequenceGenerate.class.getClassLoader().getResourceAsStream("properties/sequence.properties"));
        Object sss = properties.setProperty("qqq","1234556");
        System.out.println(sss);
        String qqq = properties.getProperty("qqq");
        System.out.println(qqq);
    }



}
