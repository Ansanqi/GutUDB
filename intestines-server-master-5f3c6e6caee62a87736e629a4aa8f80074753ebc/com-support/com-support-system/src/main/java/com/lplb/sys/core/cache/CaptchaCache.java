package com.lplb.sys.core.cache;

import com.lplb.sys.core.cache.base.AbstractRedisCacheOperator;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;

/**
 *   @author Ray-zy
 *   @since 2022/3/24 13:33
 **/
public class CaptchaCache extends AbstractRedisCacheOperator<Map<String,Object>> {


    /**
     * 登录用户缓存前缀
     */
    public static final String CAPTCHA_CACHE_PREFIX = "CAPTCHA:";

    public CaptchaCache(RedisTemplate<String, Map<String,Object>> timedCache) {
        super(timedCache);
    }


    @Override
    public String getCommonKeyPrefix() {
        return CAPTCHA_CACHE_PREFIX;
    }
}
