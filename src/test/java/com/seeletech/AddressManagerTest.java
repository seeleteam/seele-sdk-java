package com.seeletech;

import com.seeletech.core.address.AddressManager;
import org.junit.Test;
import org.spongycastle.util.encoders.Hex;
import static org.junit.Assert.assertEquals;

public class AddressManagerTest {

    private String address = "cd26fc8ee810bbf514075bf966f1d949c3876b71";

    @Test
    public void testGetAddress() {
        String address1 = Hex.toHexString(AddressManager.getAddress("0xd6cfa19439827666be5bdc2d169538af4693cb81".getBytes()));
        assertEquals(address, address1);
    }
}
