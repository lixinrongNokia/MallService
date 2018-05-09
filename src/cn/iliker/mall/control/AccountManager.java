package cn.iliker.mall.control;

import cn.iliker.mall.alipay.config.AlipayConfig;
import cn.iliker.mall.alipay.util.AlipayNotify;
import cn.iliker.mall.alipay.util.AlipaySubmit;
import cn.iliker.mall.entity.*;
import cn.iliker.mall.interceptor.Permission;
import cn.iliker.mall.service.*;
import cn.iliker.mall.utils.DateParseUtils;
import cn.iliker.mall.utils.MD5Util;
import cn.iliker.mall.utils.SerialNumber;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.iliker.mall.alipay.util.AlipayCore.logResult;
import static cn.iliker.mall.utils.AliPayUtil.parsAliPayPostInfo;

//财务相关
public class AccountManager extends ActionSupport implements ServletRequestAware {
    private ITransferSvc transferSvc;
    private ITransferRecordSvc transferRecordSvc;
    private ITransferDetailSvc transferDetailSvc;
    private IUserinfoSvc userinfoSvc;
    private int transferId;
    private final StringBuffer buffer = new StringBuffer();
    private final Map<String, String> sParaTemp = new HashMap<>();
    private HttpServletRequest httpServletRequest;
    private String payment_code = "";
    private JSONObject msg = new JSONObject();
    private String device;
    private Transfer transfer;
    private ICommonQuerySvc commonQuerySvc;
    private int totalPage = 1;

    private int offset = 1;

    public int getTotalPage() {
        return totalPage;
    }


    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    private List<Transfer> transfers;

    public List<Transfer> getTransfers() {
        return transfers;
    }

    public void setTransfers(List<Transfer> transfers) {
        this.transfers = transfers;
    }

    public void setCommonQuerySvc(ICommonQuerySvc commonQuerySvc) {
        this.commonQuerySvc = commonQuerySvc;
    }

    public void setTransfer(Transfer transfer) {
        this.transfer = transfer;
    }

    public Transfer getTransfer() {
        return this.transfer;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public void setUserinfoSvc(IUserinfoSvc userinfoSvc) {
        this.userinfoSvc = userinfoSvc;
    }

    public JSONObject getMsg() {
        return msg;
    }

    public void setPayment_code(String payment_code) {
        this.payment_code = payment_code;
    }

    public void setTransferDetailSvc(ITransferDetailSvc transferDetailSvc) {
        this.transferDetailSvc = transferDetailSvc;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public void setTransferRecordSvc(ITransferRecordSvc transferRecordSvc) {
        this.transferRecordSvc = transferRecordSvc;
    }

    public void setTransferSvc(ITransferSvc transferSvc) {
        this.transferSvc = transferSvc;
    }

    //查询申请的数据
    public String showApply() {
        GeneralList<Transfer> generalList = loadData("transferId,transfer.phone,transfer.account,transfer.realname,amount,putinforTime,note,userinfo.nickname", "transfer.phone=userinfo.phone and dealTag=0");
        if (generalList != null) {
            ActionContext.getContext().put("generalList", generalList);
            return SUCCESS;
        }
        return ERROR;
    }

    private GeneralList loadData(String _fields, String _where) {
        JSONObject jsonObject = null;
        jsonObject = commonQuerySvc.getAll(_fields, "transfer,userinfo", _where, null, "putinforTime DESC", offset == 0 ? 1 : offset, 10);
        if (jsonObject != null) {
            List<Transfer> list = JSON.parseArray(jsonObject.getJSONArray("dataSet").toJSONString(), Transfer.class);
            GeneralList<Transfer> generalList = new GeneralList<>();
            generalList.setList(list);
            generalList.setTotalSize(jsonObject.getInteger("totalSize"));
            totalPage = jsonObject.getInteger("pageCount");
            return generalList;
        }
        return null;
    }

    //查询成功的数据
    public String successApply() {
        GeneralList<Transfer> generalList = loadData("transferId,transfer.phone,transfer.account,transfer.realname,amount,manageTime,note,userinfo.nickname", "transfer.phone=userinfo.phone and dealTag=1");
        if (generalList != null) {
            ActionContext.getContext().put("generalList", generalList);
            return SUCCESS;
        }
        return NONE;
    }

    //单个处理
    @Permission(module = "account",privilege = "transfer")
    public String singleOperation() throws IOException {
        if (transferId == 0) {
            return ERROR;
        }
        TransferRecord transientInstance = new TransferRecord();
        Transfer transfer = transferSvc.findById(transferId);
        TransferDetail transferDetail = new TransferDetail();
        transferDetail.setTransfer(transfer);
        //付款当天日期
        String pay_date = DateParseUtils.getFormatDate("yyyyMMdd");
        transientInstance.setPayDate(pay_date);
        //必填，格式：年[4位]月[2位]日[2位]，如：20100801

        //批次号
        String batch_no = DateParseUtils.create_Batch_no();
        //必填，格式：当天日期[8位]+序列号[3至16位]，如：201008010000001
        transientInstance.setBatchNo(batch_no);
        transferDetail.setTransferRecord(transientInstance);
        transientInstance.getTransferDetails().add(transferDetail);
        //付款总金额
        String batch_fee = transfer.getAmount().toString();
        //必填，即参数detail_data的值中所有金额的总和
        transientInstance.setBatchFee(Double.parseDouble(batch_fee));
        //付款笔数
        String batch_num = "1";
        //必填，即参数detail_data的值中，“|”字符出现的数量加1，最大支持1000笔（即“|”字符出现的数量999个）
        transientInstance.setBatchNum(Integer.parseInt(batch_num));
        buffer.setLength(0);
        //付款详细数据
        String serial_no = SerialNumber.newInstance("1", new Date()).toString();
        transferDetail.setSerialNo(serial_no);
        String detail_data = buffer.append(serial_no)
                .append("^")
                .append(transfer.getAccount())
                .append("^")
                .append(transfer.getRealname())
                .append("^")
                .append(transfer.getAmount())
                .append("^")
                .append("提现退款")
                .toString();
        transientInstance.setDetailData(detail_data);
        try {
            transferRecordSvc.save(transientInstance);
            //必填，格式：流水号1^收款方帐号1^真实姓名^付款金额1^备注说明1|流水号2^收款方帐号2^真实姓名^付款金额2^备注说明2....
            aliPay(pay_date, batch_no, batch_fee, batch_num, detail_data);
            buffer.setLength(0);
            return NONE;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ERROR;
    }

    //批量处理
    @Permission(module = "account",privilege = "transfer")
    public String moreOperation() throws IOException {
        TransferRecord transientInstance = new TransferRecord();
        //付款当天日期
        String pay_date = DateParseUtils.getFormatDate("yyyyMMdd");
        //必填，格式：年[4位]月[2位]日[2位]，如：20100801
        transientInstance.setPayDate(pay_date);
        //批次号
        String batch_no = DateParseUtils.create_Batch_no();
        //必填，格式：当天日期[8位]+序列号[3至16位]，如：201008010000001
        transientInstance.setBatchNo(batch_no);
        //付款总金额
        double batch_fee = 0;
        //必填，即参数detail_data的值中所有金额的总和

        //付款笔数
        int batch_num = 0;
        //必填，即参数detail_data的值中，“|”字符出现的数量加1，最大支持1000笔（即“|”字符出现的数量999个）
        buffer.setLength(0);

        for (Transfer transfer : transfers) {
            TransferDetail transferDetail = new TransferDetail();
            transferDetail.setTransfer(transfer);
            transferDetail.setTransferRecord(transientInstance);
            transientInstance.getTransferDetails().add(transferDetail);
            String serial_no = SerialNumber.newInstance("1", new Date()).toString();
            transferDetail.setSerialNo(serial_no);
            buffer.append(serial_no)
                    .append("^")
                    .append(transfer.getAccount())
                    .append("^")
                    .append(transfer.getRealname())
                    .append("^")
                    .append(transfer.getAmount())
                    .append("^")
                    .append("提现退款")
                    .append("|");
            batch_fee += transfer.getAmount();
            batch_num++;
        }
        buffer.deleteCharAt(buffer.length() - 1);
        String detail_data = buffer.toString();
        transientInstance.setBatchNum(batch_num);
        transientInstance.setBatchFee(batch_fee);
        transientInstance.setDetailData(detail_data);
        try {
            transferRecordSvc.save(transientInstance);
            aliPay(pay_date, batch_no, String.valueOf(batch_fee), String.valueOf(batch_num), detail_data);
            buffer.setLength(0);
            return NONE;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ERROR;
    }

    private void aliPay(String pay_date, String batch_no, String batch_fee, String batch_num, String detail_data) throws IOException {
        ServletActionContext.getResponse().setContentType(
                "text/html;charset=utf-8");
        //把请求参数打包成数组
        sParaTemp.put("service", "batch_trans_notify");
        sParaTemp.put("partner", AlipayConfig.PARTNER);
        sParaTemp.put("_input_charset", AlipayConfig.INPUT_CHARSET);
        sParaTemp.put("notify_url", AlipayConfig.NOTIFY_URL);
        sParaTemp.put("email", AlipayConfig.EMAIL);
        sParaTemp.put("account_name", AlipayConfig.ACCOUNT_NAME);
        sParaTemp.put("pay_date", pay_date);
        sParaTemp.put("batch_no", batch_no);
        sParaTemp.put("batch_fee", batch_fee);
        sParaTemp.put("batch_num", batch_num);
        sParaTemp.put("detail_data", detail_data);

        //建立请求
        String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", "确认");
        PrintWriter out = ServletActionContext.getResponse().getWriter();
        out.println(sHtmlText);
        out.flush();
        out.close();
        sParaTemp.clear();
    }

    //支付宝批量转账到支付宝账户异步通知
    public String aliPayNotify() throws IOException {
        //获取支付宝POST过来反馈信息
        Map<String, String> params = parsAliPayPostInfo(httpServletRequest.getParameterMap());

        String success_details = params.get("success_details");

        //批量付款数据中转账失败的详细信息
        String fail_details = params.get("fail_details");

        String batch_no = params.get("batch_no");

        String sign_type = params.get("sign_type");
        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();

        if (AlipayNotify.verify(params, sign_type)) {//验证成功

            //请在这里加上商户的业务逻辑程序代码
            new Thread(() -> {
                try {
                    List<TransferDetail> transferDetails = transferDetailSvc.findByBatchNo(batch_no);
                    Map<String, TransferDetail> map = list2Map(transferDetails);
                    if (map != null) {
                        transferDetails.clear();
                        String[] successes;
                        if (success_details != null && success_details.length() > 0) {
                            if (success_details.contains("\\|")) {
                                successes = success_details.split("\\|");
                            } else {
                                successes = new String[]{success_details};
                            }
                            Date date = new Date();
                            for (String successStr : successes) {
                                String serialNo = successStr.split("\\^")[0];
                                TransferDetail transferDetail = map.get(serialNo);//获取成功条目
                                transferDetail.setStateTag(true);
                                Transfer transfer = transferDetail.getTransfer();
                                transfer.setManageTime(date);
                                transfer.setDealTag(true);
                                transferDetails.add(transferDetail);
                            }
                        }

                        String[] fails;

                        if (fail_details != null && fail_details.length() > 0) {
                            if (fail_details.contains("\\|")) {
                                fails = fail_details.split("\\|");
                            } else {
                                fails = new String[]{fail_details};
                            }
                            for (String failsStr : fails) {
                                String[] detailData = failsStr.split("\\^");
                                String serialNo = detailData[0];
                                TransferDetail transferDetail = map.get(serialNo);//失败的条目
                                transferDetail.setRemark(detailData[5]);//更新失败原因
                                transferDetails.add(transferDetail);
                            }
                        }
                        transferDetailSvc.batchUpdate(transferDetails);
                    } else {
                        logResult("no transferDetails data");
                    }
                } catch (Exception e) {
                    logResult("system error" + e.getMessage());
                }
            }).start();
            printWriter.println("success");
            printWriter.flush();
            printWriter.close();
            return NONE;
        } else {
            printWriter.println("fail");
            printWriter.flush();
            printWriter.close();
            return NONE;
        }
    }

    private Map<String, TransferDetail> list2Map(List<TransferDetail> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        Map<String, TransferDetail> map = new HashMap<>();
        for (TransferDetail transferDetail : list) {
            map.put(transferDetail.getSerialNo(), transferDetail);
        }
        return map;
    }

   /* public String demo() throws IOException {
        responseTxt=true
 isSign=true
 返回回来的参数：batch_no=20170406104141&notify_id=169e4d73366b0c07ab63da75db51dfeh2y&notify_time=2017-04-06 10:42:00&notify_type=batch_trans_notify&pay_account_no=20884212450036740156&pay_user_id=2088421245003674&pay_user_name=东莞市艾芬儿服饰有限公司&sign=04e2adac69c117cbc8df0782890cd028&sign_type=MD5&success_details=11704060002^15602229027^刘向上^0.07^S^^201704060204268008^20170406104159|*//**//*
        String success_details = "11704050001^15602229027^刘向上^0.07^S^^201704060204268008^20170406104159|";

        //批量付款数据中转账失败的详细信息
        String fail_details = null;

        String batch_no = "20170405145914";

        String sign_type = "MD5";
        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
        try {
            List<TransferDetail> transferDetails = transferDetailSvc.findByBatchNo(batch_no);
            Map<String, TransferDetail> map = list2Map(transferDetails);
            if (map != null) {
                transferDetails.clear();
                String[] successes;
                if (success_details != null && success_details.length() > 0) {
                    if (success_details.contains("\\|")) {
                        successes = success_details.split("\\|");
                    } else {
                        successes = new String[]{success_details};
                    }
                    Date date = new Date();
                    for (String successStr : successes) {
                        String serialNo = successStr.split("\\^")[0];
                        TransferDetail transferDetail = map.get(serialNo);//获取成功条目
                        transferDetail.setStateTag(true);
                        Transfer transfer = transferDetail.getTransfer();
                        transfer.setManageTime(date);
                        transfer.setDealTag(true);
                        TOrder tOrder = transfer.gettOrder();
                        if (tOrder != null) {
                            tOrder.setOrderstate(OrderState.SHUTDOWN.getName());
                        }
                        transferDetails.add(transferDetail);
                    }
                }

                String[] fails;

                if (fail_details != null && fail_details.length() > 0) {
                    if (fail_details.contains("\\|")) {
                        fails = fail_details.split("\\|");
                    } else {
                        fails = new String[]{fail_details};
                    }
                    for (String failsStr : fails) {
                        String[] detailData = failsStr.split("\\^");
                        String serialNo = detailData[0];
                        TransferDetail transferDetail = map.get(serialNo);//失败的条目
                        transferDetail.setRemark(detailData[5]);//更新失败原因
                        transferDetails.add(transferDetail);
                    }
                }
                transferDetailSvc.batchUpdate(transferDetails);
                printWriter.print("success");
                printWriter.close();
            } else {
                printWriter.print("error");
                printWriter.close();
            }

        } catch (Exception e) {
            logResult(e.getMessage() + "//" + System.err.toString());
            printWriter.print("error");
            printWriter.close();
        }

        return NONE;
    }*/

    @Override
    public void setServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    public String addTransfer() {
        msg.clear();
        Userinfo userinfo = userinfoSvc.findByPhone(transfer.getPhone());
        if (userinfo != null) {
            if ("h5".equals(device)) {
                payment_code = MD5Util.getMD5Str(new String(MD5Util.decode(payment_code)));
            }
            if (userinfo.getPaymentCode() == null) {
                msg.put("success", false);
                msg.put("msg", "你没有设置支付密码");
                return ERROR;
            }
            if (!userinfo.getPaymentCode().equals(payment_code)) {
                msg.put("success", false);
                msg.put("msg", "支付密码不匹配");
                return ERROR;
            }
            try {
                transferSvc.addTransfer(transfer);
                msg.put("success", true);
                return SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        msg.put("msg", "不明原因失败");
        msg.put("success", false);
        return ERROR;
    }

}
