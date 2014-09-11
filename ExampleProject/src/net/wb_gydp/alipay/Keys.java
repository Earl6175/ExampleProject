/*
 * Copyright (C) 2010 The MobileSecurePay Project
 * All right reserved.
 * author: shiqun.shi@alipay.com
 * 
 *  提示：如何获取安全校验码和合作身份者id
 *  1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *  2.点击“商家服务”(https://b.alipay.com/order/myorder.htm)
 *  3.点击“查询合作者身份(pid)”、“查询安全校验码(key)”
 */

package net.wb_gydp.alipay;

//
// 请参考 Android平台安全支付服务(msp)应用开发接口(4.2 RSA算法签名)部分，并使用压缩包中的openssl RSA密钥生成工具，生成一套RSA公私钥。
// 这里签名时，只需要使用生成的RSA私钥。
// Note: 为安全起见，使用RSA私钥进行签名的操作过程，应该尽量放到商家服务器端去进行。
public final class Keys {

	// 合作商户ID,以2088开头的16位纯数字。用签约支付宝账号登录ms.alipay.com后，在账户信息页面获取。
	// public static final String DEFAULT_PARTNER = "2088201611767607";
	// 商户收款的支付宝账号
	// public static final String DEFAULT_SELLER = "2088201611767607";
	// 商户（RSA）私钥
	// public static final String PRIVATE =
	// "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBANWVZ3wyCMgx3AC5WGbc6xVkG1Xpk6Wxv8Bq1rM/jSqWqv0jHsVb7f8Oo+Ay1AZQ6FbhGl/cr4RE5M0ij55NA/LHjO19k/xGxolIXKkUlZxnI4EADFPrhjjluaXk4A0iCe4cAKbIzkiWNUkL5QsRTJ2+iPsQvh2vfUcHpLaGLtzLAgMBAAECgYBu6oGtW4Z4AHyR267u7DchCrWvbfeBCsfbsyAAQjabMVdm0gA+2bjExeOQoJS/GvEtfQTnV4HGnMh9Rz9yI+iyDhH+Od50Ft+8pVarLRwP4yYpTC7/mh2ciDr/JPy0Nu/z+9Ojo3/mldeEpS+uCzOj6DtJ4MwRTdaWBrrfb6xOMQJBAP0gTQ5i3mHEvGpxqd6UFATYs92/HlzBDTuOIEmaTsn1Ub3sEUuJwgmIa0U8eO9fXL0HewAMvos7XSRwiPr9L58CQQDYAiy39/NiyhTrIa1cAyZlC3qKgfRN7/r0Q6Bx26kqRVxbnbZGtRUfEMS1MLFXfClXzZNpLkeGCvmJxt5ZYtNVAkBwBvSzkkRTxuuQvJ9ut+CoSHRKhsRcTsCZhAx8JsQKE/3KbYk2AlETQQIg0WPApVb4/i+M1RcbrflKIFgvpRCHAkBErMxS4P38Vos4Bkj7i/TVA0U+a/qkHLwIlMeZ62BpFAw9BHNDH6dLbSVdBdIcIllylOVc9129/BdB+nwOpyDRAkBxH1LD+ybVXH6isCFhsOT9FtExRVvwyplbP0PGf9kymMle+W5DlQQyXgy0wAmZ2FNiVeRGtfKRK8NxHKMRpJ4X";
	// 支付宝（RSA）公钥 用签约支付宝账号登录ms.alipay.com后，在密钥管理页面获取。
	public static final String PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCrmWqfQaenc4c1IGEYBU6ufwC/HQ11AI+h/Vbd MkArRTi2EGftFcmpt5ow1LwfVxLSRNaGY7eCh7jVZpFSE7qrUJLIQOScz+MjSt9JrsfMAIluv+Ug +wPyB9OFJOiir3yfy2eYYz3+ZjqJLkin876yPdx1oedBRudAyt+HjMnKYQIDAQAB";

}
