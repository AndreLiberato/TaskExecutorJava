package br.imd.ufrn.utils;

import java.util.Random;

public class RandomGenerate {

    public static float generateCost(){
        return new Random().nextFloat() * 0.01f;
    }

    public static int generateValue(){
        return new Random().nextInt(11);
    }

}
