package com.seeletech;

import com.alibaba.fastjson.JSON;
import com.seeletech.core.key.KeyManager;
import org.junit.Test;
import java.util.Map;
import static org.junit.Assert.assertNotNull;

public class KeyManagerTest {

    @Test
    public void testGenerateKey() {
        String resutl = KeyManager.key(1);
        Map map1 = JSON.parseObject(resutl);
        assertNotNull(((Map) map1.get("result")).get("public key"));
        assertNotNull(((Map) map1.get("result")).get("private key"));
    }
}
