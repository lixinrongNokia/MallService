package cn.iliker.mall.entity.stateattr;

/**
 * 订单状态
 */
public enum OrderState {

    /**
     * 已取消
     */
    CANCEL {
        public String getName() {
            return "已取消";
        }
    },
    /**
     * 等待付款
     */
    WAITPAYMENT {
        public String getName() {
            return "等待付款";
        }
    },
    /**
     * 待审核
     */
    WAITCONFIRM {
        public String getName() {
            return "待审核";
        }
    },
    /**
     * 已审核
     */
    CONFIRMORDERED {
        public String getName() {
            return "已审核";
        }
    },
    /**
     * 等待配货
     */
    ADMEASUREPRODUCT {
        public String getName() {
            return "等待配货";
        }
    },
    /**
     * 等待发货
     */
    WAITDELIVER {
        public String getName() {
            return "等待发货";
        }
    },
    /**
     * 已发货
     */
    DELIVERED {
        public String getName() {
            return "已发货";
        }
    },
    /**
     * 已收货
     */
    RECEIVED {
        public String getName() {
            return "已收货";
        }
    },
    REFUNDING {
        public String getName() {
            return "退款中";
        }
    },
    SHUTDOWN {
        public String getName() {
            return "已关闭";
        }
    };

    public abstract String getName();

    /*允许取消订单的条件*/
    public static String[] allowsCancel = {WAITPAYMENT.getName(), WAITCONFIRM.getName(), CONFIRMORDERED.getName(), ADMEASUREPRODUCT.getName(), WAITDELIVER.getName()};
    /*不允许订单退款的条件*/
    public static String[] allowsRefunds = {WAITPAYMENT.getName(),RECEIVED.getName(), REFUNDING.getName(), SHUTDOWN.getName()};
    public static String[] allowsPickup = {WAITPAYMENT.getName(),RECEIVED.getName(), REFUNDING.getName(), SHUTDOWN.getName()};
}
