package cn.com.educate.app.util;

public class ChangeRecord {
	@Override
	public String toString() {
		return "ChangeRecord [rowNum=" + rowNum + ", name=" + name
				+ ", userNumAndLendNum=" + userNumAndLendNum + ", lendDate="
				+ lendDate + ", lendAmount=" + lendAmount + ", lendType="
				+ lendType + ", oldCrmName=" + oldCrmName + ", crmName="
				+ crmName + ", ccaName=" + ccaName + ", changeDate="
				+ changeDate + "]";
	}
	int rowNum;
	String name;
	String userNumAndLendNum;
	String lendDate;
	double lendAmount;
	String lendType;
	String oldCrmName;
	String crmName;
	String ccaName;
	String changeDate;
	
	public ChangeRecord(int rowNum, String name, String userNumAndLendNum,
			String lendDate, double lendAmount, String lendType,
			String oldCrmName, String crmName, String ccaName, String changeDate) {
		super();
		this.rowNum = rowNum;
		this.name = name;
		this.userNumAndLendNum = userNumAndLendNum;
		this.lendDate = lendDate;
		this.lendAmount = lendAmount;
		this.lendType = lendType;
		this.oldCrmName = oldCrmName;
		this.crmName = crmName;
		this.ccaName = ccaName;
		this.changeDate = changeDate;
	}
	public int getRowNum() {
		return rowNum;
	}
	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserNumAndLendNum() {
		return userNumAndLendNum;
	}
	public void setUserNumAndLendNum(String userNumAndLendNum) {
		this.userNumAndLendNum = userNumAndLendNum;
	}
	public String getLendDate() {
		return lendDate;
	}
	public void setLendDate(String lendDate) {
		this.lendDate = lendDate;
	}
	public double getLendAmount() {
		return lendAmount;
	}
	public void setLendAmount(double lendAmount) {
		this.lendAmount = lendAmount;
	}
	public String getLendType() {
		return lendType;
	}
	public void setLendType(String lendType) {
		this.lendType = lendType;
	}
	public String getOldCrmName() {
		return oldCrmName;
	}
	public void setOldCrmName(String oldCrmName) {
		this.oldCrmName = oldCrmName;
	}
	public String getCrmName() {
		return crmName;
	}
	public void setCrmName(String crmName) {
		this.crmName = crmName;
	}
	public String getCcaName() {
		return ccaName;
	}
	public void setCcaName(String ccaName) {
		this.ccaName = ccaName;
	}
	public String getChangeDate() {
		return changeDate;
	}
	public void setChangeDate(String changeDate) {
		this.changeDate = changeDate;
	}
	
}
