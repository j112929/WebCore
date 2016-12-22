/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.appcore.util;

import java.util.UUID;

public class WXPayUtil {
	public static final String notify_url = "http://im.app987.com:8001/nodo/api/btsPay/callBack";
	public static final String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	public static final String appid = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	public static final String mch_id = "https://api.mch.weixin.qq.com/pay/unifiedorder";

	public static void createTrade(String openid, String goodsId,
			String orderNo, String remark) {
		String appid = "https://api.mch.weixin.qq.com/pay/unifiedorder";

		String mch_id = "https://api.mch.weixin.qq.com/pay/unifiedorder";

		String nonce_str = UUID.randomUUID().toString();

		String body = remark;

		String out_trade_no = orderNo;

		int total_fee = 100;

		String spbill_create_ip = "123.12.12.123";

		String notify_url = "http://im.app987.com:8001/nodo/api/btsPay/callBack";

		String trade_type = "JSAPI";

		String product_id = goodsId;
	}
}