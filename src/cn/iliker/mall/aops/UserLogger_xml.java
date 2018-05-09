package cn.iliker.mall.aops;

import cn.iliker.mall.dao.IUserAccountDAO;
import cn.iliker.mall.dao.IUserinfoDAO;
import cn.iliker.mall.entity.*;
import cn.iliker.mall.utils.MatchOrderType;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

import java.math.BigDecimal;

public class UserLogger_xml {
    private IUserAccountDAO userAccountDAO;
    private IUserinfoDAO userinfoDAO;
    /*三级分销分佣数据*/
    private static double ddk[][] = new double[3][5];

    static {
        ddk[0][0] = 0.012;
        ddk[0][1] = 0.029;
        ddk[0][2] = 0.046;
        ddk[0][3] = 0.058;
        ddk[0][4] = 0.058;

        ddk[1][0] = 0.003;
        ddk[1][1] = 0.008;
        ddk[1][2] = 0.014;
        ddk[1][3] = 0.018;
        ddk[1][4] = 0.018;

        ddk[2][0] = 0.001;
        ddk[2][1] = 0.006;
        ddk[2][2] = 0.009;
        ddk[2][3] = 0.014;
        ddk[2][4] = 0.014;
    }

    public void setUserAccountDAO(IUserAccountDAO userAccountDAO) {
        this.userAccountDAO = userAccountDAO;
    }

    public void setUserinfoDAO(IUserinfoDAO userinfoDAO) {
        this.userinfoDAO = userinfoDAO;
    }

    /**
     * 使用POJO作为增强类
     * Aspect 基于注解的非侵入式配置(重点在配置文件中体现：详见：readme_Aspect 基于注解的非侵入式配置)
     */
    /*java.lang.Object[] getArgs()：获取连接点方法运行时的入参列表；
     Signature getSignature() ：获取连接点的方法签名对象；
     java.lang.Object getTarget() ：获取连接点所在的目标对象；
     java.lang.Object getThis() ：获取代理对象本身*/
    //前置增强     匹配   所有类型返回值的，proxy_dynamic包中的，所有方法名，所有参数个数和类型
    public void before(JoinPoint point) {
        System.out.println("前置增强执行111!");
    }

    //注册用户后置增强记录三级
    public void afterReturning(JoinPoint point, Object returnvalue) {
        /*String trade_no, String orderId, double total_fee, String phone*/

    }

    /*分佣后置增强*/
    public void afterCommission(JoinPoint point, Object returnvalue) {
        Object[] objects = point.getArgs();
        String orderId = (String) objects[1];
        Double total_fee = (Double) objects[2];
        BigDecimal feedb = new BigDecimal(total_fee);
        String phone = (String) objects[3];
        char type = MatchOrderType.matchOrderType(orderId);
        switch (type) {
            case 'c':
                int integral = (int) (total_fee * 0.1 + 0.5f);
                IntegralAccount integralAccount = userAccountDAO.getIntegral(phone);
                integralAccount.setAmount(integralAccount.getAmount() + integral);//更新积分
                userAccountDAO.saveIntegral(integralAccount);//执行更新
                Integralincome integralincome = new Integralincome(integral, phone);
                userAccountDAO.saveIntegralincome(integralincome);//记录积分
                Spending spending = new Spending("爱内秀购物", total_fee, phone);//记录消费
                userAccountDAO.saveSpending(spending);
                Wallet wallet2 = userAccountDAO.getWallet(phone);
                BigDecimal oldSpending = new BigDecimal(wallet2.getTotalSpending());
                BigDecimal addSpending = new BigDecimal(total_fee);
                wallet2.setTotalSpending(oldSpending.add(addSpending).doubleValue());//计算总支出
                userAccountDAO.saveWallet(wallet2);
                /*收入分佣*/
                Userinfo userinfo = userinfoDAO.findByPhone(phone);
                Userinfo oneLV = userinfo.getParent();
                if (oneLV != null) {
                    int levelter = oneLV.getLevel();
                    BigDecimal benefit1 = new BigDecimal(ddk[0][levelter]);
                    BigDecimal onecs = feedb.multiply(benefit1);//获取一级分佣金额
                    Wallet oneLVWallet = oneLV.getWallet();
                    BigDecimal oneOld = new BigDecimal(oneLVWallet.getRemainingSum());
                    oneLVWallet.setRemainingSum(onecs.add(oneOld).doubleValue());//更新一级账户金额
                    Income income1 = new Income("分佣", onecs.doubleValue(), oneLV.getPhone());
                    userAccountDAO.saveIncome(income1);
                    userAccountDAO.saveWallet(oneLVWallet);//更新二级账户
                    Userinfo twoLV = oneLV.getParent();
                    if (twoLV != null) {
                        int levelter2 = twoLV.getLevel();
                        BigDecimal benefit2 = new BigDecimal(ddk[1][levelter2]);
                        BigDecimal twocs = feedb.multiply(benefit2);//获取二级分佣金额
                        Wallet twoLVWallet = twoLV.getWallet();
                        BigDecimal twoOld = new BigDecimal(twoLVWallet.getRemainingSum());
                        twoLVWallet.setRemainingSum(twocs.add(twoOld).doubleValue());//更新二级账户金额
                        userAccountDAO.saveWallet(twoLVWallet);//更新二级账户
                        Income income2 = new Income("分佣", twocs.doubleValue(), twoLV.getPhone());
                        userAccountDAO.saveIncome(income2);
                        Userinfo threeLV = twoLV.getParent();
                        if (threeLV != null) {
                            int levelter3 = threeLV.getLevel();
                            BigDecimal benefit3 = new BigDecimal(ddk[2][levelter3]);
                            BigDecimal threecs = feedb.multiply(benefit3);//获取二级分佣金额
                            Wallet threeLVWallet = threeLV.getWallet();
                            BigDecimal threeOld = new BigDecimal(threeLVWallet.getRemainingSum());
                            threeLVWallet.setRemainingSum(threecs.add(threeOld).doubleValue());//更新二级账户金额
                            userAccountDAO.saveWallet(threeLVWallet);
                            Income income3 = new Income("分佣", threecs.doubleValue(), threeLV.getPhone());
                            userAccountDAO.saveIncome(income3);
                        }
                    }
                }
                break;
            case 'd':
                /*自充值*/
                Income income = new Income("账户充值", total_fee, phone);//记录收入
                userAccountDAO.saveIncome(income);
                Spending spending1 = new Spending("账户充值", total_fee, phone);//记录消费
                userAccountDAO.saveSpending(spending1);
                break;
            case 'x':
                Spending spending2 = new Spending("购买卡券", total_fee, phone);//记录消费
                userAccountDAO.saveSpending(spending2);
                break;
        }
    }

    //环绕增强
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("=======================拦截到action方法");
        return pjp.proceed();
    }

    //异常增强
    public void afterThrowing() throws Throwable {
        System.out.println("===异常增强");
    }

    //最终增强（无论如何都要执行的此方法）
    public void after(JoinPoint point) throws Throwable {
        Object[] objects = point.getArgs();
        String phone = (String) objects[3];
        Userinfo userinfo = userinfoDAO.findByPhone(phone);
        Wallet wallet = userinfo.getWallet();
        userinfoDAO.updateLevel(userinfo);
        Userinfo oneLV = userinfo.getParent();
        Userinfo twoLV;
        Userinfo threeLV;
        if (oneLV != null) {
            userinfoDAO.updateLevel(oneLV);
            twoLV = oneLV.getParent();
            if (twoLV != null) {
                userinfoDAO.updateLevel(twoLV);
                threeLV = twoLV.getParent();
                if (threeLV != null) {
                    userinfoDAO.updateLevel(threeLV);
                }
            }
        }
    }
}
