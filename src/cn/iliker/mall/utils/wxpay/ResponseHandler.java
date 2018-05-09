package cn.iliker.mall.utils.wxpay;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import static cn.iliker.mall.alipay.util.AlipayCore.logResult;

/**
 * 应答处理类
 * 应答处理类继承此类，重写isTenpaySign方法即可。
 *
 * @author miklchen
 */
public class ResponseHandler {

    /**
     * 密钥
     */
    private String key;

    /**
     * 应答的参数
     */
    private SortedMap parameters;

    /**
     * debug信息
     */
    private String debugInfo;

    private HttpServletRequest request;

    private HttpServletResponse response;

    private String uriEncoding;

    /**
     * 构造函数
     *
     * @param request
     * @param response
     */
    public ResponseHandler(HttpServletRequest request,
                           HttpServletResponse response) {
        this.request = request;
        this.response = response;

        this.key = "";
        this.debugInfo = "";

        this.uriEncoding = "";
        try {
            BufferedReader reader = request.getReader();
            String line = "";
            StringBuffer inputString = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                inputString.append(line);
            }
            reader.close();
            this.parameters = XMLUtil.doXMLParse(inputString.toString());
        } catch (Exception e) {
            logResult(e.getMessage());
        }

    }

    /**
     * 获取密钥
     */
    public String getKey() {
        return key;
    }

    /**
     * 设置密钥
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * 获取参数值
     *
     * @param parameter 参数名称
     * @return String
     */
    public String getParameter(String parameter) {
        String s = (String) this.parameters.get(parameter);
        return (null == s) ? "" : s;
    }

    /**
     * 设置参数值
     *
     * @param parameter      参数名称
     * @param parameterValue 参数值
     */
   /* public void setParameter(String parameter, String parameterValue) {
        String v = "";
        if (null != parameterValue) {
            v = parameterValue.trim();
        }
        this.parameters.put(parameter, v);
    }*/

    /**
     * 是否财付通签名,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
     *
     * @return boolean
     */
    public boolean isTenpaySign() {
        StringBuffer sb = new StringBuffer();
        Set es = this.parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (!"sign".equals(k) && null != v && !"".equals(v)) {
                sb.append(k + "=" + v + "&");
            }
        }

        sb.append("key=" + this.getKey());

        //算出摘要
        String enc = TenpayUtil.getCharacterEncoding(this.request, this.response);
        String sign = MD5.MD5Encode(sb.toString(), enc).toLowerCase();

        String tenpaySign = this.getParameter("sign").toLowerCase();

        //debug信息
        this.setDebugInfo(sb.toString() + " => sign:" + sign +
                " tenpaySign:" + tenpaySign);

        return tenpaySign.equals(sign);
    }

    /**
     * 返回处理结果给财付通服务器。
     *
     * @param msg: Success or fail。
     * @throws IOException
     */
    public void sendToCFT(String msg) throws IOException {
        String strHtml = msg;
        PrintWriter out = this.getHttpServletResponse().getWriter();
        out.println(strHtml);
        out.flush();
        out.close();

    }


    /**
     * 设置debug信息
     */
    protected void setDebugInfo(String debugInfo) {
        this.debugInfo = debugInfo;
    }


    protected HttpServletResponse getHttpServletResponse() {
        return this.response;
    }

}
