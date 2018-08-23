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

package com.mxixm.fastboot.weixin.module.message.processor;

import com.mxixm.fastboot.weixin.module.media.WxMedia;
import com.mxixm.fastboot.weixin.module.media.WxMediaManager;
import com.mxixm.fastboot.weixin.module.message.WxMessageBody;
import com.mxixm.fastboot.weixin.module.message.parameter.WxMessageParameter;
import com.mxixm.fastboot.weixin.util.WxUrlUtils;
import org.springframework.core.io.FileSystemResource;

/**
 * FastBootWeixin AbstractWxGroupMediaMessageProcessor
 *
 * @author Guangshan
 * @date 2017/8/20 22:53
 * @since 0.1.2
 */
public abstract class AbstractWxMediaMessageProcessor<B extends WxMessageBody.Media> extends AbstractWxMessageBodyProcessor<B> {

    protected WxMediaManager wxMediaManager;

    public AbstractWxMediaMessageProcessor(WxMediaManager wxMediaManager) {
        this.wxMediaManager = wxMediaManager;
    }

    @Override
    protected B processBody(WxMessageParameter wxMessageParameter, B body) {
        if (body.getMediaId() == null) {
            // 优先使用path
            if (body.getMediaPath() != null) {
                String mediaId = wxMediaManager.addTempMedia(WxMedia.Type.IMAGE, new FileSystemResource(body.getMediaPath()));
                body.setMediaId(mediaId);
            } else if (body.getMediaUrl() != null) {
                String url = WxUrlUtils.mediaUrl(wxMessageParameter.getRequestUrl(), body.getMediaUrl());
                String mediaId = wxMediaManager.addTempMediaByUrl(WxMedia.Type.IMAGE, url);
                body.setMediaId(mediaId);
            }
        }
        return body;
    }


}
