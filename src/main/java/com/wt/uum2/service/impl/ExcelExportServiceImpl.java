package com.wt.uum2.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.wt.uum2.domain.AttributeType;
import com.wt.uum2.domain.User;
import com.wt.uum2.service.ExcelExportService;
import com.wt.uum2.service.UUMService;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-9
 * 作者:	LiuYX
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class ExcelExportServiceImpl implements ExcelExportService
{
	/**
	 * 
	 */
	private UUMService uumService;
	/**
	 * 
	 */
	private List<String> columns;
	/**
	 * 
	 */
	private String tempDir;
	/**
	 * 
	 */
	private String downloadUrl;
	/**
	 * 
	 */
	private int rowTotal;
	/**
	 * 
	 */
	private int rowCurrent;

	public void setUumService(UUMService uumService)
	{
		this.uumService = uumService;
	}

	public void setColumns(List<String> columns)
	{
		this.columns = columns;
	}

	public void setTempDir(String tempDir)
	{
		this.tempDir = tempDir;
	}

	public void setDownloadUrl(String downloadUrl)
	{
		this.downloadUrl = downloadUrl;
	}

	public void setRowTotal(int rowTotal)
	{
		this.rowTotal = rowTotal;
	}

	public void setRowCurrent(int rowCurrent)
	{
		this.rowCurrent = rowCurrent;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.ExcelExportService#getAttributeTypeByColumns()
	 */
	/**
	 * 方法说明：getAttributeTypeByColumns
	 * 
	 * @return List
	 */
	public List<String> getAttributeTypeByColumns()
	{
		List<String> headersList = new ArrayList<String>();
		for (String column : columns) {
			List<AttributeType> attrType = uumService.getAttributeTypeById(column);
			if (CollectionUtils.isNotEmpty(attrType)) {
				headersList.add(StringUtils.isNotBlank(attrType.get(0).getName()) ? attrType.get(0)
						.getName() : "");
			}
		}

		return headersList;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.ExcelExportService#transformXslUsers(com.wt.uum2.domain.User)
	 */
	/**
	 * 方法说明：transformXslUsers
	 * 
	 * @param user
	 *            user
	 * @return List
	 */
	public List<String> transformXslUsers(User user)
	{
		String[] columnsToArray = new String[columns.size()];
		List<String> xslUsers = new ArrayList<String>();
		columns.toArray(columnsToArray);
		Map<String, String> xmlUsersAccount = new LinkedHashMap<String, String>();
		xmlUsersAccount = uumService.getAttributesMapByResourceAndTypes(user, columnsToArray);

		for (String column : columns) {
			xslUsers.add(xmlUsersAccount.get(column) == null ? "" : xmlUsersAccount.get(column));
		}
		return xslUsers;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.ExcelExportService#exportExcel(java.util.List, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	/**
	 * 方法说明：exportExcel
	 * 
	 * @param xlsUsersList
	 *            xlsUsersList
	 * @param request
	 *            request
	 * @param response
	 *            response
	 * @return String
	 */
	public String exportExcel(List<List<String>> xlsUsersList, HttpServletRequest request,
			final HttpServletResponse response)
	{
		String tempFileName = "";
		if (CollectionUtils.isNotEmpty(xlsUsersList)) {
			StringBuffer tempPath = new StringBuffer();
			tempPath.append(request.getSession().getServletContext().getRealPath("/"));
			tempPath.append(tempDir);
			File directory = new File(tempPath.toString());
			File tempFile = null;
			boolean isExists = true;
			try {
				if (!directory.isDirectory() && !directory.exists()) {
					isExists = directory.mkdir();
				}
				if (isExists) {
					tempFile = File.createTempFile(this.getFileName(), ".xls", directory);
					WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
					WritableSheet ws = workbook.createSheet("sheet 1", 0);

					for (int i = 0; i < xlsUsersList.size(); i++) {
						int columnNum = 0;
						for (String value : xlsUsersList.get(i)) {
							ws.addCell(new Label(columnNum++, i, value));
						}
					}
					workbook.write();
					workbook.close(); // 一定要关闭, 否则没有保存Excel

					tempFileName = tempDir + tempFile.getName();
				}
			} catch (Exception e) {
				e.printStackTrace();

			} finally {
				if (tempFile != null)
					tempFile.deleteOnExit();
				directory.deleteOnExit();
			}
		}
		return tempFileName;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.ExcelExportService#getFileName()
	 */
	/**
	 * 方法说明：getFileName
	 * 
	 * @return String
	 */
	public String getFileName()
	{
		SimpleDateFormat datetime = new SimpleDateFormat("yyyyMMddhhmmss");
		Date time = new Date();
		String name = datetime.format(time);
		return name;
	}
}
