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

package com.mxixm.fastboot.weixin.module.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.mxixm.fastboot.weixin.module.adapter.WxJsonAdapters;

import java.util.Date;
import java.util.List;

/**
 * FastBootWeixin WxUser
 *
 * @author Guangshan
 * @date 2017/8/5 22:29
 * @since 0.1.2
 */
public class WxUser {

    /**
     * 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
     */
    @JsonProperty("subscribe")
    private Integer subscribe;

    /**
     * 用户的标识，对当前公众号唯一
     */
    @JsonProperty("openid")
    private String openId;

    /**
     * 用户的昵称
     */
    @JsonProperty("nickname")
    private String nickName;

    /**
     * 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
     */
    @JsonProperty("sex")
    private Integer sex;
    /**
     * 用户所在城市
     */
    @JsonProperty("city")
    private String city;

    /**
     * 用户所在国家
     */
    @JsonProperty("country")
    private String country;

    /**
     * 用户所在省份
     */
    @JsonProperty("province")
    private String province;

    /**
     * 用户的语言，简体中文为zh_CN
     */
    @JsonProperty("language")
    private String language;

    /**
     * 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），
     * 用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
     */
    @JsonProperty("headimgurl")
    private String headImgUrl;

    /**
     * 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
     */
    @JsonDeserialize(converter = WxJsonAdapters.WxIntDateConverter.class)
    @JsonProperty("subscribe_time")
    private Date subscribeTime;

    /**
     * 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
     */
    @JsonProperty("unionid")
    private String unionId;

    /**
     * 公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注
     */
    @JsonProperty("remark")
    private String remark;

    /**
     * 用户所在的分组ID（兼容旧的用户分组接口）
     */
    @JsonProperty("groupid")
    private Integer groupId;

    /**
     * 用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）
     */
    @JsonProperty("privilege")
    private List<String> privileges;

    /**
     * 用户被打上的标签ID列表
     * 理论上是个list，暂时偷懒不写转换器了
     */
    @JsonProperty("tagid_list")
    private List<Integer> tagIdList;

    public WxUser() {
    }

    public Integer getSubscribe() {
        return this.subscribe;
    }

    public String getOpenId() {
        return this.openId;
    }

    public String getNickName() {
        return this.nickName;
    }

    public Integer getSex() {
        return this.sex;
    }

    public String getCity() {
        return this.city;
    }

    public String getCountry() {
        return this.country;
    }

    public String getProvince() {
        return this.province;
    }

    public String getLanguage() {
        return this.language;
    }

    public String getHeadImgUrl() {
        return this.headImgUrl;
    }

    public Date getSubscribeTime() {
        return this.subscribeTime;
    }

    public String getUnionId() {
        return this.unionId;
    }

    public String getRemark() {
        return this.remark;
    }

    public Integer getGroupId() {
        return this.groupId;
    }

    public List<String> getPrivileges() {
        return this.privileges;
    }

    public List<Integer> getTagIdList() {
        return this.tagIdList;
    }

    public void setSubscribe(Integer subscribe) {
        this.subscribe = subscribe;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public void setSubscribeTime(Date subscribeTime) {
        this.subscribeTime = subscribeTime;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public void setPrivileges(List<String> privileges) {
        this.privileges = privileges;
    }

    public void setTagIdList(List<Integer> tagIdList) {
        this.tagIdList = tagIdList;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof WxUser)) {
            return false;
        }
        final WxUser other = (WxUser) o;
        if (!other.canEqual((Object) this)) {
            return false;
        }
        final Object this$subscribe = this.getSubscribe();
        final Object other$subscribe = other.getSubscribe();
        if (this$subscribe == null ? other$subscribe != null : !this$subscribe.equals(other$subscribe)) {
            return false;
        }
        final Object this$openId = this.getOpenId();
        final Object other$openId = other.getOpenId();
        if (this$openId == null ? other$openId != null : !this$openId.equals(other$openId)) {
            return false;
        }
        final Object this$nickName = this.getNickName();
        final Object other$nickName = other.getNickName();
        if (this$nickName == null ? other$nickName != null : !this$nickName.equals(other$nickName)) {
            return false;
        }
        final Object this$sex = this.getSex();
        final Object other$sex = other.getSex();
        if (this$sex == null ? other$sex != null : !this$sex.equals(other$sex)) {
            return false;
        }
        final Object this$city = this.getCity();
        final Object other$city = other.getCity();
        if (this$city == null ? other$city != null : !this$city.equals(other$city)) {
            return false;
        }
        final Object this$country = this.getCountry();
        final Object other$country = other.getCountry();
        if (this$country == null ? other$country != null : !this$country.equals(other$country)) {
            return false;
        }
        final Object this$province = this.getProvince();
        final Object other$province = other.getProvince();
        if (this$province == null ? other$province != null : !this$province.equals(other$province)) {
            return false;
        }
        final Object this$language = this.getLanguage();
        final Object other$language = other.getLanguage();
        if (this$language == null ? other$language != null : !this$language.equals(other$language)) {
            return false;
        }
        final Object this$headImgUrl = this.getHeadImgUrl();
        final Object other$headImgUrl = other.getHeadImgUrl();
        if (this$headImgUrl == null ? other$headImgUrl != null : !this$headImgUrl.equals(other$headImgUrl)) {
            return false;
        }
        final Object this$subscribeTime = this.getSubscribeTime();
        final Object other$subscribeTime = other.getSubscribeTime();
        if (this$subscribeTime == null ? other$subscribeTime != null : !this$subscribeTime.equals(other$subscribeTime)) {
            return false;
        }
        final Object this$unionId = this.getUnionId();
        final Object other$unionId = other.getUnionId();
        if (this$unionId == null ? other$unionId != null : !this$unionId.equals(other$unionId)) {
            return false;
        }
        final Object this$remark = this.getRemark();
        final Object other$remark = other.getRemark();
        if (this$remark == null ? other$remark != null : !this$remark.equals(other$remark)) {
            return false;
        }
        final Object this$groupId = this.getGroupId();
        final Object other$groupId = other.getGroupId();
        if (this$groupId == null ? other$groupId != null : !this$groupId.equals(other$groupId)) {
            return false;
        }
        final Object this$privileges = this.getPrivileges();
        final Object other$privileges = other.getPrivileges();
        if (this$privileges == null ? other$privileges != null : !this$privileges.equals(other$privileges)) {
            return false;
        }
        final Object this$tagIdList = this.getTagIdList();
        final Object other$tagIdList = other.getTagIdList();
        if (this$tagIdList == null ? other$tagIdList != null : !this$tagIdList.equals(other$tagIdList)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $subscribe = this.getSubscribe();
        result = result * PRIME + ($subscribe == null ? 43 : $subscribe.hashCode());
        final Object $openId = this.getOpenId();
        result = result * PRIME + ($openId == null ? 43 : $openId.hashCode());
        final Object $nickName = this.getNickName();
        result = result * PRIME + ($nickName == null ? 43 : $nickName.hashCode());
        final Object $sex = this.getSex();
        result = result * PRIME + ($sex == null ? 43 : $sex.hashCode());
        final Object $city = this.getCity();
        result = result * PRIME + ($city == null ? 43 : $city.hashCode());
        final Object $country = this.getCountry();
        result = result * PRIME + ($country == null ? 43 : $country.hashCode());
        final Object $province = this.getProvince();
        result = result * PRIME + ($province == null ? 43 : $province.hashCode());
        final Object $language = this.getLanguage();
        result = result * PRIME + ($language == null ? 43 : $language.hashCode());
        final Object $headImgUrl = this.getHeadImgUrl();
        result = result * PRIME + ($headImgUrl == null ? 43 : $headImgUrl.hashCode());
        final Object $subscribeTime = this.getSubscribeTime();
        result = result * PRIME + ($subscribeTime == null ? 43 : $subscribeTime.hashCode());
        final Object $unionId = this.getUnionId();
        result = result * PRIME + ($unionId == null ? 43 : $unionId.hashCode());
        final Object $remark = this.getRemark();
        result = result * PRIME + ($remark == null ? 43 : $remark.hashCode());
        final Object $groupId = this.getGroupId();
        result = result * PRIME + ($groupId == null ? 43 : $groupId.hashCode());
        final Object $privileges = this.getPrivileges();
        result = result * PRIME + ($privileges == null ? 43 : $privileges.hashCode());
        final Object $tagIdList = this.getTagIdList();
        result = result * PRIME + ($tagIdList == null ? 43 : $tagIdList.hashCode());
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof WxUser;
    }

    @Override
    public String toString() {
        return "com.mxixm.fastboot.weixin.module.user.WxUser(subscribe=" + this.getSubscribe() + ", openId=" + this.getOpenId() + ", nickName=" + this.getNickName() + ", sex=" + this.getSex() + ", city=" + this.getCity() + ", country=" + this.getCountry() + ", province=" + this.getProvince() + ", language=" + this.getLanguage() + ", headImgUrl=" + this.getHeadImgUrl() + ", subscribeTime=" + this.getSubscribeTime() + ", unionId=" + this.getUnionId() + ", remark=" + this.getRemark() + ", groupId=" + this.getGroupId() + ", privileges=" + this.getPrivileges() + ", tagIdList=" + this.getTagIdList() + ")";
    }
}
