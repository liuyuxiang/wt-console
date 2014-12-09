package com.wt.hea.common.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


import jxl.BooleanCell;
import jxl.BooleanFormulaCell;
import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.DateFormulaCell;
import jxl.LabelCell;
import jxl.NumberCell;
import jxl.NumberFormulaCell;
import jxl.Range;
import jxl.Sheet;
import jxl.StringFormulaCell;
import jxl.Workbook;
import jxl.write.WritableWorkbook;

/***
 * 该工具类提供操作Excel的相关方法
 * @author 袁明敏
 *
 */
public class ExcelUtil {
	
	/***
	 * 新建单个空EXCEL文件
	 * @param path 文件路径
	 * @return File
	 */
	public static File createExcel(String path){
		WritableWorkbook book =null;
		File newFile=null;
		try{
		   newFile=new File(path);
           book  =  Workbook.createWorkbook(newFile);
           book.createSheet("Sheet1" ,  0 );
           book.write();
         
       }catch(Exception e){
    	   
       }finally{
    	   if(book!=null){
    		   try{
    			  book.close();
    		   }catch(Exception ex){
    			   ex.printStackTrace();
    		   }
    	   }
       }
      return newFile;
	}

	/***
	 * 新建单个Excel文件
	 * @param path 文件存放路径
	 * @param sheetNames excel里的 多个sheet名称
	 * @return 返回excel文件对象
	 */
	public static File createExcel(String path,String ... sheetNames){
		WritableWorkbook book =null;
		File newFile=null;
		try{
		   newFile=new File(path);
           book  =  Workbook.createWorkbook(newFile);
           for(int i=0;i<sheetNames.length;i++){
        	   book.createSheet(sheetNames[i] ,  i );
           }
          
           book.write();
           
       }catch(Exception e){
    	   e.printStackTrace();
       }finally{
    	   if(book!=null){
    		   try{
    			  book.close();
    		   }catch(Exception ex){
    			   ex.printStackTrace();
    		   }
    	   }
       }
       return newFile;
	}
	
	/***
	 * 读取单个Excel文件，map 键为sheet名称，值为sheet表格的二维数据
	 * (合并单元格区域， 区域的数据只显示一次)
	 * @param path excel文件路径
	 * @return Map<String,Object[][]>
	 */
	public static Map<String,Object[][]>  readExcel(String path){
		Workbook book =null;
		File file=null;
		Map<String,Object[][]> map=new HashMap<String,Object[][]>();
        try{
        	file=new File(path);
        	book  =  Workbook.getWorkbook(file);
        	String [] sheetNames=book.getSheetNames();
        	for(String i: sheetNames){
        		Sheet sheet=book.getSheet(i);
        		int rows=sheet.getRows();
        		int cols=sheet.getColumns();
        		Object [][] data=new Object[rows][cols];
        		for(int r=0;r<rows;r++){
        			for(int c=0;c<cols;c++){
        				Cell cell=sheet.getCell(c, r);
        				data[r][c] =getCellValue(cell);
        			}
        		}
        		map.put(i, data);
        	}
        }   catch  (Exception e)   {
            e.printStackTrace();
        }finally{
    	   if(book!=null){
    		   try{
    			  book.close();
    		   }catch(Exception ex){
    			   ex.printStackTrace();
    		   }
    	   }
       }
        return map;
	}
	
	/***
	 * 读取单个Excel文件，map 键为sheet名称，值为sheet表格的二维数据
	 * (合并单元格区域   区域内的每一个Cell都显示数据)
	 * 
	 * @param path excel文件全路径
	 * @param isShowMergedCell 合并的单元格块 数据是否以最基本单元格填充显示数据
	 * @return Map<String,Object[][]> 返回map 键为sheet名称，值为sheet表格的二维数据
	 */
	public static Map<String,Object[][]>  readExcel(String path,Boolean isShowMergedCell){
		Workbook book =null;
		File file=null;
		Map<String,Object[][]> map=new HashMap<String,Object[][]>();
        try{
        	file=new File(path);
        	book  =  Workbook.getWorkbook(file);
        	String [] sheetNames=book.getSheetNames();
        	for(String i: sheetNames){
        		Sheet sheet=book.getSheet(i);
        		int rows=sheet.getRows();
        		int cols=sheet.getColumns();
        		Object [][] data=new Object[rows][cols];
        		for(int r=0;r<rows;r++){
        			for(int c=0;c<cols;c++){
        				if(isInMergedCell(r, c, sheet)){
        					data[r][c]= getMergedCellValue(r, c, sheet);
        				}else{
	        				Cell cell=sheet.getCell(c, r);
	        				data[r][c] =getCellValue(cell);
        				}
        			}
        		}
        		map.put(i, data);
        	}
        }   catch  (Exception e)   {
            e.printStackTrace();
        }finally{
    	   if(book!=null){
    		   try{
    			  book.close();
    		   }catch(Exception ex){
    			   ex.printStackTrace();
    		   }
    	   }
       }
        return map;
	}
	
	/***
	 * 读取单个Excel文件，读取出来的数据以map的形试存储,map 键为sheet名称，值为sheet表格的二维数据
	 * (合并单元格区域   区域内的每一个Cell都显示数据)
	 * 
	 * @param path excel文件全路径
	 * @param isShowMergedCell 合并的单元格块 数据是否以最基本单元格填充显示数据
	 * @param startRowIndex 以指定的行数开始读取数据,第一行的数据索引号为0
	 * @return Map<String,Object[][]> map 键为sheet名称，值为sheet表格的二维数据
	 */
	public static Map<String,Object[][]>  readExcel(String path,Boolean isShowMergedCell,int startRowIndex){
		Workbook book =null;
		File file=null;
		Map<String,Object[][]> map=new HashMap<String,Object[][]>();
        try{
        	file=new File(path);
        	book  =  Workbook.getWorkbook(file);
        	String [] sheetNames=book.getSheetNames();
        	for(String i: sheetNames){
        		Sheet sheet=book.getSheet(i);
        		int rows=sheet.getRows();
        		
        		
        		int cols=sheet.getColumns();
        		Object [][] data=new Object[rows][cols];
        		for(int r=0;r<rows;r++){
        			for(int c=0;c<cols;c++){
        				if(isInMergedCell(r, c, sheet)){
        					data[r][c]= getMergedCellValue(r, c, sheet);
        				}else{
	        				Cell cell=sheet.getCell(c, r);
	        				
	        				data[r][c] =getCellValue(cell);
        				}
        			}
        		}
        		map.put(i, data);
        	}
        }   catch  (Exception e)   {
            e.printStackTrace();
        }finally{
    	   if(book!=null){
    		   try{
    			  book.close();
    		   }catch(Exception ex){
    			   ex.printStackTrace();
    		   }
    	   }
       }
        return map;
	}
	
	/**
	 * 判断当前行当前列的单元格是否坐落在合并单元格区域内,并获取合并单元格的值
	 * @param row sheet表格行索引（从0开始）
	 * @param col sheet表格列索引（从0开始）
	 * @param sheet sheet表格对象
	 * @return 返回单元格内的值
	 */
	private static Object getMergedCellValue(int row ,int col,Sheet sheet){
		Range [] ranges=sheet.getMergedCells();
		for(Range g :ranges){
			int top_row=g.getTopLeft().getRow();
			int top_col=g.getTopLeft().getColumn();
			
			int buttom_row=g.getBottomRight().getRow();
			int buttom_col=g.getBottomRight().getColumn();
			
			
			if(row>=top_row && row<=buttom_row  && col>=top_col && col<=buttom_col){
				return getCellValue(g.getTopLeft());
			}
		}
		return null;
	}
	
/*	public static void main(String[] args) throws BiffException, IOException {
		Workbook wb=Workbook.getWorkbook(new File("C:/Users/MM/Desktop/a.xls"));
		wb.getSheet("Sheet1").getMergedCells();
		
		
		Map<String ,Object [][]> map=ExcelUtil.readExcel("C:/Users/MM/Desktop/a.xls",true);
		Object[] [] data=map.get("Sheet1");
		for(Object [] record: data){
			for(Object ele: record){
				System.out.print((ele==null|| "".equals(ele)?"null":ele)+"         ");
			}
		}

	}
	*/
	
	
	/**
	 * 判断当前行当前列的单元格是否坐落在合并单元格区域内
	 * @param row 行索引
	 * @param col 列索引
	 * @param sheet sheet表对象
	 * @return 当前行当前列的单元格是否坐落在合并单元格区域内就返回真
	 */
	private static Boolean isInMergedCell(int row,int col,Sheet sheet){
		Range [] ranges=sheet.getMergedCells();
		for(Range g :ranges){
			int top_row=g.getTopLeft().getRow();
			int top_col=g.getTopLeft().getColumn();
			
			int buttom_row=g.getBottomRight().getRow();
			int buttom_col=g.getBottomRight().getColumn();
			
			
			if(row>=top_row && row<=buttom_row  && col>=top_col && col<=buttom_col){
				return true;
			}
			
		}
		return false;
	}
	
	
	/**
	 * 辅助方法,过滤null值,获取excel单元格值
	 * @param value 单元格对象
	 * @return 返回单元格对象内的内容值
	 */
	private static Object getCellValue(Cell value){
		CellType cellType=value.getType();

		if(cellType==CellType.BOOLEAN){
			BooleanCell  bcell=(BooleanCell)value;
			return bcell.getValue();
		}else if(cellType==CellType.BOOLEAN_FORMULA){
			BooleanFormulaCell bcell=(BooleanFormulaCell)value;
			return bcell.getValue();
		}else if(cellType==CellType.DATE){
			DateCell dcell=(DateCell)value;
			return DateUtil.format(dcell.getDate(), null);
			
		}else if(cellType==CellType.DATE_FORMULA){
			DateFormulaCell dcell=(DateFormulaCell)value;
			return DateUtil.format(dcell.getDate(), null);
			
			
		}else if(cellType==CellType.EMPTY){
			return  "";
		}else if(cellType==CellType.ERROR){
			return "";
		}else if(cellType==CellType.FORMULA_ERROR){
			return "";
		}else if(cellType==CellType.LABEL){
			jxl.LabelCell lcell=(LabelCell)value;
			return lcell.getContents();
		}else if(cellType==CellType.NUMBER){
			jxl.NumberCell ncell=(NumberCell)value;
			return ncell.getValue();
		}else if(cellType==CellType.NUMBER_FORMULA){
			NumberFormulaCell nfcell=(NumberFormulaCell)value;
			return nfcell.getValue();
		}else if(cellType==CellType.STRING_FORMULA){
			StringFormulaCell scell=(StringFormulaCell)value;
			return scell.getString();
		}else if(   (!"".equals(value.getContents()))   && value.getContents()!=null){
			return value.getContents();
		}
		return "";
	}
}
