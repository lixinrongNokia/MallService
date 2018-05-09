package cn.iliker.mall.control;

import cn.iliker.mall.entity.*;
import cn.iliker.mall.entity.Collection;
import cn.iliker.mall.error.DelError;
import cn.iliker.mall.interceptor.Permission;
import cn.iliker.mall.service.IBrandSvc;
import cn.iliker.mall.service.ICrowdSvc;
import cn.iliker.mall.service.IGoodsSvc;
import cn.iliker.mall.service.ISearchSvc;
import cn.iliker.mall.utils.DateParseUtils;
import cn.iliker.mall.utils.UrlTools;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.KeyProperty;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.util.ServletContextAware;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;
import java.util.*;

import static cn.iliker.mall.utils.DateParseUtils.create_Batch_no;
import static cn.iliker.mall.utils.Rename_PIC.renameUtil;

public class GoodsManger extends ActionSupport implements ServletContextAware, ServletResponseAware {
    private final Map<Integer, SheetData> picdata = new HashMap<>();
    private final List<Goods> goodArray = new ArrayList<>();
    private IGoodsSvc goodsvc;
    private Goods goods;
    private List<File> goodimg;//封面图
    private List<String> goodimgFileName;//封面图名
    private List<File> illustrations;//详情图
    private List<String> illustrationsFileName;//详情图名
    private File excel;
    private String excelFileName;
    private int goodid;
    private String divided_into;
    private ICrowdSvc crowdSvc;
    private IBrandSvc brandSvc;
    private int offset = 1;
    private int totalPage;
    private int totalSize;
    private String goodCode = "";
    private String[] markname;
    private String phone;
    private Brand brand;
    private List<Goods> goodses;
    private ISearchSvc searchSvc;
    private String search_where;
    private String search_value;
    private int userId;
    @KeyProperty("stockId")
    private Set<StockInfo> stockInfoSet = new LinkedHashSet<>();

    public Set<StockInfo> getStockInfoSet() {
        return stockInfoSet;
    }

    public void setStockInfoSet(Set<StockInfo> stockInfoSet) {
        this.stockInfoSet = stockInfoSet;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setSearch_where(String search_where) {
        this.search_where = search_where;
    }

    public void setSearch_value(String search_value) {
        this.search_value = search_value;
    }

    public void setSearchSvc(ISearchSvc searchSvc) {
        this.searchSvc = searchSvc;
    }

    private JSONObject msg = new JSONObject();

    public List<File> getIllustrations() {
        return illustrations;
    }

    public void setIllustrations(List<File> illustrations) {
        this.illustrations = illustrations;
    }

    public List<String> getIllustrationsFileName() {
        return illustrationsFileName;
    }

    public void setIllustrationsFileName(List<String> illustrationsFileName) {
        this.illustrationsFileName = illustrationsFileName;
    }

    public JSONObject getMsg() {
        return msg;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public List<Goods> getGoodses() {
        return goodses;
    }

    public void setGoodses(List<Goods> goodses) {
        this.goodses = goodses;
    }

    public String getDivided_into() {
        return divided_into;
    }

    public void setDivided_into(String divided_into) {
        this.divided_into = divided_into;
    }

    public String[] getMarkname() {
        return markname;
    }

    public void setMarkname(String[] markname) {
        this.markname = markname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setBrandSvc(IBrandSvc brandSvc) {
        this.brandSvc = brandSvc;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public void setCrowdSvc(ICrowdSvc crowdSvc) {
        this.crowdSvc = crowdSvc;
    }

    public String getGoodCode() {
        return goodCode;
    }

    public void setGoodCode(String goodCode) {
        this.goodCode = goodCode;
    }

    public int getGoodid() {
        return goodid;
    }

    public void setGoodid(int goodid) {
        this.goodid = goodid;
    }

    public List<File> getGoodimg() {
        return goodimg;
    }

    public void setGoodimg(List<File> goodimg) {
        this.goodimg = goodimg;
    }

    public List<String> getGoodimgFileName() {
        return goodimgFileName;
    }

    public void setGoodimgFileName(List<String> goodimgFileName) {
        this.goodimgFileName = goodimgFileName;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public void setGoodsvc(IGoodsSvc goodsvc) {
        this.goodsvc = goodsvc;
    }

    public File getExcel() {
        return excel;
    }

    public void setExcel(File excel) {
        this.excel = excel;
    }

    public String getExcelFileName() {
        return excelFileName;
    }

    public void setExcelFileName(String excelFileName) {
        this.excelFileName = excelFileName;
    }

    @Permission(module = "product", privilege = "insert")
    public String launchGood() {
        if (goodimg == null || goodimg.isEmpty()) {
            ActionContext.getContext().put("message", "请添加图片");
            return ERROR;
        }

        List<String> filenames = renameUtil(goodimgFileName);
        StringBuilder buf = new StringBuilder();
        for (String str : filenames) {
            buf.append(str).append("#");
        }
        buf.deleteCharAt(buf.length() - 1);
        goods.setImgpath(buf.toString());

        List<String> illustrationsName = null;
        if (illustrations != null) {
            illustrationsName = renameUtil(illustrationsFileName);
            StringBuilder buf2 = new StringBuilder();
            for (String str : illustrationsName) {
                buf2.append(str).append("#");
            }
            buf2.deleteCharAt(buf2.length() - 1);
            goods.setIllustrations(buf2.toString());
        }
        for (StockInfo stockInfo : stockInfoSet) {
            /*Set<StockItem> itemSet = stockInfo.getStockItems();
            for (StockItem item : itemSet) {
                item.setStockInfo(stockInfo);
            }
            stockInfo.setGoods(goods);*/
            goods.getStockInfoSet().add(stockInfo);
        }
        boolean isadd = goodsvc.save(goods);
        if (isadd) {
            try {
                if (illustrations != null) {
                    filenames.addAll(illustrationsName);
                    goodimg.addAll(illustrations);
                }
                for (int i = 0; i < goodimg.size(); i++) {
                    FileUtils.copyFile(goodimg.get(i), new File(UrlTools.GOODSDIR, filenames.get(i)));
                }
                goodimg.clear();
                goodimg = null;
                filenames.clear();
                goodCode = null;
                goods = null;
                return SUCCESS;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ActionContext.getContext().put("message", "添加失败");
        return ERROR;
    }

    public String queryBrands() {
        msg.clear();
        try {
            List<Brand> brands = brandSvc.findAll();
            if (brands != null && !brands.isEmpty()) {
                msg.put("brands", JSONArray.parseArray(JSON.toJSONString(brands)));
                return SUCCESS;
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return NONE;
    }

    @Permission(module = "product", privilege = "view")
    public String queryGoods() {
        if (search_where != null && search_value != null) {
            ServletActionContext.getRequest().getSession().setAttribute("search_where", search_where);
            ServletActionContext.getRequest().getSession().setAttribute("search_value", search_value);
        }
        if ("0".equals(search_where) && "0".equals(search_value)) {
            ServletActionContext.getRequest().getSession().removeAttribute("search_where");
            ServletActionContext.getRequest().getSession().removeAttribute("search_value");
        }
        String search_where = (String) ServletActionContext.getRequest().getSession().getAttribute("search_where");
        String search_value = (String) ServletActionContext.getRequest().getSession().getAttribute("search_value");
        GeneralList<Goods> goodList = goodsvc.findAll(offset, 10, search_where, search_value);
        if (goodList != null) {
            totalPage = goodList.getTotalPage();
            totalSize = goodList.getTotalSize();
            ActionContext.getContext().put("goodList", goodList);
            return SUCCESS;
        }
        return ERROR;
    }

    public String findGoods() {
        Goods good = goodsvc.findById(goodid);
        if (good != null) {
            ActionContext.getContext().put("good", good);
            return SUCCESS;
        }
        return ERROR;
    }

    @Permission(module = "product", privilege = "visible")
    public String visibleGoods() {
        Goods goods1 = goodsvc.findById(goods.getId());
        if (goods1 != null) {
            goods1.setVisible(goods.getVisible());
            if (goodsvc.updateGood(goods1)) {
                return SUCCESS;
            }
        }
        return NONE;
    }

    @Permission(module = "product", privilege = "update")
    public String updateGood() {
        Goods good = goodsvc.findById(goods.getId());
        String oldImgpath = good.getImgpath();
        if (goods.getGoodCode() != null) {
            good.setGoodCode(goods.getGoodCode());
        }
        good.setClothestype(goods.getClothestype());
        good.setBrand(goods.getBrand());
        if (goods.getGoodName() != null) {
            good.setGoodName(goods.getGoodName());
        }
        if (goods.getSizes() != null) {
            good.setSizes(goods.getSizes());
        }
        if (goods.getColors() != null) {
            good.setColors(goods.getColors());
        }
        if (goods.getGoodsDesc() != null) {
            good.setGoodsDesc(goods.getGoodsDesc());
        }
        if (goods.getPrice() != null) {
            good.setPrice(goods.getPrice());
        }
        good.getStockInfoSet().clear();
        for (StockInfo stockInfo : goods.getStockInfoSet()) {
            /*Set<StockItem> itemSet = stockInfo.getStockItems();
            for (StockItem item : itemSet) {
                item.setStockInfo(stockInfo);
            }
            stockInfo.setGoods(good);*/
            good.getStockInfoSet().add(stockInfo);
        }
        List<String> list = findimg(oldImgpath, markname);
        StringBuilder buf = new StringBuilder();
        List<String> ills = null;
        if (illustrations != null && !illustrations.isEmpty()) {
            ills = renameUtil(illustrationsFileName);
            StringBuilder buf2 = new StringBuilder();
            for (String str : ills) {
                buf2.append(str).append("#");
            }
            buf2.deleteCharAt(buf2.length() - 1);
            good.setIllustrations(buf2.toString());
        }
        if (goodimg != null && !goodimg.isEmpty()) {
            List<String> filenames = renameUtil(goodimgFileName);
            if (!list.isEmpty()) {
                filenames.addAll(list);
            }
            for (String str : filenames) {
                buf.append(str).append("#");
            }
            buf.deleteCharAt(buf.length() - 1);
            good.setImgpath(buf.toString());

            boolean isupdate = goodsvc.updateGood(good);
            if (isupdate) {
                try {
                    List<String> delfiles = findDelimg(oldImgpath, markname);
                    if (!delfiles.isEmpty()) {
                        for (String delname : delfiles) {
                            String str = UrlTools.GOODSDIR + "/" + delname;
                            File delfile = new File(str.replace("\\", "/"));
                            delfile.delete();
                        }
                    }
                    if (illustrations != null) {
                        goodimg.addAll(illustrations);
                        filenames.addAll(ills);
                    }
                    for (int i = 0; i < goodimg.size(); i++) {
                        FileUtils.copyFile(goodimg.get(i), new File(UrlTools.GOODSDIR, filenames.get(i)));
                    }
                    goodimg = null;
                    filenames.clear();
                    markname = null;
                    goods = null;
                    return SUCCESS;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            if (!list.isEmpty()) {
                for (String str : list) {
                    buf.append(str).append("#");
                }
                buf.deleteCharAt(buf.length() - 1);
                good.setImgpath(buf.toString());
            }

            List<String> hashString = findDelimg(oldImgpath, markname);

            boolean isupdate = goodsvc.updateGood(good);
            if (isupdate) {
                if (!hashString.isEmpty()) {
                    for (String delname : hashString) {
                        String str = UrlTools.GOODSDIR + "/" + delname;
                        File delfile = new File(str.replace("\\", "/"));
                        delfile.delete();

                    }
                }
                if (illustrations != null) {
                    try {
                        for (int i = 0; i < illustrations.size(); i++) {
                            FileUtils.copyFile(illustrations.get(i), new File(UrlTools.GOODSDIR, ills.get(i)));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                markname = null;
                return SUCCESS;
            }
        }
        ActionContext.getContext().put("errormsg", "发生错误");
        return ERROR;
    }

    /**
     * 对原图片字符串检查修改，添加情况，找到没有删除修改的图片
     */
    private static List<String> findimg(String oldImgpath, String[] markname) {
        List<String> hashString = new ArrayList<>();
        String[] imgstr;
        if (oldImgpath != null && oldImgpath.contains("#")) {
            imgstr = oldImgpath.split("#");
        } else
            imgstr = new String[]{oldImgpath};
        for (String imgname : markname) {
            for (String findName : imgstr) {
                if (imgname.equals(findName)) {
                    hashString.add(imgname);
                    break;
                }
            }
        }
        return hashString;
    }

    /**
     * 对原图片字符串检查删除情况找到被删除移除的图片
     */
    private static List<String> findDelimg(String oldImgpath, String[] markname) {
        List<String> hashString = new ArrayList<>();
        String[] imgstr;
        if (oldImgpath.contains("#")) {
            imgstr = oldImgpath.split("#");
        } else
            imgstr = new String[]{oldImgpath};
        for (String findName : imgstr) {
            boolean isequals = false;
            for (String imgname : markname) {
                if (findName.equals(imgname)) {
                    isequals = true;
                    break;
                }
            }
            if (!isequals) {
                hashString.add(findName);
            }
        }
        return hashString;
    }

    public String regGoodCode() throws IOException {
        List list = goodsvc.findByGoodCode(goodCode);
        ServletActionContext.getResponse().setContentType(
                "text/html;charset=utf-8");
        PrintWriter out = ServletActionContext.getResponse().getWriter();
        if (list != null && list.size() > 0) {
            out.print("已入库");
            out.flush();
            out.close();
            return SUCCESS;
        }
        out.print("");
        out.flush();
        out.close();
        return NONE;
    }

    /*删除商品*/
    public String delGoods() {
        Goods good = goodsvc.findById(goodid);
        if (good != null) {
            final String imgs = good.getIllustrations();
            try {
                goodsvc.delete(good);
                new Thread(() -> {
                    String[] delfiles;
                    if (imgs.contains("#")) {
                        delfiles = imgs.split("#");
                    } else {
                        delfiles = new String[]{imgs};
                    }
                    for (String delF : delfiles) {
                        String str = UrlTools.GOODSDIR + "/" + delF;
                        File file = new File(str.replace("\\", "/"));
                        file.delete();
                    }
                }).start();
            } catch (DelError e) {
                return SUCCESS;
            }
            return SUCCESS;
        }
        return ERROR;
    }

    public String editDivided_into() throws IOException {
        int i = goodsvc.editDivided_into(divided_into, goodid);
        ServletActionContext.getResponse().setContentType(
                "text/html;charset=utf-8");
        PrintWriter out = ServletActionContext.getResponse().getWriter();
        if (i > 0) {
            out.print("更改成功");
            out.flush();
            out.close();
            return NONE;
        }
        out.print("更改失败");
        out.flush();
        out.close();
        return NONE;
    }

    /*
    导入excel上传商品
     */

    public String batchImport() throws IOException, InterruptedException {
        if (!goodArray.isEmpty()) goodArray.clear();
        if (goodses != null) goodses.clear();
        InputStream is = new FileInputStream(excel);
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        if (regExportFile(excel, excelFileName)) {
            GetPictureDataThread getPictureDataThread = new GetPictureDataThread(hssfWorkbook);
            getPictureDataThread.start();
            getPictureDataThread.join();
            if (!picdata.isEmpty()) {
                ReadExcelThread readExcelThread = new ReadExcelThread(hssfWorkbook);
                readExcelThread.start();
                readExcelThread.join();
                if (!goodArray.isEmpty()) {
                    String url = create_Batch_no();
                    File temp = new File(servletcontext.getRealPath(url));
                    if (!temp.exists()) temp.mkdirs();
                    new Thread(() -> copyPic(temp.getAbsolutePath())).start();
                    ActionContext.getContext().getSession().put("tempImgDir", url);
                    ActionContext.getContext().getSession().put("goodList", goodArray);
                    return SUCCESS;
                }
            }
        }
        ActionContext.getContext().put("errorMsg", "批量添加失败");
        return ERROR;
    }

    //拷贝图片到服务器硬盘
    private void copyPic(String imgurl) {
        for (Map.Entry<Integer, SheetData> entry : picdata.entrySet()) {
            SheetData sheetData = entry.getValue();
            Map<Integer, Row> rowMap = sheetData.getRows();
            for (Map.Entry<Integer, Row> integerRowEntry : rowMap.entrySet()) {
                List<HSSFPictureData> hssList = integerRowEntry.getValue().getPictureDatas();
                List<String> picNames = integerRowEntry.getValue().getPicNames();
                int len = hssList.size();
                for (int i = 0; i < len; i++) {
                    try {
                        FileOutputStream out = new FileOutputStream(imgurl + "/" + picNames.get(i));
                        out.write(hssList.get(i).getData());
                        out.flush();
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        picdata.clear();
    }

    private ServletContext servletcontext;
    private HttpServletResponse httpServletResponse;

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletcontext = servletContext;
    }

    @Override
    public void setServletResponse(HttpServletResponse httpServletResponse) {
        this.httpServletResponse = httpServletResponse;
    }

    //执行GetPictureDataRunnable的线程
    class GetPictureDataThread extends Thread {
        private final GetPictureDataRunnable getPictureDataRunnable;

        public GetPictureDataThread(HSSFWorkbook hssfWorkbook) {
            getPictureDataRunnable = new GetPictureDataRunnable(hssfWorkbook);
        }

        @Override
        public void run() {
            getPictureDataRunnable.run();
        }
    }

    //执行ReadExcelRunnable的线程
    class ReadExcelThread extends Thread {
        private final ReadExcelRunnable readExcelRunnable;

        public ReadExcelThread(HSSFWorkbook hssfWorkbook) {
            readExcelRunnable = new ReadExcelRunnable(hssfWorkbook);
        }

        @Override
        public void run() {
            readExcelRunnable.run();
        }
    }

    //读取excel任务
    class ReadExcelRunnable implements Runnable {
        private final HSSFWorkbook hssfWorkbook;

        public ReadExcelRunnable(HSSFWorkbook hssfWorkbook) {
            this.hssfWorkbook = hssfWorkbook;
        }

        @Override
        public void run() {
            readXls(hssfWorkbook);
        }
    }

    //获取图片任务
    class GetPictureDataRunnable implements Runnable {
        private final HSSFWorkbook hssfWorkbook;

        public GetPictureDataRunnable(HSSFWorkbook hssfWorkbook) {
            this.hssfWorkbook = hssfWorkbook;
        }

        @Override
        public void run() {
            getPIC(hssfWorkbook);
        }
    }

    /*读取属性*/
    private void readXls(HSSFWorkbook hssfWorkbook) {
        StringBuilder builder = new StringBuilder();

        // 循环工作表Sheet
        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            SheetData sheetData = picdata.get(numSheet);
            if (hssfSheet == null || sheetData == null) {
                continue;
            }
            String[] sheetName = hssfSheet.getSheetName().split("#");
            Crowd crowd = new Crowd(Integer.parseInt(sheetName[0]), sheetName[1]);
            // 循环行Row
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                Row row = sheetData.getRows().get(rowNum);
                if (hssfRow == null || row == null) {
                    continue;
                }
                Goods goods = new Goods();
                if (row.getPicNames().size() > 1) {
                    for (String string : row.getPicNames()) {
                        builder.append(string);
                        builder.append("#");
                    }
                    builder.deleteCharAt(builder.length() - 1);
                    goods.setImgpath(row.getPicNames().get(0));
                    goods.setIllustrations(builder.toString());
                } else {
                    goods.setImgpath(row.getPicNames().get(0));
                    goods.setIllustrations(row.getPicNames().get(0));
                }
                builder.setLength(0);
                HSSFCell cell = hssfRow.getCell(0);
                if (cell != null) {
                    Clothestype clothestype = new Clothestype();
                    clothestype.setCrowd(crowd);
                    String[] clothestypes = cell.getStringCellValue().split(":");
                    clothestype.setId(Integer.parseInt(clothestypes[0]));
                    clothestype.setName(clothestypes[1]);
                    goods.setClothestype(clothestype);
                }

                cell = hssfRow.getCell(1);
                if (cell != null) {
                    String[] brands = cell.getStringCellValue().split(":");
                    Brand brand = new Brand();
                    brand.setBrandId(brands[0]);
                    brand.setBrandName(brands[1]);
                    goods.setBrand(brand);
                }
                goods.setMarketDate(DateParseUtils.getFormatDate("yyyy-MM-dd"));
                goods.setSales(0);

                cell = hssfRow.getCell(2);
                if (cell != null) {
                    goods.setGoodCode(conversion(cell));
                }
                cell = hssfRow.getCell(3);
                if (cell != null) {
                    goods.setGoodName(cell.getStringCellValue());
                }
                cell = hssfRow.getCell(4);
                if (cell != null) {
                    goods.setGoodsDesc(cell.getStringCellValue());
                }
                cell = hssfRow.getCell(5);
                if (cell != null) {
                    goods.setPrice(cell.getNumericCellValue());
                }

                cell = hssfRow.getCell(6);
                if (cell != null) {
                    goods.setColors(cell.getStringCellValue());
                }

                cell = hssfRow.getCell(7);
                if (cell != null) {
                    goods.setSizes(cell.getStringCellValue());
                }

                cell = hssfRow.getCell(8);
                if (cell != null) {
                    goods.setStock((int) cell.getNumericCellValue());
                }

                cell = hssfRow.getCell(9);
                if (cell != null) {
                    goods.setDivided_into(cell.getNumericCellValue());
                }
                goodArray.add(goods);
            }
        }
    }

    private static String conversion(HSSFCell cell) {
        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_NUMERIC:   //数字
                double doubleValue = cell.getNumericCellValue();
                String str = doubleValue + "";
                if (str.contains(".0")) {
                    str = ((int) doubleValue) + "";
                }
                return str;
            case HSSFCell.CELL_TYPE_STRING:    //字符串
                return cell.getStringCellValue();
            case HSSFCell.CELL_TYPE_BOOLEAN:   //布尔
                break;
            case HSSFCell.CELL_TYPE_BLANK:     // 空值
                break;
            case HSSFCell.CELL_TYPE_FORMULA:   // 公式
                break;
            case HSSFCell.CELL_TYPE_ERROR:     // 故障
        }
        return "";
    }

    //获取图片，拼接图片名
    private void getPIC(HSSFWorkbook hssfWorkbook) {
        List pictures = hssfWorkbook.getAllPictures();
        StringBuilder builder = new StringBuilder();
        if (pictures.size() != 0) {
            for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
                Sheet sheet = hssfWorkbook.getSheetAt(numSheet);
                HSSFPatriarch patriarch = (HSSFPatriarch) sheet.getDrawingPatriarch();
                if (patriarch == null) {
                    continue;
                }
                SheetData sheetData = new SheetData();
                picdata.put(numSheet, sheetData);
                for (HSSFShape shape : patriarch.getChildren()) {
                    HSSFClientAnchor anchor = (HSSFClientAnchor) shape.getAnchor();
                    if (shape instanceof HSSFPicture) {
                        HSSFPicture pic = (HSSFPicture) shape;
                        int pictureIndex = pic.getPictureIndex() - 1;
                        HSSFPictureData picData = (HSSFPictureData) pictures.get(pictureIndex);
                        String ext = picData.suggestFileExtension();
                        int cour = anchor.getRow1();
                        Row row = sheetData.getRows().get(cour);
                        if (row == null) {
                            row = new Row();
                            sheetData.getRows().put(cour, row);
                        }
                        String picType = "";
                        if ("jpeg".equals(ext)) picType = ".jpg";
                        if ("png".equals(ext)) picType = ".png";
                        row.getPicNames().add(renameUtil() + picType);
                        row.getPictureDatas().add(picData);
                    }
                }
                builder.setLength(0);
            }
        }
    }

    private boolean regExportFile(File file, String excelFileName) {
        if (file == null) {
            return false;
        }
        excelFileName = excelFileName.substring(excelFileName.lastIndexOf(".") + 1, excelFileName.length());
        return "xls".equals(excelFileName) || "xlsx".equals(excelFileName);
    }

    public String ultimateSubmit() {
        if (goodses != null) {
            goodArray.clear();
            ActionContext.getContext().getSession().remove("goodList");
            ActionContext.getContext().getSession().put("goodList", goodses);
        }
        try {
            goodsvc.addBuyBean(goodses);
            goodses.clear();
            ActionContext.getContext().getSession().remove("goodList");
            Map<String, Object> session = ActionContext.getContext().getSession();
            new Thread(() -> {
                moveImgToDir(session);
            }).start();
            return SUCCESS;
        } catch (SQLException e) {
            ActionContext.getContext().put("errorMsg", e.getMessage());
        }
        return ERROR;
    }

    private void moveImgToDir(Map<String, Object> session) {
        try {
            String tempImgDir = (String) session.get("tempImgDir");
            File tempDir = new File(servletcontext.getRealPath(tempImgDir));
            File[] files = tempDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.renameTo(new File(UrlTools.GOODSDIR, file.getName()));
                }
            }
            session.remove("tempImgDir");
            tempDir.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String keyword;
    private int firstResult;
    private int maxResult;

    public int getMaxResult() {
        return maxResult;
    }

    public void setMaxResult(int maxResult) {
        this.maxResult = maxResult;
    }

    public int getFirstResult() {
        return firstResult;
    }

    public void setFirstResult(int firstResult) {
        this.firstResult = firstResult;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }


    public String searchGoods() throws IOException {
        List<Goods> list = searchSvc.searchObject(firstResult, maxResult, new String[]{"id", "goodCode", "goodName", "goodsDesc", "price", "imgpath", "illustrations", "sales", "divided_into", "colors", "sizes"}, keyword, Goods.class);
        if (list != null && !list.isEmpty()) {
            JSONArray jsonArray = new JSONArray();
            for (Goods goods : list) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", goods.getId());
                jsonObject.put("goodCode", goods.getGoodCode());
                jsonObject.put("goodName", goods.getGoodName());
                jsonObject.put("goodsDesc", goods.getGoodsDesc());
                jsonObject.put("price", goods.getPrice());
                jsonObject.put("imgpath", goods.getImgpath());
                jsonObject.put("illustrations", goods.getIllustrations());
                jsonObject.put("sales", goods.getSales());
                jsonObject.put("divided_into", goods.getDivided_into());
                jsonObject.put("colors", goods.getColors());
                jsonObject.put("sizes", goods.getSizes());
                jsonArray.add(jsonObject);
            }
            httpServletResponse.setCharacterEncoding("UTF-8");
            PrintWriter writer = httpServletResponse.getWriter();
            writer.write(jsonArray.toJSONString());
            writer.flush();
            writer.close();
        }
        return NONE;
    }

    private String property;
    private String resJSON;

    public String getResJSON() {
        return resJSON;
    }

    public void setResJSON(String resJSON) {
        this.resJSON = resJSON;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getProperty() {
        return property;
    }

    public String getGoodsAsList() {
        if (property != null) {
            com.alibaba.fastjson.JSONArray jsonArray = goodsvc.loadLimit("Goods goods", new String[]{"id", "goodCode", "goodName", "goodsDesc", "price", "sales", "imgpath", "illustrations", "colors", "sizes", "divided_into"}, "goods.visible=true", null, null, null, "goods." + property + " DESC");
            if (jsonArray != null) {
                setResJSON(jsonArray.toString());
                return SUCCESS;
            }
        }
        return NONE;
    }

    public String getGoodsById() {
        msg.clear();
        if (goodid > 0) {
            com.alibaba.fastjson.JSONArray jsonArray = goodsvc.loadLimit("Goods goods", new String[]{"id", "goodCode", "goodName", "goodsDesc", "price", "sales", "imgpath", "illustrations", "colors", "sizes", "divided_into"}, "goods.id=?", new Object[]{goodid}, null, null, null);
            if (jsonArray != null) {
                msg.put("goods", jsonArray.get(0));
                msg.put("success", true);
                return SUCCESS;
            }
        }
        msg.put("success", false);
        return ERROR;
    }

    public String loadClassify() {
        msg.clear();
        List<Crowd> crowds = crowdSvc.findAll();
        try {
            msg.put("data", JSONArray.parseArray(JSON.toJSONString(crowds)));
            msg.put("success", true);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
        }
        msg.put("success", false);
        return ERROR;
    }

    public String getCollectionByUserIds() {
        msg.clear();
        GeneralList<Collection> collectionGeneralList = goodsvc.findByUserId(userId, offset, 10);
        if (collectionGeneralList != null) {
            msg.put("totalSize", collectionGeneralList.getTotalSize());
            msg.put("totalPage", collectionGeneralList.getTotalPage());
            JSONArray jsonArray = new JSONArray();
            for (Collection collection : collectionGeneralList.getList()) {
                JSONObject collJson = new JSONObject();
                JSONObject goodsJSON = new JSONObject();
                Goods goods = collection.getGoods();
                goodsJSON.put("id", goods.getId());
                goodsJSON.put("goodCode", goods.getGoodCode());
                goodsJSON.put("goodName", goods.getGoodName());
                goodsJSON.put("goodsDesc", goods.getGoodsDesc());
                goodsJSON.put("price", goods.getPrice());
                goodsJSON.put("imgpath", goods.getImgpath());
                goodsJSON.put("illustrations", goods.getIllustrations());
                goodsJSON.put("sales", goods.getSales());
                goodsJSON.put("divided_into", goods.getDivided_into());
                goodsJSON.put("colors", goods.getColors());
                goodsJSON.put("sizes", goods.getSizes());
                goodsJSON.put("stock", goods.getStock());
                collJson.put("colltime", collection.getColltime());
                collJson.put("color", collection.getColor());
                collJson.put("size", collection.getSize());
                collJson.put("id", collection.getId());
                collJson.put("goods", goodsJSON);
                jsonArray.add(collJson);
            }
            msg.put("collections", jsonArray);
            System.out.println(msg.toJSONString());
            return SUCCESS;
        }
        return NONE;
    }

    public String loadBrands() {
        msg.clear();
        List list = brandSvc.findAll();
        if (list != null && !list.isEmpty()) {
            msg.put("brands", JSONArray.parseArray(JSON.toJSONString(list)));
            msg.put("success", true);
            return SUCCESS;
        }
        msg.put("success", false);
        return ERROR;
    }

    /*获取对应品牌对应商品编号的商品*/
    public String getAttrByGoodsCode() {
        msg.clear();
        Goods retGoods = goodsvc.findGoodsByGoodsCode(goods);
        if (retGoods != null) {
            msg = JSON.parseObject(JSON.toJSONString(retGoods));
            return SUCCESS;
        }
        return NONE;
    }
}
