package cn.com.educate.app;

import java.io.File;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.educate.app.web.InitSetupListener;

public class SysConfig {
	private Logger logger = LoggerFactory.getLogger(SysConfig.class);

	private Configuration config = null;
	
	private static SysConfig sysConfig = null;
	
	public Configuration getConfiguration(){
		return config;
	}
	private synchronized Configuration loadProperties(
			String config_file) {

		XMLConfiguration xmlconfiguration = null;
		try {
			xmlconfiguration = new XMLConfiguration(config_file);
			xmlconfiguration.setAutoSave(true);
			return xmlconfiguration;
		} catch (ConfigurationException ex) {
			logger.error("xml文件载入出错，请检查", ex);
		}
		return null;
	}

	private void init() {
		config = loadProperties(InitSetupListener.rootPath + File.separator+"WEB-INF"+File.separator+"classes"+File.separator+"sysconfig.xml");
	}
	
	private SysConfig(){
		init();
	}
	
	public static SysConfig getInstance(){
		if(sysConfig == null){
			sysConfig = new SysConfig();
		}
		return sysConfig;
	}
}
