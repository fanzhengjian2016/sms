package com.shenjun.web.dispatcher;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.dispatcher.Dispatcher;

import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.config.entities.ActionConfig;
import com.opensymphony.xwork2.config.entities.PackageConfig;
import com.opensymphony.xwork2.config.entities.ResultConfig;
import com.shenjun.util.RegexUtil;
import com.shenjun.util.WebUtil;

/**
 * @description load struts2 file
 * @author shenjun
 * {@link sj_enginner@sina.com}
 * 版权个人所有，严禁侵权。
 */

public class Struts2DispatcherLoad {
	
	private static final Log log = LogFactory.getLog(Struts2DispatcherLoad.class);
	
	private static Configuration cfg = null;
	
	private static final String PackageConfigPrefix="Struts2-Ioc-";
	
	public static final String AUTOSTINGVALUE="ausuccess";
	
	public static final String FORWORDNAME="chainname";
	
	public static final String REDIRECT="redirectcode";
	
	public static final String STREAM="streamname";
	
	public static final String SJASPER="sjasper";
	
	private static Map<String,PackageConfig.Builder> pcg = new HashMap<String,PackageConfig.Builder>();
	
	private static Dispatcher dispatcher=null;
	
	/**
	 * load
	 * @param dispatcher
	 */
	protected static void load(Dispatcher dispatcher){
		try {
			Struts2DispatcherLoad.cfg=dispatcher.getConfigurationManager().getConfiguration();
			Struts2DispatcherLoad.dispatcher=dispatcher;
			
			eachFile(new File(WebUtil.getClassesRoot()));
			
			for(Map.Entry<String,PackageConfig.Builder> pcb : pcg.entrySet()){
				log.debug("load in struts2 config file ->"+pcb.getKey());
				cfg.addPackageConfig(pcb.getKey(), pcb.getValue().build());
			}
			
		} catch (ClassNotFoundException e) {
			log.error("Struts2DispatcherLoad load ClassNotFoundException:"+e.getMessage());
		}
	}
	/**
	 * each file list
	 * @param fl
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unused")
	private static void eachFile(File fl) throws ClassNotFoundException{
		if(fl.isFile()){
			List ls=null;
			if((ls=RegexUtil.getSpecifyRegex(
					"com[\\p{Punct}]web[\\p{Punct}][a-z]+[\\p{Punct}]action[\\p{Punct}][a-zA-Z]+Action"
					, fl.getAbsolutePath())
					).size()>0){
				String className=((ls.get(0)+"").replaceAll("[\\p{Punct}]", "."));
				
				String namespace=(className.substring(
						"com.web.".length(), 
						className.lastIndexOf(".action.")));
				
				String actionName=(className.substring(
						className.lastIndexOf(".action")+8,
						className.lastIndexOf("Action"))).toLowerCase();
				
				
				PackageConfig.Builder pc = pcg.get(PackageConfigPrefix+namespace);
				if(pc==null){
					PackageConfig.Builder d = new PackageConfig.Builder(PackageConfigPrefix+namespace);
		        	d.namespace("/"+namespace);
		        	List<PackageConfig> pcgls = new ArrayList<PackageConfig>();
		        	pcgls.add(cfg.getPackageConfig("Main-Struts2"));
		        	d.addParents(pcgls);
		        	
		        	pcg.put(PackageConfigPrefix+namespace, d);
		        	pc=d;
				}
				
				ActionConfig.Builder s=new ActionConfig.Builder(PackageConfigPrefix+namespace,actionName,className);
	        	
				ResultConfig.Builder rb=new ResultConfig.Builder(AUTOSTINGVALUE,"com.shenjun.web.dispatcher.ServletSuperDispatcherResult");
	        	rb.addParam("location", "/error.jsp");
	        	s.addResultConfig(rb.build());
	        	
	        	ResultConfig.Builder rb1=new ResultConfig.Builder(FORWORDNAME,"com.shenjun.web.dispatcher.ActionSuperChainResult");
	        	rb1.addParam("location", "/error.jsp");
	        	s.addResultConfig(rb1.build());
	        	
	        	ResultConfig.Builder rb2=new ResultConfig.Builder(REDIRECT,"com.shenjun.web.dispatcher.ServletSuperRedirectResult");
	        	rb2.addParam("location", "/error.jsp");
	        	s.addResultConfig(rb2.build());
	        	
	        	ResultConfig.Builder rb3=new ResultConfig.Builder(STREAM,"com.shenjun.web.dispatcher.StreamSuperResult");
	        	//rb3.addParam("location", "/error.jsp");
	        	s.addResultConfig(rb3.build());
	        	
	        	ResultConfig.Builder rb4=new ResultConfig.Builder(SJASPER,"com.shenjun.web.dispatcher.SJasperReportsResult");
	        	//rb4.addParam("location", "/error.jsp");
	        	s.addResultConfig(rb4.build());
	        	
	        	ActionConfig ac = s.build();
	        	pc.addActionConfig(actionName, ac);

			}
		}else if(fl.isDirectory()){
			for(File cfl : fl.listFiles())
				eachFile(cfl);
		}
	}
	
	/**
	 * debug actionConfig
	 *
	 */
	public void debug(){
        Map<String, PackageConfig> cp= dispatcher.getConfigurationManager().getConfiguration().getPackageConfigs();
        for(Map.Entry<String, PackageConfig> cpd : cp.entrySet()){
        	System.out.println("---------->"+cpd.getKey()+":"+cpd.getValue());
        	
        	for(Map.Entry<String, ActionConfig> pc:cpd.getValue().getActionConfigs().entrySet()){
        		//System.out.println("<>----<>"+pc.getKey()+":"+pc.getValue().getMethodName()+":"+pc.getValue().getClassName()+":"+pc.getValue().getName());
        		System.out.println(pc.getValue());
        	}
        	if("Systems-Ioc-Debug".equals(cpd.getKey())){
        		System.out.println(cpd.getValue());
        	}
        }
	}

}
