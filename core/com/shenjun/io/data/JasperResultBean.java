package com.shenjun.io.data;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.shenjun.web.thread.Lc;
/**
 * 
 * @author jbox_user
 *
 */
public class JasperResultBean {
	
	public static final String JASPER_RESULT_BEAN="jasper_result_bean";
	public static final String JASPER_CONN="jasper_conn";
	public static final String JASPER_DATASOURCE="jasper_dataSource";
	public static final String JASPER_REPORT_PARAMS="jasper_report_parameters";
	
	
	public static final String FORMAT_PDF="PDF";
	public static final String FORMAT_HTML="HTML";
	public static final String FORMAT_XML="XML";
	public static final String FORMAT_CSV="CSV";
	public static final String FORMAT_XLS="XLS";
	
	private String location;
	
	private String format=FORMAT_HTML;
	
	private Map<Object,Object> reportParameters;
	
	private String imageServletUrl="/jasperimage?image=";
	
	private List<Object> dataSource;
	
	private Connection connection;

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = "/WEB-INF/classes/"+location;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public Map<Object,Object> getReportParameters() {
		return reportParameters;
	}

	public void setReportParameters(Map<Object,Object> reportParameters) {
		Lc.setContext(JasperResultBean.JASPER_REPORT_PARAMS, reportParameters);
		this.reportParameters = reportParameters;
	}

	public String getImageServletUrl() {
		return imageServletUrl;
	}

	public void setImageServletUrl(String imageServletUrl) {
		this.imageServletUrl = imageServletUrl;
	}

	public List<Object> getDataSource() {
		return dataSource;
	}

	public void setDataSource(List<Object> dataSource) {
		Lc.setContext(JasperResultBean.JASPER_DATASOURCE, dataSource);
		this.dataSource = dataSource;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		Lc.setContext(JasperResultBean.JASPER_CONN, connection);
		this.connection = connection;
	}
	
	/**
	location：是指我们刚才用iReport编译生成的jasper文件
	dataSource：是指我们执行的数据库查询结果，在testAction类里把这个结果查询出来，这个参数必须要有
	format：是指需要输出的类型，默认是PDF，其他的输出类型有：XML、HTML、XLS、CSV、RTF ，注意这里一定要大写
	reportParameters：jasperreport中的Parameters
	除了这几个参数，还有下列参数：
	delimiter：是指如果输出类型为CSV的话，指定分割符，默认为“,”
	parse：是指是否解析location参数中的EL表达式，默认为 true
	contentDisposition：指定disposition，默认为“inline”，如果设为“attachment”就是强制下载
	documentName：输出的文件名
	imageServletUrl：生成图形的路径
	**/
}
