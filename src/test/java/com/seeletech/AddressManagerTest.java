package com.seeletech;

import com.seeletech.core.address.AddressManager;
import org.junit.Test;
import org.spongycastle.util.encoders.Hex;
import static org.junit.Assert.assertEquals;

public class AddressManagerTest {
    private String address = "8be23786d29d615a630ad3f1febb84a4d5acb3a1";
    @Test
    public void testGetAddress(){
        String pubString = "040947751e3022ecf3016be03ec77ab0ce3c2662b4843898cb068d74f698ccc8ad75aa17564ae80a20bb044ee7a6d903e8e8df624b089c95d66a0570f051e5a05b";
        byte[] pubKey = Hex.decode(pubString);
        assertEquals(address,(Hex.toHexString(AddressManager.getAddress(pubKey))));
    }
}
