package cn.com.educate.app.rmi.impl;

import cn.com.educate.app.rmi.IHessianService;
import cn.com.educate.app.web.InitSetupListener;

import com.caucho.hessian.server.HessianServlet;

public class HessianServiceImpl extends HessianServlet implements IHessianService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3542828852435933947L;

	@Override
	public boolean attrSync(String coding) {
		boolean isSuccess = true;
		try{
			//InitSetupListener.replaceAttr2Map(coding);
			int i=1;
		}catch(Exception e){
			isSuccess = false;
			e.printStackTrace();
		}
		return isSuccess;
	}

}
