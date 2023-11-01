package br.imd.ufrn.utils;

import br.imd.ufrn.model.Type;

import java.util.Random;

public class RandomGenerate {

    public static float generateCost(){
        return new Random().nextFloat() * 0.01f;
    }

    public static int generateValue(){
        return new Random().nextInt(11);
    }

    public static Type generateType(int E){

        return new Random().nextInt(101) < E ? Type.WRITING : Type.READ;
    }

}
