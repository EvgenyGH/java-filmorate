package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

public class Tester {
    private enum GENERES {G1, G2, G3;

        @Override
        public String toString() {
            switch(this){
                case G1: return "G-1";
                case G2: return "G-2";
                case G3: return "G-3";
                default: return "Error";
            }
        }
    }

    @Test
    public void testSets () {
        System.out.println(GENERES.G1);
        HashSet<GENERES> set = new HashSet<>();
        set.add(GENERES.G2);
        set.add(GENERES.G3);
        System.out.println(set);
    }
}
