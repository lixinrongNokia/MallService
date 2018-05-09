package cn.iliker.mall.dao.daoimpl;

import cn.iliker.mall.dao.ICommonQueryDAO;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.orm.hibernate5.SessionFactoryUtils;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.sql.*;

public class CommonQueryDAO extends HibernateDaoSupport implements ICommonQueryDAO {
    @Override
    public JSONObject getAll(String _fields, String _tables, String _where, String _groupby, String _orderby, int _pageindex, int _pagesize) {
        String procdure = "{Call Query_Pagination(?,?,?,?,?,?,?,?,?)}";
        CallableStatement cs = null;
        JSONObject rootObject;
        Connection connection = null;
        try {
            connection = SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
            cs = connection.prepareCall(procdure);
            rootObject = new JSONObject();
            cs.setString(1, _fields);
            cs.setString(2, _tables);
            cs.setString(3, _where);
            cs.setString(4, _groupby);
            cs.setString(5, _orderby);
            cs.setInt(6, _pageindex);
            cs.setInt(7, _pagesize);
            cs.registerOutParameter(8, Types.INTEGER);
            cs.registerOutParameter(9, Types.INTEGER);
            boolean hadResults = cs.execute();
            if (hadResults) {
                JSONArray jsonArray = new JSONArray();
                rootObject.put("totalSize", cs.getInt(8));
                rootObject.put("pageCount", cs.getInt(9));
                ResultSet rs = cs.getResultSet();
                ResultSetMetaData setMetaData = rs.getMetaData();
                int itemCount = setMetaData.getColumnCount();
                while (rs.next()) {
                    JSONObject jsonObject = new JSONObject();
                    for (int i = 0; i < itemCount; i++) {
                        jsonObject.put(setMetaData.getColumnName(i + 1), rs.getObject(i + 1));
                    }
                    jsonArray.add(jsonObject);
                }
                if (jsonArray.size() > 0) {
                    rootObject.put("dataSet", jsonArray);
                } else return null;
            }
        } catch (SQLException e) {
            return null;
        } finally {
            try {
                if (cs != null) {
                    cs.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return rootObject;
    }
}
