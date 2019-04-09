package com.qihang.friendship.controller;

import com.alipay.api.internal.util.AlipaySignature;
import com.qihang.friendship.common.Constants;
import com.qihang.friendship.config.AlipayProperties;
import com.qihang.friendship.service.IAlipayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * <p>项目名称: awesome-friends </p>
 * <p>文件名称: com.qihang.friendship.controller.AlipayController </p>
 * <p>创建时间: 2019/3/18 </p>
 * <p>公司信息: 上海启航软件科技有限公司 </p>
 *
 * @author <a href="mail to: young_fine@163.com" rel="nofollow">topyfine</a>
 * @version v1.0
 */
@RestController
@Slf4j
public class AlipayController {
    private static final String SUCCESS = "success";
    private static final String FAILURE = "failure";
    @Autowired
    private IAlipayService alipayService;
    @Autowired
    private AlipayProperties properties;

    /**
     * 验证签名正确后，必须再严格按照如下描述校验通知数据的正确性。
     *
     * 1、商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
     * 2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
     * 3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email），
     * 4、验证app_id是否为该商户本身。上述1、2、3、4有任何一个验证不通过，则表明本次通知是异常通知，务必忽略。
     *
     * 在上述验证通过后商户必须根据支付宝不同类型的业务通知，正确的进行不同的业务处理，并且过滤重复的通知结果数据。
     * 在支付宝的业务通知中，只有交易通知状态为TRADE_SUCCESS或TRADE_FINISHED时，支付宝才会认定为买家付款成功。
     *
     * @param request
     * @return
     */
    @RequestMapping(value = Constants.ALIPAY_APP_NOTIFY_PATH, method = RequestMethod.POST)
    public String appPayNotifyHandler(HttpServletRequest request) {
        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        try {
            //切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
            //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
            boolean signVerfied = AlipaySignature.rsaCheckV1(params, properties.getAlipayPublicKey(), "utf-8","RSA2");
            if (signVerfied) {
                //验签成功后
                //按照支付结果异步通知中的描述，对支付结果中的业务内容进行1\2\3\4二次校验，校验成功后在response中返回success，校验失败返回failure
                boolean confirm = alipayService.confirmAppNotify(params);
                log.info(">>>支付宝APP支付异步通知验签成功，确认结果：{}，通知内容：{}", confirm, params);
                return confirm ? SUCCESS : FAILURE;
            } else {
                //验签失败则记录异常日志，并在response中返回failure.
                log.error(">>>支付宝APP支付异步通知验签失败：{}", params);
                return FAILURE;
            }
        } catch (Exception e) {
            log.error(">>>支付宝APP支付异步通知处理失败：{}", params, e);
            return FAILURE;
        }
    }
}
