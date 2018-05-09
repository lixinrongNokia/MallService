package cn.iliker.mall.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * 工作表
 * Created by LIXINRONG on 2016/9/11.
 */
public class SheetData {
    private Map<Integer, Row> rows = new HashMap<>();

    public Map<Integer, Row> getRows() {
        return rows;
    }

    public void setRows(Map<Integer, Row> rows) {
        this.rows = rows;
    }
}
