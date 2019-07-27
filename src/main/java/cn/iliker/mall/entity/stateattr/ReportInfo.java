package cn.iliker.mall.entity.stateattr;

public class ReportInfo {
    //'色情低俗','政治敏感','违法','广告','其他'
    public static String reportShareType(int typeCode) {
        switch (typeCode) {
            case 0:
                return "色情低俗";
            case 1:
                return "政治敏感";
            case 2:
                return "违法";
            case 3:
                return "广告";
            case 4:
                return "其他";
        }
        return "色情低俗";
    }
}
