package com.seeletech;

import com.alibaba.fastjson.JSON;
import com.seeletech.core.address.AddressManager;
import org.junit.Test;
import org.spongycastle.util.encoders.Hex;
import java.util.Map;
import static org.junit.Assert.*;

public class AddressManagerTest {

    @Test
    public void testGetAddress() {
        String address = "8be23786d29d615a630ad3f1febb84a4d5acb3a1";
        String pubString = "040947751e3022ecf3016be03ec77ab0ce3c2662b4843898cb068d74f698ccc8ad75aa17564ae80a20bb044ee7a6d903e8e8df624b089c95d66a0570f051e5a05b";
        byte[] pubKey = Hex.decode(pubString);
        String address1 = Hex.toHexString(AddressManager.getAddress(pubKey));
        assertEquals(address, address1);
    }

    @Test
    public void testGetBalance() {
        String jsonResult = AddressManager.getBalance("0xe95d99fec90954eb8f6f899c188aef5caa20d501", "http://104.218.164.169:8037");
        Map mapResult = JSON.parseObject(jsonResult);
        assertNotNull(((Map) ((Map) mapResult.get("result")).get("result")).get("Balance"));
    }

    @Test
    public void testGetBalanceInvalidAddressLength() {
        String actualResult = "{\"errMsg\":\"invalid argument 0: invalid address length 2, expected length is 20\"}";
        String jsonResult = AddressManager.getBalance("0xe95d", "http://104.218.164.169:8037");
        assertEquals(actualResult,jsonResult);
    }

    @Test
    public void testGetBalanceOddLength() {
        String actualResult = "{\"errMsg\":\"invalid argument 0: hex string of odd length\"}";
        String jsonResult = AddressManager.getBalance("0xe95", "http://104.218.164.169:8037");
        assertEquals(actualResult,jsonResult);
    }

    @Test
    public void testGetBalanceSyntaxCharaceter() {
        String actualResult = "{\"errMsg\":\"invalid argument 0: invalid hex string\"}";
        String jsonResult = AddressManager.getBalance("0xe95d99fec90954eb8f6f899c188aef5caa20d50-", "http://104.218.164.169:8037");
        assertEquals(actualResult,jsonResult);
    }

    @Test
    public void testValidateInvalid() {
        String[] types = new String[]{"0", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "A", "B", "C", "D", "E", "F"};
        for(String type : types) {
            String addr = "4c10f2cd2159bb432094e3be7e17904c2b4aeb2"+type;
            assertFalse(AddressManager.addressValidate(addr));
        }
    }

    @Test
    public void testValidate() {
        String addr = "4c10f2cd2159bb432094e3be7e17904c2b4aeb21";
        assertTrue(AddressManager.addressValidate(addr));
    }
}
