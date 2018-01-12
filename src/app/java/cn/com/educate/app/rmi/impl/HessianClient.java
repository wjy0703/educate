package cn.com.educate.app.rmi.impl;

import java.net.MalformedURLException;

import cn.com.educate.app.rmi.IHessianService;

import com.caucho.hessian.client.HessianProxyFactory;

public class HessianClient {

	public static void main(String[] args){
	       String url = "http://localhost:8080/unicomkbs/remote/attrsync";
	       HessianProxyFactory factory = new HessianProxyFactory();
	       IHessianService hessian = null;
	       try {
	    	   hessian = (IHessianService)factory.create(IHessianService.class, url);
	       } catch (MalformedURLException e) {
	           e.printStackTrace();
	       }
	       System.out.println("isSuccess: " + hessian.attrSync(""));
	   }
}
