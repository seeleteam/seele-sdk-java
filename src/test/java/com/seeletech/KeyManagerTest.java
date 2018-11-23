package com.seeletech;

import com.seeletech.core.key.KeyManager;
import org.junit.Test;

public class KeyManagerTest {
    @Test
    public void testGenerateKey(){
        for(int i = 0 ; i < 20;i++){
            System.out.println("key"+i+":"+ KeyManager.key(2));
        }
    }
}
