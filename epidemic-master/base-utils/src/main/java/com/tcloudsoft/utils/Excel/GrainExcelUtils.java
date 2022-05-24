package com.tcloudsoft.utils.Excel;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.tcloudsoft.utils.TcloudUtils;
import com.tcloudsoft.utils.ex.TcmsAuthException;
import com.tcloudsoft.utils.utils.DateUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 粮安项目动态导入导出封装工具类
 * @since 2021-10-20
 * @author liuwei
 *
 * <p>
 *     可以对导入导出业务进行封装，简化代码和效率
 * </>
 */

@Slf4j
@Component
@Data
public class GrainExcelUtils {

    private static XSSFFormulaEvaluator evaluator;

    private Integer totalRow = 0;// 最大行数 默认为 0

    private Sheet sheet;// excel sheet

    private static Integer headRows = 0;// 表头占的行数 默认为 0

    private static ExcelModel [] excelModels = new ExcelModel[0]; //读取到的实体类所有属性信息集合

    /**
     * excel导入模板读取数据 利用反射动态赋值
     * @param row excel文件需要读取的行
     * @param fieldList 需要导入的列属性集合
     * @param errList 导入错误的列属性
     * @param startIndex 起始读取位置(对应excel)
     * @param size 总共连续读取的大小
     * @deprecated 已过时
     * @return List<String> 返回的错误信息集合
     */
    public synchronized List<String> importFromExcel(Object model, Row row, List<ExcelModel> fieldList, List<String> errList, Integer startIndex, Integer size){
        try {
            if (fieldList.size() == 0) throw new IllegalArgumentException("需要映射的实体字段集合不能为空！");
            // 该实体类所有属性值
            int fieldIndex =0;
            for (int i = startIndex ; i<startIndex+size ; i++){// 循环读取 excel 从 startIndex 读取到 startIndex+size
                // 获取该列的属性信息
                ExcelModel excelInfo = fieldList.get(fieldIndex);
                String fieldEnName = excelInfo.getFieldEnName();
                String fieldType = excelInfo.getFieldType();
                boolean isNotNull = excelInfo.isNotNull();

                if(fieldEnName.indexOf("_") > -1){//包含下划线（非驼峰命名格式）
                    String str[] = fieldEnName.split("_");
                    StringBuffer sb = new StringBuffer(str[0]);// 缓存操作
                    for(int x =1 ; x < str.length; x++){
                        sb.append(str[x].substring(0,1).toUpperCase()+str[x].substring(1));
                    }
                    fieldEnName = sb.toString();
                }
                // 反射获取字段属性，后续进行赋值
                Class<?> clazz = model.getClass();
                Field field = clazz.getDeclaredField(fieldEnName);
                field.setAccessible(true);// 开放权限

                //读取excel值
                Cell cell = row.getCell(i);
                try {
                    Object value = this.getSheetValue(cell);// 读取excel值
                    if(TcloudUtils.isEmpty(value) || "无".equals(value.toString())){
                        if(isNotNull){
                            errList.add("第"+(row.getRowNum()-headRows)+"行 "+excelInfo.getFieldName()+"为必填项，不能为空！");
                        }
                        fieldIndex++;// 同步
                        continue;
                    }
                    switch (fieldType){
                        case "String":
                            String strV = value.toString();
                            if(strV.indexOf(".") > -1){
                                strV = strV.substring(0,strV.indexOf("."));
                            }
                            field.set(model,strV);
                            break;
                        case "Integer":
                            String strValue = value.toString();
                            if(strValue.indexOf(".") > -1){// 带小数点的类型格式
                                double dv = (double)value;
                                int iv =(int) Math.floor(dv);
                                field.set(model,iv);
                            }else {
                                field.set(model,Integer.parseInt(value.toString()));
                            }
                            break;
                        case "Double":
                            field.set(model,Double.parseDouble(value.toString()));
                            break;
                        case "BigDecimal":
                            BigDecimal big = new BigDecimal(value.toString()).setScale(2, RoundingMode.HALF_UP);
                            field.set(model,big);
                            break;
                        case "DateTime":
                            if (value.toString().indexOf("-") > 0 || value.toString().indexOf("/") > 0) {
                                Date date = DateUtils.StringToDate(value.toString());
                                field.set(model,date);
                            } else {
                                Date date = HSSFDateUtil.getJavaDate(Double.parseDouble(value.toString()));
                                field.set(model,date);
                            }
                            break;
                        case "Date":
                            if (value.toString().indexOf("-") > 0 || value.toString().indexOf("/") > 0) {
                                Date date = DateUtils.string2Date(value.toString());
                                field.set(model,date);
                            } else {
                                Date date = HSSFDateUtil.getJavaDate(Double.parseDouble(value.toString()));
                                field.set(model,date);
                            }
                            break;
                        default:
                            String strD = value.toString();
                            if(strD.indexOf(".") > -1){
                                strD = strD.substring(0,strD.indexOf("."));
                            }
                            field.set(model,strD);
                            break;
                    }
                }catch (Exception e){
                    errList.add("第"+(row.getRowNum()-headRows)+"行 "+excelInfo.getFieldName()+"格式错误！");
                }
                // 每执行完一列的读取，将元素集合的下标往后推一位，与读取的列保持一致
                fieldIndex++;
            }
        }catch (Exception e){
            log.error("导出失败",e);
        }finally {
            //返回错误信息集合
            return errList;
        }
    }


    /**
     * 根据实体类注解信息分段读取excel表信息
     * 根据excel中元素下标进行匹配机制
     * @param model 实体对象
     * @param row 读取的行
     * @param errList 错误信息集合对象
     * @param startIndex 起始读取下标（包含此下标）
     * @param endIndex 终止读取下标（包含此下标）
     * @param beanMap 指定excel下标的字段数据以读取到的值的集合
     */
    public synchronized void importByAnnotationHasOnly(Object model, Row row,Map<Integer,List<Object> > beanMap,
         List<String> errList, int startIndex, int endIndex) throws IllegalArgumentException,NoSuchFieldException{
        //通过注解grainExcelModel读取所有属性信息
        if(excelModels.length == 0){// 第一次进来的时候获取属性集合信息，后续就不用重复获取
            excelModels = getFieldList(model);
        }
        // 该实体类所有属性值
        for (int i = startIndex ; i<=endIndex ; ++i){// 循环读取 excel 从 startIndex 读取到 startIndex+size
            // 获取该列的属性信息，原本集合是按excel下标存放，因此此处可以直接根据下标拿指定元素
            ExcelModel excelModel = excelModels[i];
            if(excelModel == null){
                continue;
            }
            String fieldEnName = excelModel.getFieldEnName();
            String fieldName = excelModel.getFieldName();
            String fieldType = excelModel.getFieldType();
            boolean isNotNull = excelModel.isNotNull();
            boolean isOnly = excelModel.isOnly();

            // 反射获取字段属性，后续进行赋值
            Class<?> clazz = model.getClass();
            Field field = clazz.getDeclaredField(fieldEnName);
            field.setAccessible(true);// 开放权限

            try {
                // 读取excel该下标i出的值
                Cell cell = row.getCell(i);
                Object value = this.getSheetValue(cell);// 读取excel值
                if(TcloudUtils.isEmpty(value) || "无".equals(value.toString())){
                    if(isNotNull){// 非空
                        errList.add("第"+(row.getRowNum()-headRows)+"行 " +
                                ""+fieldName+"为必填项，不能为空！");
                    }
                    continue;
                }
                boolean flag = false; // 判断该字段是否唯一，并且所传的集合中是否存在该下标已读的对象List
                if(isOnly){// 唯一
                    if(TcloudUtils.isNotEmpty(beanMap) && beanMap.size() > 0){
                        flag = true;
                        List<Object> fieldValueList = beanMap.get(i);// 根据excel下标获取指定该位置已读取的数据集合
                        if(TcloudUtils.isNotEmpty(fieldValueList)){
                            if(fieldValueList.contains(value)){
                                errList.add("第"+(row.getRowNum()-headRows)+"行 "+
                                        fieldName+"与在 excel 中已存在，请检查后重新导入");
                                continue;
                            }
                        }
                    }
                }
                switch (fieldType){
                    case "String":
                        String strV = value.toString();
                        if(strV.indexOf(".") > -1)
                        strV = strV.substring(0,strV.indexOf("."));
                        field.set(model,strV);
                        break;
                    case "Integer":
                        String strValue = value.toString();
                        if(strValue.indexOf(".") == 0) {
                            field.set(model,Integer.parseInt(value.toString()));
                            break; }
                        double dv = (double)value;
                        int iv =(int) Math.floor(dv);
                        field.set(model,iv);
                        break;
                    case "Double":
                        field.set(model,Double.parseDouble(value.toString()));
                        break;
                    case "BigDecimal":
                        BigDecimal big = new BigDecimal(value.toString()).
                                setScale(2, RoundingMode.HALF_UP);
                        field.set(model,big);
                        break;
                    case "DateTime":// 日期 年 月 日 时 分 秒
                        Date dateTime = new Date();
                        if (value.toString().indexOf("-") > 0) {
                            dateTime = DateUtils.StringToDate(value.toString());
                        }else if(value.toString().indexOf("/") > 0){
                            dateTime = DateUtils.StringToDate2(value.toString());
                        } else {
                            dateTime = HSSFDateUtil.getJavaDate(
                                    Double.parseDouble(value.toString()));
                        }
                        field.set(model,dateTime);
                        break;
                    case "Date":// 日期 年 月 日
                        Date date = new Date();
                        if (value.toString().indexOf("-") > 0) {
                            date = DateUtils.string2Date(value.toString());
                        }else if(value.toString().indexOf("/") > 0){
                            date = DateUtils.string2Date2(value.toString());
                        } else {
                            date = HSSFDateUtil.getJavaDate(
                                    Double.parseDouble(value.toString()));
                        }
                        field.set(model,date);
                        break;
                    default:// 默认为 String 类型数据
                        String strD = value.toString();
                        if(strD.indexOf(".") > -1)
                        strD = strD.substring(0,strD.indexOf("."));
                        field.set(model,strD);
                        break;
                }
                //将读取的这一行放入已读取集合中
                if (flag) beanMap.get(i).add(value);
            }catch (Exception e){
                errList.add("第"+(row.getRowNum()-headRows)+"行 "+fieldName+"格式错误！");
            }
        }
    }

    /**
     * 没有在 excel 中唯一的字段属性时 调用这个方法
     * @param model
     * @param row
     * @param errList
     * @param startIndex
     * @param endIndex
     * @return
     */
    public synchronized void importByAnnotationOnOnly(Object model, Row row, List<String> errList, int startIndex, int endIndex)throws IllegalArgumentException,NoSuchFieldException{
         this.importByAnnotationHasOnly(model,row,new HashMap<>(),errList,startIndex,endIndex);
    }


    /**
     * 判断实体对象是否存在自定义注解GrainExcelModel
     *  如果存在 获取该字段信息以及注解相关信息
     * @param model 实体对象
     * @return ExcelModel [] 按下标存储
     * @since 2020-10-21
     * @author liuwei
     */
    public static ExcelModel [] getFieldList(Object model) throws IllegalArgumentException,NoSuchFieldException{
        if(model == null){
            throw new IllegalArgumentException("参数错误，操作对象为空！");
        }
        Field [] fields = model.getClass().getDeclaredFields();
        ExcelModel [] models = new ExcelModel[fields.length*10];
        // 定义size大小，避免每次循环重复获取length属性，++i避免每次循环都生成临时变量，提高循环的效率
        for (int i = 0,size = fields.length ; i<size; ++i){
            Field field = fields[i];
            // 获取自定义注解 grainExcelModel 类对象
            Class<? extends GrainExcelModel> annotationClass = GrainExcelModel.class;
            boolean isBoolean = field.isAnnotationPresent(annotationClass);
            if (isBoolean){
                String name = field.getAnnotation(annotationClass).name();// excel字段中文名
                String index = field.getAnnotation(annotationClass).index();// 字段对应excel 下标
                Integer in = Integer.parseInt(index);
                boolean isNotNull = field.getAnnotation(annotationClass).isNotNull();// 是否必填
                boolean isOnly = field.getAnnotation(annotationClass).isOnLy();// 是否唯一
                String fieldType = field.getType().getTypeName();// 字段数据类型
                String fieldName = field.getName();// 字段名称
                ExcelModel excelModel = new ExcelModel(fieldName,name,isNotNull,isOnly,in,fieldType);
                // 放入excelModel实体集合中(这里根据按excel下标的位置存放，后续读取数据更加快速)
                models[in] = excelModel;
            }
        }
        return models;
    }

    /**
     * 判断导入的excel是否符合导入要求
     * @param file 导入的excel文件
     * @param beginRow 内容起始行（除去表头的第一行）
     * @throws IOException
     * @since 2021-10-20
     */
    public synchronized boolean checkExcel(MultipartFile file, int beginRow) throws TcmsAuthException,IOException {
        String filename = file.getOriginalFilename();
        if (!filename.matches("^.+\\.(?i)(xls)$") && !filename.matches("^.+\\.(?i)(xlsx)$")) {
            throw new TcmsAuthException("500","非法excel格式！请选择正确的excel文件再进行上传！");
        }
        // 判断Excel文件的版本
        boolean isExcel2003 = true;
        if (filename.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }
        InputStream is = file.getInputStream();
        Workbook wb = null;
        if (isExcel2003) {
            wb = new HSSFWorkbook(is);// 2003版本获取(xls)
        } else {
            wb = new XSSFWorkbook(is);// 非2003版本获取(xlsx)
        }
        //Sheet sheetAt = wb.getSheetAt(0);
        //int totalRows = sheetAt.getPhysicalNumberOfRows(); // 获取行数，第一行是标题
        ////全局变量赋值
        //this.sheet = sheetAt;
        //this.totalRow = totalRows;
        //this.headRows = beginRow - 1;
        //if (totalRows < beginRow) {
        //    throw new TcmsAuthException("500","您上传的excel表格无数据！请检查上传的excel后再重新上传！");
        //}
        return true;
    }

    public static Object getSheetValue(Cell cell){
        Object value ;
        if (cell == null) {
            //导入不能为空
            value = "无";
            return value;
        }
        // 格式化 number String
        DecimalFormat df = new DecimalFormat("0");
        // 字符,格式化日期字符串
        SimpleDateFormat sdf = new SimpleDateFormat(
                "yyyy-MM-dd");
        // 格式化数字
        DecimalFormat nf = new DecimalFormat("0");
        //判断当前单元格的元素类型
        switch (cell.getCellType()) {
            case XSSFCell.CELL_TYPE_STRING:
                value = cell.getStringCellValue();
                break;
            case XSSFCell.CELL_TYPE_NUMERIC:
                value = cell.getNumericCellValue();
                //避免手机号,身份证等自动识别为double类型
                //由于精度问题需要用减法判断大小
                if (80000000.0 - (double)value < 0){
                    value = (long)((double)value);
                    value = String.valueOf(value);
                }
                break;
            case XSSFCell.CELL_TYPE_BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            //空格，空白
            case XSSFCell.CELL_TYPE_BLANK:
                value = "";
                break;
            case XSSFCell.CELL_TYPE_FORMULA:
                value = "";
                try{
                    //可能会有Invalid sheetIndex异常
                    value = getCellValue(evaluator.evaluate(cell));
                }catch (Exception e){
                    e.printStackTrace();
                }

                break;
            default:
                value = cell.toString();
        }
        if (value == null || "".equals(value)) {
            //导入不能为空
            value = "无";
        }
        return value;
    }

    private static String getCellValue(CellValue cell) {
        String cellValue = null;
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                cellValue=cell.getStringValue();
                break;

            case Cell.CELL_TYPE_NUMERIC:
                cellValue=String.valueOf(cell.getNumberValue());
                break;
            case Cell.CELL_TYPE_FORMULA:
                break;
            default:
                break;
        }
        return cellValue;
    }

    /**
     * excel 脱离excel 导出
     * @param response 响应对象
     * @param fileName 文件名称(转 ISO-8859-1)
     * @param templetName  文件名称
     * @param beanList  字段名与中文名对应字段集合
     * @param list 数据集合
     */
    public static void getExcel(HttpServletResponse response, String fileName, String templetName, List beanList, Object list){
        try {
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition","attachment;filename="+fileName);
            ServletOutputStream out = response.getOutputStream();
            ExportParams exportParams = new ExportParams(templetName, templetName, ExcelType.XSSF);
            Workbook workbook = ExcelExportUtil.exportExcel(exportParams, beanList,(List)list);
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
