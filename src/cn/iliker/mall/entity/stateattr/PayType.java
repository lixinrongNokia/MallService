package cn.iliker.mall.entity.stateattr;

public enum PayType {
    WXPAY {
        public String getName() {
            return "微信支付";
        }
    },
    ALIPAY {
        public String getName() {
            return "支付宝支付";
        }
    }, PAYFOR {
        public String getName() {
            return "零钱支付";
        }
    }, CARDVOUCHERTOPAY {
        public String getName() {
            return "卡券支付";
        }
    };

    public abstract String getName();
}
