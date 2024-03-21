package com.lplb.framework.poi;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.log.Log;
import cn.hutool.poi.excel.ExcelReader;
import com.alibaba.fastjson.JSON;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *   @author Ray-zy
 *   @since 2021/7/9 09:38
 **/
public class ExcelTemplateUtil {
    private static final Log log = Log.get();
//
//    public static void main(String[] args) {
//        ExcelWriter writer = new ExcelWriter("K:/test.xlsx");
//        StyleSet styleSet = writer.getStyleSet();
//        CellStyle cellStyle = styleSet.getCellStyle();
//        String[] arr = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
//        int index = 0;
//
//        writer.addHeaderAlias("name","姓名");
//        writer.addHeaderAlias("sex","性别");
//        writer.addHeaderAlias("usl","密级");
//        List<Map<String,Object>> rows = new ArrayList<>();
//        Map<String,Object> row = new HashMap<>();
//        row.put("name","张三");
//        row.put("sex","女");
//        row.put("usl","机密");
//        rows.add(row);
//        // 合并单元格后的标题行，使用默认标题样式
//        writer.merge(2, "人员信息");
//        // 一次性写出内容，使用默认样式，强制输出标题
//        writer.write(rows, true);
//        // 关闭writer，释放内存
//        writer.close();
//    }

    public static void createExcelTemplate(Workbook wb, String[] headers,
                                           List<String[]> downData, String[] downRows, String sheetName,
                                           String version, int hideIndex,List<String[]> entityData,boolean protect){
        //        HSSFWorkbook wb = new HSSFWorkbook();//创建工作薄
        //表头样式
        CellStyle headerStyle = commonHeaderStyle(wb);
        //新建sheet
//        HSSFSheet sheet1 = wb.createSheet("Sheet1");
        Sheet sheet1 = wb.createSheet(sheetName);

        sheet1.setDefaultRowHeight((short) 330);
//        HSSFSheet sheet2 = wb.createSheet("Sheet2");
        Sheet sheet2 = wb.createSheet(sheetName+"aux");
        sheet2.protectSheet(RandomUtil.randomString(10));
//        HSSFSheet sheet3 = wb.createSheet("Sheet3");
        //模板说明
        Row commentRow = sheet1.createRow(0);//
        commentRow.setHeight((short) 1080);
        Cell cellOne = commentRow.createCell(0);
        cellOne.setCellValue("模板说明：请不要进行插列操作，请勿修改模板格式！（版本："+ version +"）\r\n*符号标识必填");
        cellOne.setCellStyle(commonCommentStyle(wb));
        CellRangeAddress region = new CellRangeAddress(0, 0, 0, headers.length-1);
        sheet1.addMergedRegion(region);
        //生成sheet1内容
        Row rowSecond = sheet1.createRow(1);//第一个sheet的第一行为标题
        //写标题
        for(int i=0;i<headers.length;i++){
            Cell cell = rowSecond.createCell(i); //获取第一行的每个单元格
            sheet1.setColumnWidth(i, 4000); //设置每列的列宽
            cell.setCellStyle(headerStyle); //加样式

            HSSFRichTextString ts;
            if(headers[i].contains(":>")){//有注释
                String[] headerArr = headers[i].split(":>");
                ts = new HSSFRichTextString(headerArr[0]);
                initCellComment(false,sheet1,cell,headerArr[1]);
            }else{
                ts = new HSSFRichTextString(headers[i]);
            }

            ts.applyFont(commonFont(wb,12,IndexedColors.BLACK.getIndex()));
            //判断字段是否必填
            if(headers[i].startsWith("*")){
                ts.applyFont(0,1,commonFont(wb,12,IndexedColors.RED.getIndex()));
            }
            cell.setCellValue(ts); //往单元格里写数据
        }
        if (ObjectUtil.isNotEmpty(entityData)) {
            for (int i = 0; i < entityData.size(); i++) {
                Row row = sheet1.createRow(i+2);
                String[] itemData = entityData.get(i);
                for (int i1 = 0; i1 < itemData.length; i1++) {
                    Cell cell = row.createCell(i1);
                    cell.setCellValue(itemData[i1]);
                }
            }
        }
        //设置下拉框数据
        String[] arr = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
        int index = 0;
        Row row;
//        CellRangeAddressList regions = new CellRangeAddressList(2, 500, 5, 5);
        if (ObjectUtil.isNotEmpty(downRows)) {
            for(int r=0;r<downRows.length;r++){
                String[] dlData = downData.get(r);//获取下拉对象
                int rownum = Integer.parseInt(downRows[r]);
                if(dlData.length<5){ //255以内的下拉
                    //255以内的下拉,参数分别是：作用的sheet、下拉内容数组、起始行、终止行、起始列、终止列
                    sheet1.addValidationData(setDataValidationSmall(sheet1, dlData, 2, 50000, rownum ,rownum)); //超过255个报错
                } else { //255以上的下拉，即下拉列表元素很多的情况

                    //1、设置有效性
                    //String strFormula = "Sheet2!$A$1:$A$5000" ; //Sheet2第A1到A5000作为下拉列表来源数据
                    String strFormula = sheetName+"aux!$"+arr[index]+"$1:$"+arr[index]+"$"+dlData.length; //Sheet2第A1到A5000作为下拉列表来源数据
                    sheet2.setColumnWidth(r, 4000); //设置每列的列宽
                    //设置数据有效性加载在哪个单元格上,参数分别是：从sheet2获取A1到A5000作为一个下拉的数据、起始行、终止行、起始列、终止列
                    sheet1.addValidationData(setDataValidationBig(strFormula, 2, 50000, rownum, rownum)); //下拉列表元素很多的情况
                    //2、生成sheet2内容
                    for(int j=0;j<dlData.length;j++){
                        if(index==0){ //第1个下拉选项，直接创建行、列
                            row = sheet2.createRow(j); //创建数据行
                            sheet2.setColumnWidth(j, 4000); //设置每列的列宽
                            row.createCell(0).setCellValue(dlData[j]); //设置对应单元格的值

                        } else { //非第1个下拉选项

                            int rowCount = sheet2.getLastRowNum();
                            //System.out.println("========== LastRowNum =========" + rowCount);
                            if(j<=rowCount){ //前面创建过的行，直接获取行，创建列
                                //获取行，创建列
                                sheet2.getRow(j).createCell(index).setCellValue(dlData[j]); //设置对应单元格的值
                            } else { //未创建过的行，直接创建行、创建列
                                sheet2.setColumnWidth(j, 4000); //设置每列的列宽
                                //创建行、创建列
                                sheet2.createRow(j).createCell(index).setCellValue(dlData[j]); //设置对应单元格的值
                            }
                        }
                    }
                    index++;
                }
            }
        }
        wb.setSheetHidden(hideIndex,true);
        //实体数据不为空则给数据加锁
        if(protect){
            sheet1.protectSheet(RandomUtil.randomString(10));
        }
//        try {
//            response.reset();
//            response.setHeader("Content-Disposition", "attachment; filename=\"" + URLUtil.encode("项目基本信息导入模板") +DateTimeUtil.getNowStampUnix() + ".xls\"");
//            response.setHeader("Access-Control-Allow-Origin", "*");
//            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
//            response.setContentType("application/octet-stream;charset=UTF-8");
//            OutputStream outputStream = response.getOutputStream();
//            wb.write(response.getOutputStream());
////            out.close();
//            outputStream.close();
//            wb.close();
//        } catch (IOException e) {
//            log.error(">>> 下载文件异常，请求号为：{}，具体信息为：{}", RequestNoContext.get(), e.getMessage());
//            throw new ServiceException(SysFileInfoExceptionEnum.DOWNLOAD_FILE_ERROR);
//        }
//        try {
//
////            File f = new File("K:/testQQ.xls"); //写文件
//
//            //不存在则新增
////            if(!f.getParentFile().exists()){
////                f.getParentFile().mkdirs();
////            }
////            if(!f.exists()){
////                f.createNewFile();
////            }
//
////            FileOutputStream out = new FileOutputStream(f);
////            out.flush();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return new ExcelWriter(wb);
//        return new ExcelWriter();
    }

    /**
     * 获取单元格注释
     * @return
     */
    public static void initCellComment(boolean isXlsx,Sheet sheet,Cell cell,String content){
        if (!isXlsx) {
            ClientAnchor anchor = new HSSFClientAnchor();
            // 关键修改
            anchor.setDx1(0);
            anchor.setDx2(0);
            anchor.setDy1(0);
            anchor.setDy2(0);
            anchor.setCol1(cell.getColumnIndex());
            anchor.setRow1(cell.getRowIndex());
            anchor.setCol2(cell.getColumnIndex() + 3);
            anchor.setRow2(cell.getRowIndex() + 4);
            // 结束
            Drawing drawing = sheet.createDrawingPatriarch();
            Comment comment = drawing.createCellComment(anchor);
            // 输入批注信息
            comment.setString(new HSSFRichTextString(content));
            // 将批注添加到单元格对象中
            cell.setCellComment(comment);
        } else {
            ClientAnchor anchor = new XSSFClientAnchor();
            // 关键修改
            anchor.setDx1(0);
            anchor.setDx2(0);
            anchor.setDy1(0);
            anchor.setDy2(0);
            anchor.setCol1(cell.getColumnIndex());
            anchor.setRow1(cell.getRowIndex());
            anchor.setCol2(cell.getColumnIndex() + 5);
            anchor.setRow2(cell.getRowIndex() + 6);
            // 结束
            Drawing drawing = sheet.createDrawingPatriarch();
            Comment comment = drawing.createCellComment(anchor);
            // 输入批注信息
            comment.setString(new XSSFRichTextString(content));
            // 将批注添加到单元格对象中
            cell.setCellComment(comment);
        }
    }

    /**
     * 获取日期单元格的内容
     * @return
     */
    public static String getCellDateStr(Cell cell){
        if (ObjectUtil.isEmpty(cell)) {
            return null;
        }
        if (cell.getCellType().equals(CellType.STRING)) {
            return cell.getStringCellValue();
        }else if (cell.getCellType().equals(CellType.NUMERIC)){
            if (HSSFDateUtil.isCellDateFormatted(cell)){
                Date date = cell.getDateCellValue();
                //格式转换
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                return sdf.format(date);
            }else {
                return String.valueOf(cell.getNumericCellValue());
            }
        }
        return null;
    }
    /**
     * 通用表头样式
     * @param wb
     * @return
     */
    public static CellStyle commonHeaderStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFont(commonFont(wb,12,IndexedColors.BLACK.getIndex()));
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        return style;
    }

    /**
     * 通用备注说明单元格样式
     * @param wb
     * @return
     */
    public static CellStyle commonCommentStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.BLACK.getIndex());
        style.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
        style.setWrapText(true);
        //字体样式
//        fontStyle.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(commonFont(wb,12,IndexedColors.RED.getIndex()));
        return style;
    }

    /**
     * 通用字体
     * @param wb
     * @return
     */
    public static Font commonFont(Workbook wb,int fontSize,short color){
        Font font = wb.createFont();
        font.setFontName("微软雅黑");
        font.setFontHeightInPoints((short) fontSize);
        if (ObjectUtil.isNotEmpty(color)) {
            font.setColor(color);
        }
        return font;
    }
    /**
     *
     * @Title: SetDataValidation
     * @Description: 下拉列表元素很多的情况 (255以上的下拉)
     * @param @param strFormula
     * @param @param firstRow   起始行
     * @param @param endRow     终止行
     * @param @param firstCol   起始列
     * @param @param endCol     终止列
     * @param @return
     * @return HSSFDataValidation
     * @throws
     */
    private static HSSFDataValidation setDataValidationBig(String strFormula,
                                                        int firstRow, int endRow, int firstCol, int endCol) {
        // 设置数据有效性加载在哪个单元格上。四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
        DVConstraint constraint = DVConstraint.createFormulaListConstraint(strFormula);
        HSSFDataValidation dataValidation = new HSSFDataValidation(regions,constraint);

        dataValidation.createErrorBox("Error", "Error");
        dataValidation.createPromptBox("", null);

        return dataValidation;
    }


    /**
     *
     * @Title: setDataValidation
     * @Description: 下拉列表元素不多的情况(255以内的下拉)
     * @param @param sheet
     * @param @param textList
     * @param @param firstRow
     * @param @param endRow
     * @param @param firstCol
     * @param @param endCol
     * @param @return
     * @return DataValidation
     * @throws
     */
    private static DataValidation setDataValidationSmall(Sheet sheet, String[] textList, int firstRow, int endRow, int firstCol, int endCol) {
        DataValidationHelper helper = sheet.getDataValidationHelper();
        //加载下拉列表内容
        DataValidationConstraint constraint = helper.createExplicitListConstraint(textList);
        //DVConstraint constraint = new DVConstraint();
        constraint.setExplicitListValues(textList);
        //设置数据有效性加载在哪个单元格上。四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol,endCol);
        //数据有效性对象
        DataValidation dataValidation = helper.createValidation(constraint, regions);
        dataValidation.createErrorBox("Error", "Error");
        dataValidation.createPromptBox("", null);
        //DataValidation data_validation = new DataValidation(regions, constraint);
        return dataValidation;
    }

    /**
     * 检查Cell的值
     * @param cell
     * @return
     */
    public static boolean checkCellValue(Cell cell){
        if (ObjectUtil.isEmpty(cell)) {
            return false;
        }
        cell.setCellType(CellType.STRING);
        if (ObjectUtil.isEmpty(cell.getStringCellValue())) {
            return false;
        }else{
            cell.setCellValue(cell.getStringCellValue().trim());
            return true;
        }
    }

    public static void main(String[] args) {
//        String fileName = "K:/test.xlsx"; //模板名称
//        String[] handers = {"*项目名称","*项目编号","*项目类型","*所属部门","*密级","*项目阶段","*项目分组",
//                "*优先级别","*紧急程度","*项目负责人","*院CCB负责人","*所CCB负责人","*质量保证人","*质量师","*项目配置管理员",
//                "*项目开始时间","*项目结束时间","项目简介"}; //列标题
//
//        //下拉框数据
//        List<String[]> downData = new ArrayList();
//        String[] str1 = {"男","女","未知"};
//        String[] str2 = {"北京","上海","广州","深圳","武汉","长沙","湘潭"};
//        String[] str3 = {"01-汉族","02-蒙古族","03-回族","04-藏族","05-维吾尔族","06-苗族","07-彝族","08-壮族","09-布依族",
//                "10-朝鲜族","11-满族","12-侗族","13-瑶族","14-白族","15-土家族","16-哈尼族","17-哈萨克族","18-傣族","19-黎族","20-傈僳族",
//                "21-佤族","22-畲族","23-高山族","24-拉祜族","25-水族","26-东乡族","27-纳西族","28-景颇族","29-柯尔克孜族","30-土族",
//                "31-达斡尔族","32-仫佬族","33-羌族","34-布朗族","35-撒拉族","36-毛难族","37-仡佬族","38-锡伯族","39-阿昌族","40-普米族",
//                "41-塔吉克族","42-怒族","43-乌孜别克族","44-俄罗斯族","45-鄂温克族","46-德昂族","47-保安族","48-裕固族","49-京族","50-塔塔尔族",
//                "51-独龙族","52-鄂伦春族","53-赫哲族","54-门巴族","55-珞巴族","56-基诺族","98-外国血统","99-其他"};
//        downData.add(str1);
//        downData.add(str2);
//        downData.add(str3);
//        String [] downRows = {"1","2","3"}; //下拉的列序号数组(序号从0开始)
//        try {
//
////            ExcelUtil.createExcelTemplate(null,handers, downData, downRows);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        try {
            ExcelReader reader = cn.hutool.poi.excel.ExcelUtil.getReader("K:/项目清单导入模板1626075165.xls");
            List<Map<String, Object>> maps = reader.readAll();
            //List<Person> all = reader.readAll(Person.class);
            System.out.println(JSON.toJSONString(maps,true));
            reader.close();
        }catch (Exception e){
            log.error(e.getMessage());
        }

    }
}
