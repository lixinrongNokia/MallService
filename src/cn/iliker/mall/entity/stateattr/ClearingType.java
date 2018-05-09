package cn.iliker.mall.entity.stateattr;

public enum ClearingType {
    CASH {
        @Override
        public String getName() {
            return "现金结算";
        }
    },
    SPOT {
        @Override
        public String getName() {
            return "现货结算";
        }
    };

    public abstract String getName();

    public static ClearingType pars(String str) {
        if ("CASH".equals(str)) {
            return CASH;
        } else if ("SPOT".equals(str)) {
            return SPOT;
        } else {
            return CASH;
        }
    }
}
