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

package com.mxixm.fastboot.weixin.test.old.media;

import com.mxixm.fastboot.weixin.exception.WxApiException;
import com.mxixm.fastboot.weixin.module.media.WxMedia;
import com.mxixm.fastboot.weixin.module.media.WxMediaResource;
import com.mxixm.fastboot.weixin.service.WxApiService;
import com.mxixm.fastboot.weixin.service.invoker.executor.WxApiTemplate;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;

/**
 * FastBootWeixin WxMediaManager
 *
 * @author Guangshan
 * @date 2017/8/12 21:05
 * @since 0.1.2
 */
public class WxMediaManager {

    private WxApiService wxApiService;

    private WxMediaStore wxMediaStore;

    private WxApiTemplate wxApiTemplate;

    public WxMediaManager(WxApiService wxApiService, WxApiTemplate wxApiTemplate, WxMediaStore wxMediaStore) {
        this.wxApiService = wxApiService;
        this.wxApiTemplate = wxApiTemplate;
        this.wxMediaStore = wxMediaStore;
    }

    public String addTempMedia(WxMedia.Type type, File media) {
        String mediaId = wxMediaStore.findTempMediaIdByFile(media);
        if (mediaId != null) {
            return mediaId;
        }
        WxMedia.TempMediaResult result = wxApiService.uploadTempMedia(type, new FileSystemResource(media));
        wxMediaStore.storeFileToTempMedia(type, media, result);
        return result.getMediaId();
    }

    public String addTempMediaByUrl(WxMedia.Type type, String url) {
        String mediaId = wxMediaStore.findTempMediaIdByUrl(url);
        if (mediaId != null) {
            return mediaId;
        }
        Resource resource = wxApiTemplate.getForObject(url, Resource.class);
        WxMedia.TempMediaResult result = wxApiService.uploadTempMedia(type, resource);
        wxMediaStore.storeUrlToTempMedia(type, url, result);
        return result.getMediaId();
    }

    public String addMedia(WxMedia.Type type, File media) {
        String mediaId = wxMediaStore.findMediaIdByFile(media);
        if (mediaId != null) {
            return mediaId;
        }
        WxMedia.MediaResult result = wxApiService.uploadMedia(type, new FileSystemResource(media), null);
        wxMediaStore.storeFileToMedia(type, media, result);
        return result.getMediaId();
    }

    /**
     * 本来应该再给get加个缓存的，但是我又偷懒了，get的时候不加缓存了，直接拿微信api的结果吧
     *
     * @param video
     * @param description
     * @return the result
     */
    public String addVideoMedia(File video, WxMedia.Video description) {
        String mediaId = wxMediaStore.findMediaIdByFile(video);
        if (mediaId != null) {
            return mediaId;
        }
        WxMedia.MediaResult result = wxApiService.uploadMedia(WxMedia.Type.VIDEO, new FileSystemResource(video), description);
        wxMediaStore.storeFileToMedia(WxMedia.Type.VIDEO, video, result);
        return result.getMediaId();
    }

    public WxMedia.Video getVideoMedia(String mediaId) {
        return wxApiService.getVideoMedia(WxMedia.of(mediaId));
    }

    public Resource getTempMedia(String mediaId) {
        File tempMediaFile = wxMediaStore.findFileByTempMediaId(mediaId);
        if (tempMediaFile != null) {
            return new WxMediaResource(tempMediaFile);
        }
        WxMediaResource wxMediaResource = wxApiService.getTempMedia(mediaId);
        try {
            wxMediaStore.storeTempMediaToFile(mediaId, wxMediaResource);
        } catch (IOException e) {
            throw new WxApiException("获取媒体文件失败", e);
        }
        return wxMediaResource;
    }

    public Resource getMedia(String mediaId) {
        File mediaFile = wxMediaStore.findFileByMediaId(mediaId);
        if (mediaFile != null) {
            return new WxMediaResource(mediaFile);
        }
        WxMediaResource wxMediaResource = wxApiService.getMedia(WxMedia.of(mediaId));
        try {
            wxMediaStore.storeMediaToFile(mediaId, wxMediaResource);
        } catch (IOException e) {
            throw new WxApiException("获取媒体文件失败", e);
        }
        return wxMediaResource;
    }

    public String addImg(File file) {
        String url = wxMediaStore.findUrlByFile(file);
        if (url != null) {
            return url;
        }
        WxMedia.ImageResult imageResult = wxApiService.uploadImg(new FileSystemResource(file));
        wxMediaStore.storeFileToUrl(file, imageResult);
        return imageResult.getUrl();
    }

    public Resource getImg(String url) {
        File file = wxMediaStore.findFileByUrl(url);
        if (file != null) {
            return new WxMediaResource(file);
        } else {
            // 如果为空的时候是否可以直接通过url获取source呢？
            return null;
        }
    }

    public String addImgByUrl(String imgUrl) {
        String url = wxMediaStore.findUrlByUrl(imgUrl);
        if (url != null) {
            return url;
        }
        Resource resource = wxApiTemplate.getForObject(imgUrl, Resource.class);
        WxMedia.ImageResult result = wxApiService.uploadImg(resource);
        wxMediaStore.storeUrlToUrl(imgUrl, result);
        return result.getUrl();
    }

    public String getImgByUrl(String imgUrl) {
        return wxMediaStore.findUrlByUrl(imgUrl);
    }

    /**
     * 这个怎么存呢？是否有必要存一个映射关系？
     *
     * @param news
     * @return the result
     */
    public WxMedia.NewsResult storeNews(WxMedia.News news) {
        return wxApiService.addNews(news);
    }

    /**
     * 只返回一个json结果，不管了，如果有错的话会抛出异常的
     *
     * @param news
     */
    public void updateNews(WxMedia.New news) {
        wxApiService.updateNews(news);
    }

    /**
     * 主要限制是同一个接口相同的参数可能得到的是不同的结果
     *
     * @param mediaId
     * @return the result
     */
    public WxMedia.News getNews(String mediaId) {
        return wxApiService.getNewsMedia(WxMedia.of(mediaId));
    }

    public void delMedia(String mediaId) {
        wxApiService.delMedia(WxMedia.of(mediaId));
    }

    public WxMedia.Count getMediaCount() {
        return wxApiService.getMediaCount();
    }

}
