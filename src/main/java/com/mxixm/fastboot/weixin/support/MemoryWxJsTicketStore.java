/*
 * Copyright (c) 2016-2018, Guangshan (guangshan1992@qq.com) and the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mxixm.fastboot.weixin.support;

import com.mxixm.fastboot.weixin.module.credential.AbstractMemoryCredentialStore;
import com.mxixm.fastboot.weixin.module.credential.WxJsTicketStore;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.invoke.MethodHandles;

/**
 * FastBootWeixin MemoryWxTokenStore
 *
 * @author Guangshan
 * @date 2018-5-7 23:35:38
 * @since 0.6.0
 */
public class MemoryWxJsTicketStore extends AbstractMemoryCredentialStore implements WxJsTicketStore {

    private static final Log logger = LogFactory.getLog(MethodHandles.lookup().lookupClass());

    /**
     * ticket值
     */
    private String ticket;

    /**
     * 过期时间
     */
    private long expires;

    /**
     * 获取Token
     *
     * @return the result
     */
    @Override
    public String get() {
        return ticket;
    }

    /**
     * 设置ticket
     *
     * @param ticket
     * @param expires
     */
    @Override
    public void store(String ticket, long expires) {
        this.ticket = ticket;
        this.expires = expires;
    }

    /**
     * 获取过期时间
     *
     * @return the result
     */
    @Override
    public long expires() {
        return expires;
    }

}