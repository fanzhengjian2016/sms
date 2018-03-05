package com.shenjun.web.dispatcher;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.dispatcher.Dispatcher;
import org.apache.struts2.dispatcher.mapper.ActionMapping;
import org.apache.struts2.dispatcher.ng.ExecuteOperations;
import org.apache.struts2.dispatcher.ng.InitOperations;
import org.apache.struts2.dispatcher.ng.PrepareOperations;
import org.apache.struts2.dispatcher.ng.filter.FilterHostConfig;
import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;



public class StrutsSuperPrepareAndExecuteFilter extends StrutsPrepareAndExecuteFilter {
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		InitOperations init = new InitOperations();
        
        try {
            FilterHostConfig config = new FilterHostConfig(filterConfig);
            init.initLogging(config);
            Dispatcher dispatcher = init.initDispatcher(config);
           
        	/**
        	 * auto struts2 params
        	 */
        	Struts2DispatcherLoad.load(dispatcher);
        	
        	dispatcher.getConfigurationManager().getConfiguration().rebuildRuntimeConfiguration();
                  
            init.initStaticContentLoader(config, dispatcher);
            prepare = new PrepareOperations(filterConfig.getServletContext(), dispatcher);
            execute = new ExecuteOperations(filterConfig.getServletContext(), dispatcher);
			this.excludedPatterns = init.buildExcludedPatternsList(dispatcher);
			
            postInit(dispatcher, filterConfig);
        } finally {
            init.cleanup();
        }

    }
	
    @Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        try {
            prepare.setEncodingAndLocale(request, response);
            prepare.createActionContext(request, response);
            prepare.assignDispatcherToThread();
			if ( excludedPatterns != null && prepare.isUrlExcluded(request, excludedPatterns)) {
				chain.doFilter(request, response);
			} else {
				request = prepare.wrapRequest(request);
				ActionMapping mapping = prepare.findActionMapping(request, response, true);
				if (mapping == null) {
					boolean handled = execute.executeStaticResourceRequest(request, response);
					if (!handled) {
						chain.doFilter(request, response);
					}
				} else {
					execute.executeAction(request, response, mapping);
					
//					ActionConfig ac= ActionContext.getContext().getActionInvocation().getProxy().getConfig();
//					
//					System.out.println(ac.getResults().get("success"));
					
					//request.getRequestDispatcher("/index.jsp").forward(request, response);
				}
			}
        } finally {
            prepare.cleanupRequest(request);
        }
    }
}
