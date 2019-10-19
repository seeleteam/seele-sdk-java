package com.seeletech;

import com.alibaba.fastjson.JSON;
import com.seeletech.core.seele.SeeleJ;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SeeleJTest {

    @Test
    public void testGetInfoCmd() {
        SeeleJ seeleJ = new SeeleJ("http://117.50.82.193:8037");
        String jsonResult = seeleJ.execCmd("getInfo",new ArrayList());
        Map mapResult = JSON.parseObject(jsonResult);
        assertEquals(((Map)mapResult.get("result")).get("Shard"),1);
    }
}
