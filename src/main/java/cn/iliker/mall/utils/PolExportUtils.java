package cn.iliker.mall.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddressList;

import cn.iliker.mall.entity.Brand;

public final class PolExportUtils {

    public static HSSFWorkbook createSh(List<Object[]> list, List<Brand> brands) {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFCellStyle cs = wb.createCellStyle();
        HSSFFont f = wb.createFont();
        f.setFontHeightInPoints((short) 12);
        f.setColor(HSSFColor.RED.index);
        f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        cs.setFont(f);
        List<String> brandList = brands.stream().map(brand -> brand.getBrandId() + ":" + brand.getBrandName()).collect(Collectors.toList());
        try {
            for (Object[] objects : list) {
                //第二步创建sheet
                String name = objects[0] + "#" + objects[1];
                HSSFSheet sheet = wb.createSheet(name);
                //第三步创建行row:添加表头0行
                HSSFRow row = sheet.createRow(0);
                HSSFCellStyle style = wb.createCellStyle();
                style.setAlignment(HSSFCellStyle.ALIGN_CENTER); //居中

                HSSFCell cell0 = row.createCell(0);
                cell0.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell0.setCellValue("商品类别");
                cell0.setCellStyle(cs);

                HSSFCell cell1 = row.createCell(1);
                cell1.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell1.setCellValue("品牌");
                cell1.setCellStyle(style);

                HSSFCell cell2 = row.createCell(2);
                cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell2.setCellValue("商品编号");
                cell2.setCellStyle(style);

                HSSFCell cell3 = row.createCell(3);
                cell3.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell3.setCellValue("商品名称");
                cell3.setCellStyle(style);

                HSSFCell cell4 = row.createCell(4);
                cell4.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell4.setCellValue("商品详细");
                cell4.setCellStyle(style);

                HSSFCell cell5 = row.createCell(5);
                cell5.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                cell5.setCellValue("单价");
                cell5.setCellStyle(cs);

                HSSFCell cell6 = row.createCell(6);
                cell6.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell6.setCellValue("颜色");
                cell6.setCellStyle(style);

                HSSFCell cell7 = row.createCell(7);
                cell7.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell7.setCellValue("尺寸");
                cell7.setCellStyle(style);

                HSSFCell cell8 = row.createCell(8);
                cell8.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                cell8.setCellValue("库存数量");
                cell8.setCellStyle(style);

                HSSFCell cell9 = row.createCell(9);
                cell9.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                cell9.setCellValue("分成比例");
                cell9.setCellStyle(cs);

                HSSFCell cell10 = row.createCell(10);
                cell10.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell10.setCellValue("商品图片");
                cell10.setCellStyle(style);

        /*设置下拉项*/
                CellRangeAddressList addressList = new CellRangeAddressList(
                        1,
                        200,
                        0,
                        0);
                DVConstraint dvConstraint =
                        DVConstraint.createExplicitListConstraint((objects[2] + "").split(","));

                HSSFDataValidation dataValidation = new HSSFDataValidation(addressList, dvConstraint);
                dataValidation.createPromptBox("输入提示", "请从下拉列表中选择！");
                dataValidation.setSuppressDropDownArrow(false);

                CellRangeAddressList addressList2 = new CellRangeAddressList(
                        1,
                        200,
                        1,
                        1);

                String[] brandArray = brandList.toArray(new String[brandList.size()]);
                DVConstraint dvConstraint2 =
                        DVConstraint.createExplicitListConstraint(brandArray);

                HSSFDataValidation dataValidation2 = new HSSFDataValidation(addressList2, dvConstraint2);
                dataValidation2.createPromptBox("输入提示", "下拉选择！");
                dataValidation2.setSuppressDropDownArrow(false);

                CellRangeAddressList addressList3 = new CellRangeAddressList(
                        1,
                        200,
                        9,
                        9);
                String[] spit = new String[]{"0", "0.1", "0.2", "0.3", "0.4", "0.5", "0.6", "0.7", "0.8", "0.9"};
                DVConstraint dvConstraint3 =
                        DVConstraint.createExplicitListConstraint(spit);

                HSSFDataValidation dataValidation3 = new HSSFDataValidation(addressList3, dvConstraint3);
                dataValidation3.createPromptBox("输入提示", "请从下拉列表中选择！");
                dataValidation3.setSuppressDropDownArrow(false);

                wb.getSheet(name).addValidationData(dataValidation);
                wb.getSheet(name).addValidationData(dataValidation2);
                wb.getSheet(name).addValidationData(dataValidation3);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wb;
    }
}
