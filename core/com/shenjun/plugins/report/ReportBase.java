package com.shenjun.plugins.report;

import java.util.List;
import java.util.Map;

/**
 * @author: 沈军
 * @category
 * {@link shenjunhappy@live.com }
 * 版权所有,严禁侵权.
 */
public class ReportBase {
	
	private List<Object> objData;
	
	private String jasperTemplate;
	
	private String reportName;
	
	private Map parameter;
	
	private String jasperJrxml;
	
	public ReportBase(){
		
	}
	
	public ReportBase(List<Object> objData,Map parameter , String jasperJrxml ,String jasperTemplate){
		this.objData = objData;
		this.parameter = parameter;
		this.jasperJrxml = jasperJrxml;
		this.jasperTemplate =jasperTemplate;
	}

	public String getJasperJrxml() {
		return jasperJrxml;
	}

	public void setJasperJrxml(String jasperJrxml) {
		this.jasperJrxml = jasperJrxml;
	}

	public String getJasperTemplate() {
		return jasperTemplate;
	}

	public void setJasperTemplate(String jasperTemplate) {
		this.jasperTemplate = jasperTemplate;
	}

	public List<Object> getObjData() {
		return objData;
	}

	public void setObjData(List<Object> objData) {
		this.objData = objData;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public Map getParameter() {
		return parameter;
	}

	public void setParameter(Map parameter) {
		this.parameter = parameter;
	}
}
