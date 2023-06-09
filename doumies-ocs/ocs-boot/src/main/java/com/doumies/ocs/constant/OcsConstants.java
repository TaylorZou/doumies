package com.doumies.ocs.constant;

/**
 * @author Taylor
 * @date 2023-03-01
 */
public interface OcsConstants {

    String CART_PREFIX = "cart:";

    String ORDER_TOKEN_PREFIX = "order:token:";

    String ORDER_SN_PREFIX = "order:sn:";

    /**
     * 释放锁lua脚本
     */
    String RELEASE_LOCK_LUA_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";


}
