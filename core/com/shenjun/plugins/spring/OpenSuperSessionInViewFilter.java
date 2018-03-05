package com.shenjun.plugins.spring;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;

import com.shenjun.manager.CommonManager;
import com.shenjun.plugins.sftp.SftpLocal;
import com.shenjun.web.thread.Lc;
import com.shenjun.web.thread.LocalContent;


/**
 * @description 
 * @author shenjun
 * {@link sj_enginner@sina.com}
 * 版权个人所有，严禁侵权。
 */

public class OpenSuperSessionInViewFilter implements Filter {

	private static Logger log = Logger.getLogger(OpenSuperSessionInViewFilter.class.getName());
	
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("deprecation")
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		filterChain.doFilter(servletRequest, servletResponse);
		
		Map<String, CommonManager> cmap=LocalContent.getAllCm();
		if(cmap!=null){
			try{
				if(LocalContent.getError()!=null&&LocalContent.getError().length()>0){
					log.debug("("+Thread.currentThread().getName()+")error thread rollback.");
					Lc.isCommit(false);//回滚
				}else{
					//log.debug("("+Thread.currentThread().getName()+")success,thread commited");
					Lc.isCommit(true); //提交
				} 
			}catch(Exception e){
				log.error("Spring session doFilter isCommit error:"+e.getMessage(),e.getCause());
			}
			
			for(Map.Entry<String, CommonManager> ent:cmap.entrySet()){
				try{
					if(ent.getValue().isConnn()){
						ent.getValue().releaseConnection(ent.getValue().getConnection());
					}
//					if(ent.getValue().getLocalSession().isOpen()){
//						ent.getValue().getLocalSession().flush();
//						SessionFactoryUtils.closeSession(ent.getValue().getLocalSession());
//						log.debug("("+Thread.currentThread().getName()+")closeing session,name:"+ent.getKey()+
//								",state:"+ent.getValue().getLocalSession().isOpen());
//					}
				}catch(Exception e){
					log.error("Spring session doFilter closeSession error:"+e.getMessage(),e.getCause());
				}
			}
			
			cmap.clear();
		}
		
		Map<String,SftpLocal> sftpMap = LocalContent.getAllSfptCm();
		if(sftpMap!=null){
			for(Map.Entry<String, SftpLocal> ent:sftpMap.entrySet()){
				ent.getValue().closeChannel();
				log.debug("("+Thread.currentThread().getName()+") sftp closeChannel key:["+ent.getKey()+"].");
			}
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
		log.debug("init session close filter");
	}

}
