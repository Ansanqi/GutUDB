/*
Copyright [2020] [https://www.stylefeng.cn]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

Guns采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：

1.请不要删除和修改根目录下的LICENSE文件。
2.请不要删除和修改Guns源码头部的版权声明。
3.请保留源码和相关描述文件的项目出处，作者声明等。
4.分发源码时候，请注明软件出处 https://gitee.com/stylefeng/guns-separation
5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/stylefeng/guns-separation
6.若您的项目无法满足以上几点，可申请商业授权，获取Guns商业授权许可，请在官网购买授权，地址为 https://www.stylefeng.cn
 */
package com.lplb.sys.core.cache.base;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.lplb.core.cache.CacheOperator;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.lplb.core.consts.SymbolConstant.ASTERISK;

/**
 * 基于redis的缓存封装
 *
 * @author stylefeng
 * @date 2020/7/9 10:09
 */
public abstract class AbstractRedisCacheOperator<T> implements CacheOperator<T> {

    @Resource
    private final RedisTemplate<String, T> redisTemplate;

    public AbstractRedisCacheOperator(RedisTemplate<String, T> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void put(String key, T value) {
        redisTemplate.boundValueOps(getCommonKeyPrefix() + key).set(value);
    }

    @Override
    public void put(String key, T value, Long timeoutSeconds) {
        redisTemplate.boundValueOps(getCommonKeyPrefix() + key).set(value, timeoutSeconds, TimeUnit.SECONDS);
    }

    @Override
    public T get(String key) {
        return redisTemplate.boundValueOps(getCommonKeyPrefix() + key).get();
    }

    @Override
    public void remove(String... key) {
        ArrayList<String> keys = CollectionUtil.toList(key);
        List<String> withPrefixKeys = keys.stream().map(i -> getCommonKeyPrefix() + i).collect(Collectors.toList());
        redisTemplate.delete(withPrefixKeys);
    }

    @Override
    public Collection<String> getAllKeys() {
        Set<String> keys = redisTemplate.keys(getCommonKeyPrefix() + ASTERISK);
        if (ObjectUtil.isNotEmpty(keys)) {
            // 去掉缓存key的common prefix前缀
            return keys.stream().map(key -> StrUtil.removePrefix(key, getCommonKeyPrefix())).collect(Collectors.toSet());
        } else {
            return CollUtil.newHashSet();
        }
    }

    @Override
    public Collection<T> getAllValues() {
        Set<String> keys = redisTemplate.keys(getCommonKeyPrefix() + ASTERISK);
        if (ObjectUtil.isNotEmpty(keys)) {
            return redisTemplate.opsForValue().multiGet(keys);
        } else {
            return CollUtil.newArrayList();
        }
    }

    @Override
    public Map<String, T> getAllKeyValues() {
        Collection<String> allKeys = this.getAllKeys();
        HashMap<String, T> results = CollectionUtil.newHashMap();
        for (String key : allKeys) {
            results.put(key, this.get(key));
        }
        return results;
    }
}
