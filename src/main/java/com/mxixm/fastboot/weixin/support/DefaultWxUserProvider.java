/*
 * Copyright (c) 2016-2017, Guangshan (guangshan1992@qq.com) and the original author or authors.
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

import com.mxixm.fastboot.weixin.module.user.WxUser;
import com.mxixm.fastboot.weixin.module.user.WxUserProvider;
import com.mxixm.fastboot.weixin.web.WxUserManager;

/**
 * FastBootWeixin DefaultWxUserProvider
 *
 * @author Guangshan
 * @date 2017/8/5 21:54
 * @since 0.1.2
 */
public class DefaultWxUserProvider implements WxUserProvider<WxUser> {

    private WxUserManager wxUserManager;

    public DefaultWxUserProvider(WxUserManager wxUserManager) {
        this.wxUserManager = wxUserManager;
    }

    @Override
    public WxUser getUser(String fromUserName) {
        return this.wxUserManager.getWxUser(fromUserName);
    }

}
