package cn.com.educate.app.util;

public enum EmployeeLeveCodeEnum {

	    /**
	     *  团队经理
	     */
	    teamLeader {
	        @Override
	        public String toString() {
	            return "TUAN";
	        }
	    },
	    
	    /**
	     * 客户经理
	     */
	    customerLeader{
	    	@Override
	        public String toString() {
	            return "KE";
	        }
	    },	    
	    /**
	     * 理财客户经理
	     */
	    lendingCustomerLeader{
	    	@Override
	        public String toString() {
	            return "LI";
	        }
	    }
	    
	    
}
