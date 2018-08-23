package com.mxixm.fastboot.weixin.module.pay;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

public class WxPayQuery {

	
	@XmlRootElement(name = "xml")
	@XmlAccessorType(XmlAccessType.NONE)
	public static class WxOrderQuery {

		@XmlElement(name = "appid", required = true)
		private String appId;
		@XmlElement(name = "mch_id", required = true)
		private String mchId ;
		@XmlElement(name = "transaction_id" )
		private String transactionId  ; //微信订单号 ， 建议优先使用
		@XmlElement(name = "out_trade_no")
		private String outTradeNo ;
		@XmlElement(name = "nonce_str" , required = true)
		private String nonceStr ;		
		@XmlElement(name = "sign_type")
		private String signType ;

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
		public String getOutTradeNo() {
			return outTradeNo;
		}
		public void setOutTradeNo(String outTradeNo) {
			this.outTradeNo = outTradeNo;
		}
		public String getTransactionId() {
			return transactionId;
		}
		public void setTransactionId(String transactionId) {
			this.transactionId = transactionId;
		}
	}
	
	@XmlRootElement(name = "xml")
	@XmlAccessorType(XmlAccessType.NONE)
	public static class WxOrderQueryResult {
		
		@XmlElement(name = "return_code" )
		private String returnCode; // SUCCESS/FAIL
		@XmlElement(name = "return_msg" )
		private String returnMsg;
		
		@XmlElement(name = "appid", required = true)
		private String appId;
		@XmlElement(name = "mch_id", required = true)
		private String mchId ;
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
		@XmlElement(name = "device_info" )
		private String deviceInfo ;
		@XmlElement(name = "openid" )
		private String openId ;
		@XmlElement(name = "is_subscribe" )
		private String isSubscribe ; // Y yes N no
		@XmlElement(name = "trade_type" , required = true)
		private String tradeType ; // JSAPI 公众号支付 NATIVE 扫码支付 APP APP支付
		/**
		 *  SUCCESS—支付成功 
		 *	REFUND—转入退款 
		 *	NOTPAY—未支付 
		 *	CLOSED—已关闭 
		 *	REVOKED—已撤销（刷卡支付） 
		 *	USERPAYING--用户支付中 
		 *	PAYERROR--支付失败(其他原因，如银行返回失败)
		 *	支付状态机请见下单API页面
		 */
		@XmlElement(name = "trade_state" , required = true)
		private String tradeState ; //
		@XmlElement(name = "bank_type" , required = true)
		private String bankType ; // 付款银行 ， 例如 CMC
		@XmlElement(name = "total_fee" , required = true)
		private Integer totalFee ; // 订单总金额，单位为分 
		@XmlElement(name = "settlement_total_fee" )
		private Integer settlementTotalFee ; // 当订单使用了免充值型优惠券后返回该参数，应结订单金额=订单金额-免充值优惠券金额
		@XmlElement(name = "fee_type" )
		private String feeType ="CNY" ; // 货币类型，符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
		@XmlElement(name = "cash_fee" , required = true)
		private Integer cashFee ; // 
		@XmlElement(name = "cash_fee_type" )
		private String cashFeeType = "CNY" ; //
		@XmlElement(name = "coupon_fee" )
		private Integer coupon_fee ;
		@XmlElement(name = "coupon_count" )
		private Integer couponCount ;
		@XmlElement(name = "coupon_type_$n" )
		private String couponType ; // ASH--充值代金券   NO_CASH---非充值优惠券  开通免充值券功能，并且订单使用了优惠券后有返回（取值：CASH、NO_CASH）。$n为下标,从0开始编号，举例：coupon_type_$0
		@XmlElement(name = "coupon_id_$n" )
		private String couponId; // 
		@XmlElement(name = "coupon_fee_$n" )
		private Integer couponFee ; //
		@XmlElement(name = "transaction_id" )
		private String transactionId ; 
		@XmlElement(name = "out_trade_no" )
		private String outTradeNo ;
		@XmlElement(name = "attach" )
		private String attach ;
		@XmlElement(name = "time_end" )
		private String timeEnd  ; // 订单支付时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则 
		@XmlElement(name = "trade_state_desc" )
		private String tradeStateDesc  ;  
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
		public String getOpenId() {
			return openId;
		}
		public void setOpenId(String openId) {
			this.openId = openId;
		}
		public String getIsSubscribe() {
			return isSubscribe;
		}
		public void setIsSubscribe(String isSubscribe) {
			this.isSubscribe = isSubscribe;
		}
		public String getTradeState() {
			return tradeState;
		}
		public void setTradeState(String tradeState) {
			this.tradeState = tradeState;
		}
		public String getBankType() {
			return bankType;
		}
		public void setBankType(String bankType) {
			this.bankType = bankType;
		}
		public Integer getTotalFee() {
			return totalFee;
		}
		public void setTotalFee(Integer totalFee) {
			this.totalFee = totalFee;
		}
		public Integer getSettlementTotalFee() {
			return settlementTotalFee;
		}
		public void setSettlementTotalFee(Integer settlementTotalFee) {
			this.settlementTotalFee = settlementTotalFee;
		}
		public String getFeeType() {
			return feeType;
		}
		public void setFeeType(String feeType) {
			this.feeType = feeType;
		}
		public Integer getCashFee() {
			return cashFee;
		}
		public void setCashFee(Integer cashFee) {
			this.cashFee = cashFee;
		}
		public String getCashFeeType() {
			return cashFeeType;
		}
		public void setCashFeeType(String cashFeeType) {
			this.cashFeeType = cashFeeType;
		}
		public Integer getCoupon_fee() {
			return coupon_fee;
		}
		public void setCoupon_fee(Integer coupon_fee) {
			this.coupon_fee = coupon_fee;
		}
		public Integer getCouponCount() {
			return couponCount;
		}
		public void setCouponCount(Integer couponCount) {
			this.couponCount = couponCount;
		}
		public String getCouponType() {
			return couponType;
		}
		public void setCouponType(String couponType) {
			this.couponType = couponType;
		}
		public String getCouponId() {
			return couponId;
		}
		public void setCouponId(String couponId) {
			this.couponId = couponId;
		}
		public Integer getCouponFee() {
			return couponFee;
		}
		public void setCouponFee(Integer couponFee) {
			this.couponFee = couponFee;
		}
		public String getTransactionId() {
			return transactionId;
		}
		public void setTransactionId(String transactionId) {
			this.transactionId = transactionId;
		}
		public String getOutTradeNo() {
			return outTradeNo;
		}
		public void setOutTradeNo(String outTradeNo) {
			this.outTradeNo = outTradeNo;
		}
		public String getAttach() {
			return attach;
		}
		public void setAttach(String attach) {
			this.attach = attach;
		}
		public String getTimeEnd() {
			return timeEnd;
		}
		public void setTimeEnd(String timeEnd) {
			this.timeEnd = timeEnd;
		}
		public String getTradeStateDesc() {
			return tradeStateDesc;
		}
		public void setTradeStateDesc(String tradeStateDesc) {
			this.tradeStateDesc = tradeStateDesc;
		}
	}
}
