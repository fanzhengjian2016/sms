package com.shenjun.plugins.axis2;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.deployment.AbstractDeployer;
import org.apache.axis2.deployment.DeploymentEngine;
import org.apache.axis2.deployment.repository.util.DeploymentFileData;
import org.apache.axis2.deployment.util.Utils;
import org.apache.axis2.description.AxisOperation;
import org.apache.axis2.description.AxisService;
import org.apache.axis2.description.AxisServiceGroup;
import org.apache.axis2.engine.MessageReceiver;
import org.apache.axis2.i18n.Messages;
import org.apache.axis2.jsr181.JSR181Helper;
import org.apache.axis2.jsr181.WebServiceAnnotation;
import org.apache.axis2.util.Loader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shenjun.annotation.WebServiceDescription;

/**
 * org.apache.axis2.deployment.POJODeployer
 * 
 * @author shenjun
 * 
 */

public class POJOSuperDeployer extends AbstractDeployer {
	private static Log log = LogFactory.getLog(POJOSuperDeployer.class);
	private ConfigurationContext configCtx;
	private String directory;

	public void init(ConfigurationContext configCtx) {
		this.configCtx = configCtx;
	}

	public void deploy(DeploymentFileData deploymentFileData) {
		ClassLoader threadClassLoader = Thread.currentThread()
				.getContextClassLoader();

		String serviceHierarchy = Utils.getServiceHierarchy(
				deploymentFileData.getAbsolutePath(), this.directory);

		if (serviceHierarchy == null)
			serviceHierarchy = "";

		try {
			String className;
			String extension = DeploymentFileData
					.getFileExtension(deploymentFileData.getName());
			if ("class".equals(extension)) {
				AxisService axisService;
				File file = deploymentFileData.getFile();
				File parentFile = file.getParentFile();
				ClassLoader classLoader = Utils.getClassLoader(this.configCtx
						.getAxisConfiguration().getSystemClassLoader(),
						parentFile, this.configCtx.getAxisConfiguration()
								.isChildFirstClassLoading());

				Thread.currentThread().setContextClassLoader(classLoader);
				className = Utils.getClassNameFromResourceName(file.getName());

				/**
				 * add code
				 */
                className = "com.webservice.service."+className.replaceAll(".class", "");
                
				Class clazz = Loader.loadClass(className);
				log.info(Messages.getMessage("deployingpojo", serviceHierarchy
						+ className, deploymentFileData.getFile()
						.getAbsolutePath()));

				WebServiceAnnotation annotation = JSR181Helper.INSTANCE
						.getWebServiceAnnotation(clazz);

				if (annotation != null) {
					axisService = createAxisService(classLoader, className,
							deploymentFileData.getFile().toURL());
				} else {
					axisService = createAxisServiceUsingAnnogen(className,
							classLoader, deploymentFileData.getFile().toURL());
					
					/**
					 * add code
					 */
					WebServiceDescription ws=(WebServiceDescription)clazz.getAnnotation(WebServiceDescription.class);
                    if(ws!=null){
                    	axisService.setDocumentation(ws.description());
                    }
				}

				axisService.setName(serviceHierarchy + axisService.getName());
				this.configCtx.getAxisConfiguration().addService(axisService);
			} else if ("jar".equals(extension)) {
				List classList = Utils.getListOfClasses(deploymentFileData);
				ArrayList axisServiceList = new ArrayList();
				for (Iterator i$ = classList.iterator(); i$.hasNext();) {
					className = (String) i$.next();
					ArrayList urls = new ArrayList();
					urls.add(deploymentFileData.getFile().toURL());
					urls.add(this.configCtx.getAxisConfiguration()
							.getRepository());
					String webLocation = DeploymentEngine
							.getWebLocationString();
					if (webLocation != null)
						urls.add(new File(webLocation).toURL());

					ClassLoader classLoader = Utils.createClassLoader(urls,
							this.configCtx.getAxisConfiguration()
									.getSystemClassLoader(), true,
							(File) this.configCtx.getAxisConfiguration()
									.getParameterValue("artifactsDIR"),
							this.configCtx.getAxisConfiguration()
									.isChildFirstClassLoading());

					Thread.currentThread().setContextClassLoader(classLoader);
					Class clazz = Loader.loadClass(className);

					WebServiceAnnotation annotation = JSR181Helper.INSTANCE
							.getWebServiceAnnotation(clazz);

					if (annotation != null) {
						AxisService axisService = createAxisService(
								classLoader, className, deploymentFileData
										.getFile().toURL());

						axisServiceList.add(axisService);
					}
				}

				if (axisServiceList.size() > 0) {
					AxisServiceGroup serviceGroup = new AxisServiceGroup();
					serviceGroup.setServiceGroupName(serviceHierarchy
							+ deploymentFileData.getName());

					for (Iterator i$ = axisServiceList.iterator(); i$.hasNext();) {
						Object anAxisServiceList = (AxisService) i$.next();
						AxisService axisService = (AxisService) anAxisServiceList;
						axisService.setName(serviceHierarchy
								+ axisService.getName());
						serviceGroup.addService(axisService);
					}
					this.configCtx.getAxisConfiguration().addServiceGroup(
							serviceGroup);
				} else {
					String msg = "Error:\n No annotated classes found in the jar: "
							+ deploymentFileData.getFile().getName()
							+ ". Service deployment failed.";

					log.error(msg);
					this.configCtx
							.getAxisConfiguration()
							.getFaultyServices()
							.put(deploymentFileData.getFile().getAbsolutePath(),
									msg);
				}
			}

			super.deploy(deploymentFileData);
		} catch (Exception e) {
			log.debug(Messages.getMessage("stroringfaultyservice",
					e.getMessage()), e);
			storeFaultyService(deploymentFileData, e);
		} catch (Throwable t) {
			log.debug(Messages.getMessage("stroringfaultyservice",
					t.getMessage()), t);
			storeFaultyService(deploymentFileData, t);
		} finally {
			if (threadClassLoader != null)
				Thread.currentThread().setContextClassLoader(threadClassLoader);
		}
	}

	private void storeFaultyService(DeploymentFileData deploymentFileData,
			Throwable t) {
		StringWriter errorWriter = new StringWriter();
		PrintWriter ptintWriter = new PrintWriter(errorWriter);
		t.printStackTrace(ptintWriter);
		String error = "Error:\n" + errorWriter.toString();
		this.configCtx.getAxisConfiguration().getFaultyServices()
				.put(deploymentFileData.getFile().getAbsolutePath(), error);
	}

	private AxisService createAxisService(ClassLoader classLoader,
			String className, URL serviceLocation)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, AxisFault {
		AxisService axisService;
		Class claxx;
		try {
			claxx = Class
					.forName("org.apache.axis2.jaxws.description.DescriptionFactory");

			Method mthod = claxx.getMethod("createAxisService",
					new Class[] { Class.class });
			Class pojoClass = Loader.loadClass(classLoader, className);
			axisService = (AxisService) mthod.invoke(claxx,
					new Object[] { pojoClass });
			if (axisService != null) {
				Iterator operations = axisService.getOperations();
				while (operations.hasNext()) {
					AxisOperation axisOperation = (AxisOperation) operations
							.next();
					if (axisOperation.getMessageReceiver() == null)
						try {
							Class jaxwsMR = Loader
									.loadClass("org.apache.axis2.jaxws.server.JAXWSMessageReceiver");

							MessageReceiver jaxwsMRInstance = (MessageReceiver) jaxwsMR
									.newInstance();

							axisOperation.setMessageReceiver(jaxwsMRInstance);
						} catch (Exception e) {
							log.debug("Error occurde while loading JAXWSMessageReceiver for "
									+ className);
						}
				}

			}

			axisService.setElementFormDefault(false);
			axisService.setFileName(serviceLocation);
			Utils.fillAxisService(axisService,
					this.configCtx.getAxisConfiguration(), new ArrayList(),
					new ArrayList());
		} catch (Exception e) {
			log.info(Messages.getMessage("jaxwsjarsmissing", e.getMessage()), e);
			axisService = createAxisServiceUsingAnnogen(className, classLoader,
					serviceLocation);
		}
		return axisService;
	}

	private AxisService createAxisServiceUsingAnnogen(String className,
			ClassLoader classLoader, URL serviceLocation)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, AxisFault {
		HashMap messageReciverMap = new HashMap();
		Class inOnlyMessageReceiver = Loader
				.loadClass("org.apache.axis2.rpc.receivers.RPCInOnlyMessageReceiver");

		MessageReceiver messageReceiver = (MessageReceiver) inOnlyMessageReceiver
				.newInstance();

		messageReciverMap.put("http://www.w3.org/ns/wsdl/in-only",
				messageReceiver);

		Class inoutMessageReceiver = Loader
				.loadClass("org.apache.axis2.rpc.receivers.RPCMessageReceiver");

		MessageReceiver inOutmessageReceiver = (MessageReceiver) inoutMessageReceiver
				.newInstance();

		messageReciverMap.put("http://www.w3.org/ns/wsdl/in-out",
				inOutmessageReceiver);

		messageReciverMap.put("http://www.w3.org/ns/wsdl/robust-in-only",
				inOutmessageReceiver);

		AxisService axisService = AxisService.createService(className,
				this.configCtx.getAxisConfiguration(), messageReciverMap, null,
				null, classLoader);

		axisService.setFileName(serviceLocation);
		return axisService;
	}

	public void setMessageReceivers(AxisService service) {
		Iterator iterator = service.getOperations();
		while (iterator.hasNext()) {
			AxisOperation operation = (AxisOperation) iterator.next();
			String MEP = operation.getMessageExchangePattern();
			if (MEP != null)
				try {
					if ("http://www.w3.org/ns/wsdl/in-only".equals(MEP)) {
						Class inOnlyMessageReceiver = Loader
								.loadClass("org.apache.axis2.rpc.receivers.RPCInOnlyMessageReceiver");

						MessageReceiver messageReceiver = (MessageReceiver) inOnlyMessageReceiver
								.newInstance();

						operation.setMessageReceiver(messageReceiver);
					} else {
						Class inoutMessageReceiver = Loader
								.loadClass("org.apache.axis2.rpc.receivers.RPCMessageReceiver");

						MessageReceiver inOutmessageReceiver = (MessageReceiver) inoutMessageReceiver
								.newInstance();

						operation.setMessageReceiver(inOutmessageReceiver);
					}
				} catch (ClassNotFoundException e) {
					log.error(e.getMessage(), e);
				} catch (InstantiationException e) {
					log.error(e.getMessage(), e);
				} catch (IllegalAccessException e) {
					log.error(e.getMessage(), e);
				}
		}
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public void setExtension(String extension) {
	}

	public void undeploy(String fileName)
			throws org.apache.axis2.deployment.DeploymentException {
		String serviceHierarchy = Utils.getServiceHierarchy(fileName,
				this.directory);

		if (serviceHierarchy == null) {
			serviceHierarchy = "";
		}

		fileName = Utils.getShortFileName(fileName);
		if (fileName.endsWith(".class")) {
			String className = Utils.getClassNameFromResourceName(fileName);
			className = serviceHierarchy + className;
			try {
				AxisServiceGroup serviceGroup = this.configCtx
						.getAxisConfiguration().removeServiceGroup(className);

				this.configCtx.removeServiceGroupContext(serviceGroup);
				log.info(Messages.getMessage("serviceremoved", className));
			} catch (AxisFault axisFault) {
				log.debug(
						Messages.getMessage("faultyserviceremoval",
								axisFault.getMessage()), axisFault);
				this.configCtx.getAxisConfiguration().removeFaultyService(
						fileName);
			}
		} else if (fileName.endsWith(".jar")) {
			fileName = serviceHierarchy + fileName;
			try {
				AxisServiceGroup serviceGroup = this.configCtx
						.getAxisConfiguration().removeServiceGroup(fileName);

				this.configCtx.removeServiceGroupContext(serviceGroup);
				log.info(Messages.getMessage("serviceremoved", fileName));
			} catch (AxisFault axisFault) {
				log.debug(
						Messages.getMessage("faultyserviceremoval",
								axisFault.getMessage()), axisFault);
				this.configCtx.getAxisConfiguration().removeFaultyService(
						fileName);
			}
		}
		super.undeploy(fileName);
	}
}