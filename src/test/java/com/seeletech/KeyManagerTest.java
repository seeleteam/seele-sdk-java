package com.seeletech;

import com.seeletech.core.key.KeyManager;
import org.junit.Ignore;
import org.junit.Test;

public class KeyManagerTest {

    @Ignore
    @Test
    public void testGenerateKey(){
        for(int i = 0 ; i < 20;i++){
            System.out.println("key"+i+":"+ KeyManager.key(1));
        }
    }
}
