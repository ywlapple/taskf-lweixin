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

package com.mxixm.fastboot.weixin.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.invoke.MethodHandles;

/**
 * FastBootWeixin WxAccessTokenException
 *
 * @author Guangshan
 * @date 2017/7/23 23:38
 * @since 0.1.2
 */
public class WxAccessTokenException extends WxApiResultException {

    private static final Log logger = LogFactory.getLog(MethodHandles.lookup().lookupClass());

    public WxAccessTokenException(int code, String errorMessage) {
        super(code, errorMessage);
    }

    public WxAccessTokenException(String errorResult) {
        super(errorResult);
    }

}
