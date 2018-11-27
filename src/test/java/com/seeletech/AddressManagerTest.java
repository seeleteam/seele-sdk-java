package com.seeletech;

import com.alibaba.fastjson.JSON;
import com.seeletech.core.address.AddressManager;
import org.junit.Test;
import org.spongycastle.util.encoders.Hex;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AddressManagerTest {

    private String address = "cd26fc8ee810bbf514075bf966f1d949c3876b71";

    @Test
    public void testGetAddress() {
        String address1 = Hex.toHexString(AddressManager.getAddress("0xd6cfa19439827666be5bdc2d169538af4693cb81".getBytes()));
        assertEquals(address, address1);
    }

    @Test
    public void testGetBalance() {
        String jsonResult = AddressManager.getBalance("0xe95d99fec90954eb8f6f899c188aef5caa20d501","http://117.50.20.225:8037");
        Map mapResult = JSON.parseObject(jsonResult);
        assertNotNull(((Map)((Map)mapResult.get("result")).get("result")).get("Balance"));
    }
}
