package com.bcx.demo;

import java.util.function.Function;

/**
 * Created by Wen Tiehu on 2017/8/7.
 */
public class Demo {
    public static void main (String [] args){
        int incr = 20;
        int myNumber = 10;

        FunctionDemo.modifyTheValue(myNumber, val -> val + incr);

        myNumber = 15;
        FunctionDemo.modifyTheValue(myNumber, val -> val * 10);

        FunctionDemo.modifyTheValue(myNumber, val -> val - 100);

        FunctionDemo.modifyTheValue(myNumber, val -> "somestring".length() + val - 100);

    }
}



class FunctionDemo {
    //API which accepts an implementation of
    //Function interface
    public static void modifyTheValue(int valueToBeOperated, Function<Integer, Integer> function) {
        int newValue = function.apply(valueToBeOperated);
        System.out.println(newValue);

    }
}