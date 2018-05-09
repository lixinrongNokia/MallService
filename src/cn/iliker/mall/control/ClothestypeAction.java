package cn.iliker.mall.control;

import cn.iliker.mall.entity.Brand;
import cn.iliker.mall.entity.Clothestype;
import cn.iliker.mall.interceptor.Permission;
import cn.iliker.mall.service.IClothesTypeSvc;
import cn.iliker.mall.utils.PolExportUtils;
import cn.iliker.mall.utils.Rename_PIC;
import cn.iliker.mall.utils.UrlTools;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ClothestypeAction extends ActionSupport {
    private int crowdid;
    private IClothesTypeSvc closvc;
    private String jsonarray;
    private Clothestype clothestype;

    // 添加类别属性
    private File clothestimg;
    private String clothestimgFileName;

    private String fileName;
    private JSONArray crowds = new JSONArray();

    public JSONArray getCrowds() {
        return crowds;
    }

    public Clothestype getClothestype() {
        return clothestype;
    }

    public IClothesTypeSvc getClosvc() {
        return closvc;
    }

    public void setClothestype(Clothestype clothestype) {
        this.clothestype = clothestype;
    }

    public String getFileName() throws UnsupportedEncodingException {
        HttpServletRequest request = ServletActionContext.getRequest();
        String Agent = request.getHeader("User-Agent");
        if (null != Agent) {
            Agent = Agent.toLowerCase();
            if (Agent.contains("firefox")) {
                fileName = new String("商品导入编辑.xls".getBytes(), "iso8859-1");
            } else if (Agent.contains("msie")) {
                fileName = java.net.URLEncoder.encode("商品导入编辑.xls", "UTF-8");
            } else {
                fileName = java.net.URLEncoder.encode("商品导入编辑.xls", "UTF-8");
            }
        }
        return fileName;
    }

    public File getClothestimg() {
        return clothestimg;
    }

    public void setClothestimg(File clothestimg) {
        this.clothestimg = clothestimg;
    }

    public String getClothestimgFileName() {
        return clothestimgFileName;
    }

    public void setClothestimgFileName(String clothestimgFileName) {
        this.clothestimgFileName = clothestimgFileName;
    }

    public String getJsonarray() {
        return jsonarray;
    }

    public void setJsonarray(String jsonarray) {
        this.jsonarray = jsonarray;
    }

    public int getCrowdid() {
        return crowdid;
    }

    public void setCrowdid(int crowdid) {
        this.crowdid = crowdid;
    }

    public void setClosvc(IClothesTypeSvc closvc) {
        this.closvc = closvc;
    }


    public String queryCrowds() {
        List<Object[]> list = closvc.findAll();
        if (list != null && !list.isEmpty()) {
            for (Object[] objects : list) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("crowdName", objects[0]);
                String[] substr = ((String) objects[1]).split(",");
                JSONArray jsonArray = new JSONArray();
                for (String s : substr) {
                    String[] objs = s.split(";");
                    JSONObject obj = new JSONObject();
                    obj.put("id", objs[0]);
                    obj.put("name", objs[1]);
                    obj.put("typeimg", objs[2]);
                    jsonArray.add(obj);
                }
                jsonObject.put("data", jsonArray);
                crowds.add(jsonObject);
            }
            return SUCCESS;
        }
        return NONE;
    }

    public String querytype() {
        List<Object[]> list = closvc.findAll(crowdid);
        if (!list.isEmpty()) {
            List<Map<String, String>> clothestypes = new ArrayList<>();
            for (Object[] obj : list) {
                Map<String, String> map = new LinkedHashMap<>();
                map.put("id", String.valueOf(obj[0]));
                map.put("name", String.valueOf(obj[1]));
                clothestypes.add(map);
            }
            setJsonarray(JSONArray.toJSONString(clothestypes));
            clothestypes.clear();
            return SUCCESS;
        }
        return NONE;
    }

    // 添加类别
    @Permission(module = "productType",privilege = "insert")
    public String launchCrowd() {
        if (clothestimg != null) {
            String newName = Rename_PIC.renameUtil(clothestimgFileName);
            File savefile = new File(UrlTools.GOODSDIR.getParent(), newName);
            if (!savefile.getParentFile().exists())
                savefile.getParentFile().mkdirs();
            clothestype.setTypeimg(newName);
            try {
                closvc.save(clothestype);
                FileUtils.copyFile(clothestimg, savefile);
                clothestimg = null;
                clothestimgFileName = null;
                return SUCCESS;
            } catch (Exception e) {
                ActionContext.getContext().put("message", "添加失败");
            }
        }
        return ERROR;
    }

    public String downloadExcel() {
        return SUCCESS;
    }

    public InputStream getInputStream() throws IOException {
        List<Object[]> list = closvc.findAllType();
        List<Brand> brands = (List<Brand>) ActionContext.getContext().getSession().get("brands");
        HSSFWorkbook workbook = PolExportUtils.createSh(list, brands);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        workbook.write(os);
        byte[] fileContent = os.toByteArray();
        return new ByteArrayInputStream(fileContent);
    }

    private JSONObject msg = new JSONObject();

    public JSONObject getMsg() {
        return msg;
    }

    public String loadClothestypes() {
        msg.clear();
        List list = closvc.findAll(crowdid);
        if (!list.isEmpty()) {
            msg.put("success", true);
            msg.put("data", JSONArray.parseArray(JSON.toJSONString(list)));
            return SUCCESS;
        }
        msg.put("success", false);
        return ERROR;
    }

}
