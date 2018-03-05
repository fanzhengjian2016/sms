package com.shenjun.plugins.axis2;

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.description.AxisOperation;
import org.apache.axis2.description.AxisService;
import org.apache.axis2.engine.AbstractDispatcher;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shenjun.db.conn.DbConnections;
/**
 * axis dispatcher
 * @author shenjun
 *
 */
public class JxStatusDispatcher extends AbstractDispatcher {
	private final Log log = LogFactory.getLog(DbConnections.class);

	@Override
	public InvocationResponse invoke(MessageContext msContext) throws AxisFault {
		String axisName = msContext.getAxisService().getName();
		AxisOperation operaton = msContext.getAxisOperation();
		String method = operaton.getName()+"";
		String params = msContext.getEnvelope().toString();

		this.log.info("request WsName:" + axisName + ",method:" + method
				+ ",params:" + params);

		return super.invoke(msContext);
	}

	public AxisOperation findOperation(AxisService service,
			MessageContext msContext) throws AxisFault {
		this.log.debug("------------JxStatusDispatcher.findOperation-------------------------");
		return null;
	}

	public AxisService findService(MessageContext msContext) throws AxisFault {
		this.log.debug("------------JxStatusDispatcher.findService-------------------------");
		return null;
	}

	public void initDispatcher() {
	}
}
