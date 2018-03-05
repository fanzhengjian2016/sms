package com.commons.compile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;

import com.shenjun.plugins.spring.Compile;
import com.shenjun.util.WebUtil;
import com.shenjun.util.XMLUtil;
import com.shenjun.web.thread.Lc;
import com.shenjun.xml.XmlClass;

public class CompileXml implements Compile {
	
	public  Log log = LogFactory.getLog(CompileXml.class);
	
	@SuppressWarnings("unused")
	private XmlClass xmlClass;
	/**
	 * @author 
	 * 生成部门员工树
	 * @Return string 
	 */
	@SuppressWarnings("unchecked")
	public String deptUser() throws IOException{
		
		log.debug("开始进行部门与人员菜单编译。。。。。。。。。。。。。。");
		
		List user=Lc.getConn().createSuperSQLQuery(
				"select userno, username, CONCAT('D',deptno), 'b_user','true', phone from tb_sys_user where state=1");
		
		List dept=Lc.getConn().createSuperSQLQuery(
				"select CONCAT('D',deptno), deptName, CONCAT('D',parentNo), 'b_dept','false','' from tb_sys_dept where state=1");
		
		user.addAll(dept);
		
		
		Document doc=xmlClass.toXml(user, "D-1", 0, 2, new String[]{"id","text","pid","iconCls","leaf","phone"});
		String xml=XMLUtil.xmlToJson(doc).toJSONString();
		
		FileOutputStream fos = new FileOutputStream(WebUtil.getLocalWebRoot()+"/jbox_ext/data/deptuser.json");
		Writer out = new OutputStreamWriter(fos, "UTF-8");
		out.write(xml);
		
		
		out.flush();fos.flush();
		out.close();fos.close();
		return "success";
	}
	
	/**
	 * @author 
	 * 生成部门员工树
	 * @Return string 
	 */
	@SuppressWarnings("unchecked")
	public String dept() throws IOException{
		
		log.debug("开始进行部门菜单编译。。。。。。。。。。。。。。");
		
		List dept=Lc.getConn().createSuperSQLQuery(
				" select a.deptno,a.deptName,a.parentNo,'material' ,CASE when ISNULL(b.deptno) then 'true' else 'false' end as 'cc' from tb_sys_dept a left JOIN tb_sys_dept b " +
				" on a.deptno=b.parentno and a.state=1 and b.state=1 group by a.deptno,a.deptName,a.parentNo");
		
		Document doc=xmlClass.toXml(dept, "-1", 0, 2, new String[]{"id","text","pid","iconCls","leaf"});
		String xml=XMLUtil.xmlToJson(doc).toJSONString();
		
		FileOutputStream fos = new FileOutputStream(WebUtil.getLocalWebRoot()+"/jbox_ext/data/dept.json");
		Writer out = new OutputStreamWriter(fos, "UTF-8");
		out.write(xml);
		
		
		out.flush();fos.flush();
		out.close();fos.close();
		return "success";
	}

	public void setXmlClass(XmlClass xmlClass) {
		this.xmlClass = xmlClass;
	}

	public boolean build() {
		
		return true;
	}
}
