package com.mxixm.fastboot.weixin.module.pay;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

public class WxPay {

	
	@XmlRootElement(name = "xml")
	@XmlAccessorType(XmlAccessType.NONE)
	public static class WxOrder {

		@XmlElement(name = "appid", required = true)
		private String appId;
		@XmlElement(name = "mch_id", required = true)
		private String mchId ;
		@XmlElement(name = "device_info")
		private String deviceInfo ;
		@XmlElement(name = "nonce_str" , required = true)
		private String nonceStr ;
		@XmlElement(name = "sign_type")
		private String signType ;
		@XmlElement(name = "body", required = true)
		private String body ;
		@XmlElement(name = "detail")
		private String detail ;
		@XmlElement(name = "attach")
		private String attach ;
		@XmlElement(name = "out_trade_no", required = true)
		private String outTradeNo ;
		@XmlElement(name = "fee_type")
		private String feeType = "CNY";
		@XmlElement(name = "total_fee", required = true)
		private Integer totalFee ;
		@XmlElement(name = "spbill_create_ip", required = true)
		private String spbillCreateIp ;
		@XmlElement(name = "time_start")
		private String  timeStart; // yyyyMMddHHmmss
		@XmlElement(name = "time_expire")
		private String timeExpire ; // yyyyMMddHHmmss
		@XmlElement(name = "goods_tag")
		private String goodsTag ; 
		@XmlElement(name = "notify_url" , required = true)
		private String notifyUrl ; 
		@XmlElement(name = "trade_type" , required = true)
		private String tradeType ; // JSAPI 公众号支付 NATIVE 扫码支付 APP APP支付
		@XmlElement(name = "product_id" )
		private String productId ; 
		@XmlElement(name = "limit_pay" )
		private String limitPay ; 
		@XmlElement(name = "openid" )
		private String openId ; 
		@XmlElement(name = "scene_info" )
		private String sceneInfo ;
		public String getAppId() {
			return appId;
		}
		public void setAppId(String appId) {
			this.appId = appId;
		}
		public String getMchId() {
			return mchId;
		}
		public void setMchId(String mchId) {
			this.mchId = mchId;
		}
		public String getDeviceInfo() {
			return deviceInfo;
		}
		public void setDeviceInfo(String deviceInfo) {
			this.deviceInfo = deviceInfo;
		}
		public String getNonceStr() {
			return nonceStr;
		}
		public void setNonceStr(String nonceStr) {
			this.nonceStr = nonceStr;
		}
		public String getSignType() {
			return signType;
		}
		public void setSignType(String signType) {
			this.signType = signType;
		}
		public String getBody() {
			return body;
		}
		public void setBody(String body) {
			this.body = body;
		}
		public String getDetail() {
			return detail;
		}
		public void setDetail(String detail) {
			this.detail = detail;
		}
		public String getAttach() {
			return attach;
		}
		public void setAttach(String attach) {
			this.attach = attach;
		}
		public String getOutTradeNo() {
			return outTradeNo;
		}
		public void setOutTradeNo(String outTradeNo) {
			this.outTradeNo = outTradeNo;
		}
		public String getFeeType() {
			return feeType;
		}
		public void setFeeType(String feeType) {
			this.feeType = feeType;
		}
		public Integer getTotalFee() {
			return totalFee;
		}
		public void setTotalFee(Integer totalFee) {
			this.totalFee = totalFee;
		}
		public String getSpbillCreateIp() {
			return spbillCreateIp;
		}
		public void setSpbillCreateIp(String spbillCreateIp) {
			this.spbillCreateIp = spbillCreateIp;
		}
		public String getTimeStart() {
			return timeStart;
		}
		public void setTimeStart(String timeStart) {
			this.timeStart = timeStart;
		}
		public String getTimeExpire() {
			return timeExpire;
		}
		public void setTimeExpire(String timeExpire) {
			this.timeExpire = timeExpire;
		}
		public String getGoodsTag() {
			return goodsTag;
		}
		public void setGoodsTag(String goodsTag) {
			this.goodsTag = goodsTag;
		}
		public String getNotifyUrl() {
			return notifyUrl;
		}
		public void setNotifyUrl(String notifyUrl) {
			this.notifyUrl = notifyUrl;
		}
		public String getTradeType() {
			return tradeType;
		}
		public void setTradeType(String tradeType) {
			this.tradeType = tradeType;
		}
		public String getProductId() {
			return productId;
		}
		public void setProductId(String productId) {
			this.productId = productId;
		}
		public String getOpenId() {
			return openId;
		}
		public String getLimitPay() {
			return limitPay;
		}
		public void setLimitPay(String limitPay) {
			this.limitPay = limitPay;
		}
		public void setOpenId(String openId) {
			this.openId = openId;
		}
		public String getSceneInfo() {
			return sceneInfo;
		}
		public void setSceneInfo(String sceneInfo) {
			this.sceneInfo = sceneInfo;
		} 
		
	}
	
	@XmlRootElement(name = "xml")
	@XmlAccessorType(XmlAccessType.NONE)
	public static class WxOrderResult {
		
		@XmlElement(name = "return_code" )
		private String returnCode; // SUCCESS/FAIL
		@XmlElement(name = "return_msg" )
		private String returnMsg;
		
		@XmlElement(name = "appid", required = true)
		private String appId;
		@XmlElement(name = "mch_id", required = true)
		private String mchId ;
		@XmlElement(name = "device_info")
		private String deviceInfo ;
		@XmlElement(name = "nonce_str" , required = true)
		private String nonceStr ;
		@XmlElement(name = "sign" , required = true)
		private String sign ;
		@XmlElement(name = "result_code" , required = true)
		private String resultCode;   // SUCCESS/FAIL
		@XmlElement(name = "err_code" )
		private String errCode;
		@XmlElement(name = "err_code_des" )
		private String errCodeDes ;
		@XmlElement(name = "trade_type" , required = true)
		private String tradeType ; // JSAPI 公众号支付 NATIVE 扫码支付 APP APP支付
		@XmlElement(name = "prepay_id" , required = true)
		private String prepayId ;
		@XmlElement(name = "code_url" )
		private String codeUrl;
		
		/*     =====js支付需要参数 start======   */
		private String timeStamp ; // 前端支付使用 timeStamp ， 例如 1414561699
		private String _package; // 	统一下单接口返回的prepay_id参数值，提交格式如：prepay_id=***
		private String signType ;  // 签名类型，默认为MD5，支持HMAC-SHA256和MD5。注意此处需与统一下单的签名类型一致
		private String paySign ;  // 支付签名
		/*     =====js支付需要参数  end======   */
		
		public String getReturnCode() {
			return returnCode;
		}
		public void setReturnCode(String returnCode) {
			this.returnCode = returnCode;
		}
		public String getReturnMsg() {
			return returnMsg;
		}
		public void setReturnMsg(String returnMsg) {
			this.returnMsg = returnMsg;
		}
		public String getAppId() {
			return appId;
		}
		public void setAppId(String appId) {
			this.appId = appId;
		}
		public String getMchId() {
			return mchId;
		}
		public void setMchId(String mchId) {
			this.mchId = mchId;
		}
		public String getDeviceInfo() {
			return deviceInfo;
		}
		public void setDeviceInfo(String deviceInfo) {
			this.deviceInfo = deviceInfo;
		}
		public String getNonceStr() {
			return nonceStr;
		}
		public void setNonceStr(String nonceStr) {
			this.nonceStr = nonceStr;
		}
		public String getSign() {
			return sign;
		}
		public void setSign(String sign) {
			this.sign = sign;
		}
		public String getResultCode() {
			return resultCode;
		}
		public void setResultCode(String resultCode) {
			this.resultCode = resultCode;
		}
		public String getErrCode() {
			return errCode;
		}
		public void setErrCode(String errCode) {
			this.errCode = errCode;
		}
		public String getErrCodeDes() {
			return errCodeDes;
		}
		public void setErrCodeDes(String errCodeDes) {
			this.errCodeDes = errCodeDes;
		}
		public String getTradeType() {
			return tradeType;
		}
		public void setTradeType(String tradeType) {
			this.tradeType = tradeType;
		}
		public String getPrepayId() {
			return prepayId;
		}
		public void setPrepayId(String prepayId) {
			this.prepayId = prepayId;
		}
		public String getCodeUrl() {
			return codeUrl;
		}
		public void setCodeUrl(String codeUrl) {
			this.codeUrl = codeUrl;
		}
		public String getTimeStamp() {
			return timeStamp;
		}
		public void setTimeStamp(String timeStamp) {
			this.timeStamp = timeStamp;
		}
		public String get_package() {
			return _package;
		}
		public void set_package(String _package) {
			this._package = _package;
		}
		public String getSignType() {
			return signType;
		}
		public void setSignType(String signType) {
			this.signType = signType;
		}
		public String getPaySign() {
			return paySign;
		}
		public void setPaySign(String paySign) {
			this.paySign = paySign;
		}
		
	}
}
