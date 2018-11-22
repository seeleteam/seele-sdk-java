package com.seeletech.util.bean;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BeanUtil {
    /**
     *  获取javabean的字段
     * @param o 对象
     * @return 字段数组
     */
    public static String[] getField(Object o) {
        String[] bxgs = null;// 不需要修改字段
        try {
            if (o != null) {
                String bxg = "";
                Map sss = org.apache.commons.beanutils.BeanUtils.describe(o);
                Iterator iter = sss.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    /*
                     * //key 字段名称 val字段值 Object key = entry.getKey(); Object val
                     * = entry.getValue();
                     */
                    if (entry.getKey() != null && !"class".equals(entry.getKey())) {
                        if(null != entry.getValue()){
                            bxg += entry.getValue().toString() + ",";
                        }
                    }

                }

                if (bxg.length() > 1) {
                    bxgs = bxg.substring(0, bxg.length() - 1).split(",");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return bxgs;
    }

    public static String[] getFieldForSort(Object o) {
        String[] bxgs = null ;// 不需要修改字段
        try {
            if (o != null) {
                Map sss = org.apache.commons.beanutils.BeanUtils.describe(o);
                Iterator iter = sss.entrySet().iterator();
                bxgs = new String[sss.size()-1];
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    /*
                     * //key 字段名称 val字段值 Object key = entry.getKey(); Object val
                     * = entry.getValue();
                     */
                    if (entry.getKey() != null && !"class".equals(entry.getKey())) {
                        if("from".equals(entry.getKey())){
                            bxgs[0] = (String)entry.getValue();
                        }else if("to".equals(entry.getKey())){
                            bxgs[1] = (String)entry.getValue();
                        }else if("amount".equals(entry.getKey())){
                            bxgs[2] = (String)entry.getValue();
                        }else if("accountNonce".equals(entry.getKey())){
                            bxgs[3] = (String)entry.getValue();
                        }else if("gasPrice".equals(entry.getKey())){
                            bxgs[4] = (String)entry.getValue();
                        }else if("gasLimit".equals(entry.getKey())){
                            bxgs[5] = (String)entry.getValue();
                        }else if("timestamp".equals(entry.getKey())){
                            bxgs[6] = (String)entry.getValue();
                        }else if("payload".equals(entry.getKey())){
                            bxgs[7] = (String)entry.getValue();
                        }
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return bxgs;
    }

    public static Map<String, Object> objectToMap(Object obj) throws Exception {
        if(obj == null){
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();

        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(obj));
        }
        return map;
    }
}
