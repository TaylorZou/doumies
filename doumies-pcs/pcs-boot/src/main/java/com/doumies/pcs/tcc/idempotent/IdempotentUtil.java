package com.doumies.pcs.tcc.idempotent;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

/**
 * @Author Taylor
 * @Description TCC幂等工具类
 * @Date 2023/2/28 20:38
 **/
public class IdempotentUtil {

    private static Table<Class<?>, String, Long> map= HashBasedTable.create();

    public static void addMarker(Class<?> clazz, String xid, Long marker){
        map.put(clazz,xid,marker);
    }

    public static Long getMarker(Class<?> clazz, String xid){
        return map.get(clazz,xid);
    }

    public static void removeMarker(Class<?> clazz, String xid){
        map.remove(clazz,xid);
    }
}