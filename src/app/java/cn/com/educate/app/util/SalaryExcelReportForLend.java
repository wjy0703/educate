package cn.com.educate.app.util;



import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.tool.hbm2x.StringUtils;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

/**
 * 出借段 业绩统计，按照excel模板生成统计报表
 * @author xjs
 *
 */
public class SalaryExcelReportForLend {
	private  ActiveXComponent xl;
	private  Dispatch workbooks = null;
	private  Dispatch workbook = null;
	private  Dispatch sheet = null;
	private  Dispatch sheets = null;
	private  Dispatch document = null;
	private  String filename = null;
	private  boolean readonly = false;
	private static final String[] sumCols = {"E","F","G","H","I","J","K","L","M","N","O","P","Q"};
	private static final Map<String,String[]> productToColumns;
	//模板中都应当门店经理与团队经理所在地行数
	private int departRow  = 4;
	private int templateTeamRow = 5;
	
	//基础数据信息
	private static Map<String,Map<String,Integer>> basicData;
	
	static{
		//其中的key值为从数据库查询出来的键值首字母集合,例如JDY  则存在JDYCOUNT和JDYSUM两个字段，代表是件数和总额
		productToColumns = new HashMap<String,String[]>();
		productToColumns.put("JDY",new String[]{"E","F"});//季度盈
		productToColumns.put("SJY",new String[]{"G","H"});//双季盈o
		productToColumns.put("NNY",new String[]{"I","J"});//年年盈o
		productToColumns.put("XHT",new String[]{"K","L"});//信和通o
		productToColumns.put("YXT",new String[]{"M","N"});//月息通o
		//productToColumns.put("月满盈",new String[]{"O","P"});//月满盈
	}

    private void initBasicData(){
    	if(basicData == null){
    		basicData = new HashMap<String, Map<String, Integer>>();
	    	setOperationSheetByName("基础数据");
	    	//获取数据所在excel表中的范围，主要为行开始与结束，列的开始与结束
	    	Map<String,Object>  range = getColumnRangle();
	    	char columnOfPosistion =  range.get("columnOfPosistion").toString().charAt(0);
	    	char columnOfEndData =   range.get("columnOfEndData").toString().charAt(0);;
	    	int rowStart = Integer.parseInt(range.get("rowStart").toString());
	    	int rowEnd = Integer.parseInt(range.get("rowEnd").toString());
	    	for(int index = rowStart ; index <= rowEnd ; index++){
	    		//开始列作为键值，例如团队经理二级等,其他为数据
	    		char c = columnOfPosistion;
	    		String position = getValue(""+ c + index);
	            c = (char)++ c;
	    		Map<String,Integer> positionData = new HashMap<String,Integer>();
				for( ; c <= columnOfEndData ; c = (char)++c){
					String dataStr = getValue(""+c + index);
					try{
						if(!"null".equals(dataStr)){
							Double dataDouble  =  Double.parseDouble(dataStr);
							Integer data = dataDouble.intValue();
							String description = getDataDescripttion(rowStart,c);
							int pos = description.indexOf("(万元)");
							if(pos>0){
								data *= 10000;
								description = description.substring(0,pos);
							}
							positionData.put(description, data);
						}
					}catch(Exception e){
						System.out.println("出现: " + getDataDescripttion(rowStart,c));
					}
				}				
				basicData.put(position, positionData);				
			}	
    	}
    }
    /**
     * 返回基础数据的描述，例如职级，基本工资等，对应excel中基础数据表格的表头
     * @param rowStart
     * @param c
     * @return
     */
    private String getDataDescripttion(int rowStart, char c) {
		
    	while(--rowStart >=1){
    		String description = getValue("" + c + rowStart);
    		if(  !"null".equals(description) )
    			return description;
    	}
		return "notKown";
	}

	/**
     * 获取基础数据所在excel的开始列名和结束列名等信息	
     * @return
     */
	private Map<String, Object> getColumnRangle() {
		Map<String,Object> columnRange = new HashMap<String,Object>();
		//要求开始的列与结束列，必须是单个字符
		columnRange.put("columnOfPosistion", "B");
		columnRange.put("columnOfEndData", "Q");
		columnRange.put("rowStart", 5);
		columnRange.put("rowEnd", 21);
		return columnRange;
	}


	// 打开Excel文档
	public  void openExcel(String file, boolean f) {
		try {
			ComThread.InitSTA(); 
			filename = file;
			if(xl==null)
				xl = new ActiveXComponent("Excel.Application");
			xl.setProperty("Visible", new Variant(f));
			if(workbooks==null)
				workbooks = xl.getProperty("Workbooks").toDispatch();
			workbook = Dispatch.invoke(
					(Dispatch) workbooks,
					"Open",
					Dispatch.Method,
					new Object[] { filename, new Variant(false),
							new Variant(readonly) },// 是否以只读方式打开
					new int[1]).toDispatch();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public  void createNewDocument() {
		xl = new ActiveXComponent("Excel.Application");
		// Find the Documents collection object maintained by Word
		Dispatch documents = Dispatch.get(xl, "Documents").toDispatch();
		// Call the Add method of the Documents collection to create
		// a new document to edit
		document = Dispatch.call(documents, "Add").toDispatch();
	}

	// 关闭并保存Excel文档
	public  void savaAndClose(String newFileName,boolean f) {
		try {
			Dispatch.call((Dispatch) workbook, "SaveAs",newFileName);
			Dispatch.call((Dispatch) workbook, "Close", new Variant(f));
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			releaseSource();
		}
	}
	
	/**
     * 释放资源
     */
    public  void releaseSource(){
        if(xl!=null){
            xl.invoke("Quit", new Variant[0]);
            xl = null;
        }
        workbooks = null;
        ComThread.Release();
    }

	// 写入值
	public  void setValue(String position, String type, String value) {
		//sheet = Dispatch.get((Dispatch) workbook, "ActiveSheet").toDispatch();
		Object cell = Dispatch.invoke((Dispatch) sheet, "Range", Dispatch.Get,
				new Object[] { position }, new int[1]).toDispatch();
		Dispatch.put((Dispatch) cell, type, value);
	}
	// 写入值
	public  void setValue(String position, String type, double value) {
		//sheet = Dispatch.get((Dispatch) workbook, "ActiveSheet").toDispatch();
		Object cell = Dispatch.invoke((Dispatch) sheet, "Range", Dispatch.Get,
				new Object[] { position }, new int[1]).toDispatch();
		Dispatch.put((Dispatch) cell, type, value);
	}
	
	/**
	 * 
	 * @param rowNum
	 * @param templateTeamRow
	 * @param departRow
	 * @return
	 */
	public  int copyRow(int rowNum,int templateTeamRow,int departRow){
		Dispatch row = Dispatch.call((Dispatch)sheet, "Rows", new Variant(templateTeamRow)).toDispatch();
		Dispatch.call(row, "Copy");
		//Dispatch row2 = Dispatch.call((Dispatch)sheet, "Rows", new Variant(templateTeamRow)).toDispatch();
		Dispatch.call(row, "Insert");
		//选择理财经理的插入位置,因为上面已经插入一行，所以插入位置为templateTeamRow+2
		Dispatch customerRow = Dispatch.call((Dispatch)sheet, "Rows", new Variant(templateTeamRow+2)).toDispatch();
		
		for(int i = 0; i < rowNum ; i++){
			Dispatch.call(customerRow, "Copy");
			Dispatch.call(row, "Insert");
		}

		changeSumFormat(templateTeamRow,sumCols,rowNum);
		changeDepartSumFormat(templateTeamRow,sumCols,departRow);
		return templateTeamRow + rowNum + 1;
   }
	
   /**
    * 修改门店经理所在行的excel公式
    * @param templateTeamRow
    * @param sumCols
    * @param departRow
    */
    private  void changeDepartSumFormat(int templateTeamRow,String[] sumCols, int departRow) {
    	
	   for(int i = 0 ; i < sumCols.length ; i++){
		   String cellPosition = sumCols[i] + departRow;
		   String formula =getFormula(cellPosition);
		   if(formula != null){
			   if(formula.startsWith("=")){
				   formula += "+" + sumCols[i]+templateTeamRow;
			   }else{
				   formula = "=" + sumCols[i]+templateTeamRow;
			   }
		   }else{
			   formula = "=" + sumCols[i]+templateTeamRow;
		   }
		   setAnother(cellPosition,"Value",formula);
	   }
	}

   /**
    * 修改团队经理所在行的计算公式
    * @param templateTeamRow
    * @param sumCols
    * @param rowNumsOfSum
    */
   private  void changeSumFormat(int templateTeamRow, String[] sumCols,int rowNumsOfSum) {
	   int begin = templateTeamRow + 1;
	   int end =   templateTeamRow + rowNumsOfSum;
	   for(int i = 0 ; i < sumCols.length ; i++){
		   String cellPosition =  sumCols[i] + templateTeamRow;
		   String sumFormula = "=SUM(" +  sumCols[i] + begin+":" + sumCols[i] + end +")";
		   setAnother(cellPosition, "Formula",sumFormula);
	   }
		
	}
	 /**
    *  得到sheets的集合对象
    * @return
    */
   public Dispatch getSheets() {
       if(sheets==null)
           sheets = Dispatch.get(workbook, "sheets").toDispatch();
       return sheets;
   }
   
   /**
    * 设置要操作的sheet
    * @param sheetName
    */
   public void setOperationSheetByName(String sheetName){
	   sheet = getSheetByName(sheetName);
   }
   
   /**
    * 设置要操作的sheet
    * @param sheetName
    */
   public void setOperationSheetByIndex(int index){
	   sheet = getSheetByIndex(index);
   }
   
   
   /**
    * 得到当前sheet
    * @return
    */
   public Dispatch getCurrentSheet() {
       return Dispatch.get(workbook, "ActiveSheet").toDispatch();
   }

   /**
    * 通过工作表名字得到工作表
    * @param name sheetName
    * @return
    */
   public Dispatch getSheetByName(String name) {
       return Dispatch.invoke(getSheets(), "Item", Dispatch.Get, new Object[]{name}, new int[1]).toDispatch();
   }
   
   /**
    * 通过工作表索引得到工作表(第一个工作簿index为1)
    * @param index
    * @return  sheet对象
    */
   public Dispatch getSheetByIndex(Integer index) {
       return Dispatch.invoke(getSheets(), "Item", Dispatch.Get, new Object[]{index}, new int[1]).toDispatch();
   }
   
   public  void deleteRow(int rowNum){
		//sheet = Dispatch.get((Dispatch) workbook, "ActiveSheet").toDispatch();
		Dispatch row = Dispatch.call((Dispatch)sheet, "Rows", new Variant(rowNum)).toDispatch();
		Dispatch.call(row, "Delete");
   }
	
	private  void setAnother(String position, String type,String formula) {
		//sheet = Dispatch.get((Dispatch) workbook, "ActiveSheet").toDispatch();
		Object cell = Dispatch.invoke((Dispatch) sheet, "Range", Dispatch.Get,
				new Object[] { position }, new int[1]).toDispatch();
		Dispatch.put((Dispatch) cell, type, formula);
	}
	

	// 读取值
	private  String getValue(String position) {
		Object cell = Dispatch.invoke((Dispatch) sheet, "Range", Dispatch.Get,new Object[] { position }, new int[1]).toDispatch();
		String value = Dispatch.get((Dispatch) cell, "Value").toString();
		return value;
	}
	private  String getFormula(String position) {
		Object cell = Dispatch.invoke((Dispatch) sheet, "Range", Dispatch.Get,new Object[] { position }, new int[1]).toDispatch();
		String value = Dispatch.get((Dispatch) cell, "Formula").toString();
		return value;
	}


	private int copyRecordToSheet(Dispatch recordsSheet,List<Map<String, Object>> items, int firstRowOfRecord) {
		if(items != null ){
			for(int i = 0; i < items.size() ; i++){
				Map<String,Object> singleValue = items.get(i);
				if(singleValue.get("FKJE") != null && StringUtils.isNotEmpty(singleValue.get("FKJE").toString())){
					setAnother("A"+ firstRowOfRecord,"Value",singleValue.get("QDRQ").toString());
					setAnother("B"+ firstRowOfRecord,"Value",singleValue.get("JKRXM") != null ?singleValue.get("JKRXM").toString():"");//JKRXM
					setAnother("C"+ firstRowOfRecord,"Value",singleValue.get("JKTYPE") != null ?singleValue.get("JKTYPE").toString():"");//JKTYPE
					setAnother("D"+ firstRowOfRecord,"Value",singleValue.get("HTJE") != null ?singleValue.get("HTJE").toString():"");//HTJE
					setAnother("E"+ firstRowOfRecord,"Value",singleValue.get("HKQS") != null ?singleValue.get("HKQS").toString():"");//HKQS
					setAnother("F"+ firstRowOfRecord,"Value",singleValue.get("FKJE") != null ?singleValue.get("FKJE").toString():"");//FKJE
					setAnother("G"+ firstRowOfRecord,"Value",singleValue.get("NAME") != null ?singleValue.get("NAME").toString():"");//NAME
					firstRowOfRecord++;
				}
			}
		}
		return firstRowOfRecord;
	}

	public static void main(String[] args) {
		    SalaryExcelReportForLend ser = new SalaryExcelReportForLend();
			String file = "c:\\lend.xls";
			ser.openExcel(file, false);// false为不显示打开Excel
			ser.setOperationSheetByName("基础数据");
			String s = ser.getValue("G4");
			ser.initBasicData();
			System.out.println(s);
			/*for(int index = 5 ; index <= 21 ; index++){
				for(char c = 'B' ; c <= 'Q' ; c = (char)++c){
					String str = ser.getValue(""+c + index);
					System.out.print(str + "              ");
				}
				System.out.println();
			}
			ser.setOperationSheetByName("理财部绩效提成统计表");
			
			int departRow  = 4;
			int templateTeamRow = 5;
			templateTeamRow=ser.copyRow(3, templateTeamRow, departRow);
			templateTeamRow=ser.copyRow(4, templateTeamRow, departRow);
			templateTeamRow=ser.copyRow(5, templateTeamRow, departRow);
			templateTeamRow=ser.copyRow(6, templateTeamRow, departRow);
			ser.deleteRow(templateTeamRow);
			ser.deleteRow(templateTeamRow);
			ser.savaAndClose("c:\\lendLionel.xls", false);*/
			
			
	}
	/**
	 * 写入数据到excel,
	 * @param data  数据
	 * @param file  模板文件
	 * @param generateFileName 生成的文件全路径
	 */
	public void writeLendData(Map<String, Object> data, String file,String generateFileName) {
		//写入原始数据，起始的行数
		int firstRowOfRawData = 2; 
		openExcel(file,false);
		initBasicData();
		/**
		 * 取得门店经理
		 */
		String manager = "";
		try{
			List<Map<String,Object>> managerMap = (List<Map<String, Object>>) data.get("CTLIST");
			for(int i = 0; i < managerMap.size() ; i++ ){
				Map<String,Object> item = managerMap.get(i);
				if(item != null ){
					String name = item.get("NAME") != null ? item.get("NAME").toString() :"";
					if(StringUtils.isNotEmpty(name)){
						manager = name;
						break;
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		setOperationSheetByName("理财部绩效提成统计表");
		//设置门店经理
		setValue("C" + departRow,"Value",manager);
		//团队列表信息
		Object lists = data.get("TMLIST");
		if(lists != null ){
		  @SuppressWarnings("unchecked")
		  List<Map<String,Object>> itemsOfMap = (List<Map<String,Object>>)lists;
		  for(int index = 0 ; index < itemsOfMap.size() ; index++){
			    //团队经理的名字
				String teamLeaderName = itemsOfMap.get(index).get("NAME") ==null ?"":itemsOfMap.get(index).get("NAME").toString();
				String teamLeaderPositionName = itemsOfMap.get(index).get("POSITION_NAME") ==null ?"":itemsOfMap.get(index).get("POSITION_NAME").toString();
				@SuppressWarnings("unchecked")//KHLIST
				Object sumObject = itemsOfMap.get(index).get("KHLIST");
				if(sumObject != null){
					@SuppressWarnings("unchecked")
					List<Map<String,Object>> sumItems = (List<Map<String,Object>>)sumObject;
					int rowNum = sumItems.size();
					//写入统计表当中
					if(rowNum > 0 ){						
						Dispatch row = Dispatch.call((Dispatch)sheet, "Rows", new Variant(templateTeamRow)).toDispatch();
						Dispatch.call(row, "Copy");
						Dispatch.call(row, "Insert");
						setValue("B" + templateTeamRow, "Value", teamLeaderName);
						setValue("C" + templateTeamRow, "Value", teamLeaderPositionName);

						//选择理财经理的插入位置,因为上面已经插入一行，所以插入位置为templateTeamRow+2
						Dispatch customerRow = Dispatch.call((Dispatch)sheet, "Rows", new Variant(templateTeamRow+2)).toDispatch();
						for(int i = 0; i < rowNum ; i++){
							Map<String,Object> singleValue = sumItems.get(i);
							Dispatch.call(customerRow, "Copy");
							Dispatch.call(row, "Insert");						
							setValue("B"+(templateTeamRow + i + 1) , "Value",singleValue.get("NAME").toString());
							setValue("C"+(templateTeamRow + i + 1) , "Value",singleValue.get("POSITION_NAME") != null? singleValue.get("POSITION_NAME").toString():"");
							setValue("D"+(templateTeamRow + i + 1) , "Value",teamLeaderName);
							String monthTaskCount = getMonthTaskCount(singleValue.get("POSITION_NAME"));
							setValue("R"+(templateTeamRow + i + 1) , "Value",monthTaskCount);
							//如果理财经理同时也是团队经理,设置一些列的值为空
							if(StringUtils.equals(teamLeaderName, singleValue.get("NAME").toString())){
								setValue("Y"+(templateTeamRow + i + 1) , "Value","");
								setValue("Z"+(templateTeamRow + i + 1) , "Value","");
								setValue("AA"+(templateTeamRow + i + 1) , "Value","");
								setValue("AB"+(templateTeamRow + i + 1) , "Value","");
								setValue("AC"+(templateTeamRow + i + 1) , "Value","");
								setValue("AD"+(templateTeamRow + i + 1) , "Value","");
								setValue("D"+(templateTeamRow + i + 1) , "Value","");
							}
							writeDataListValue(singleValue,templateTeamRow + i + 1);
							//写入数据，单条的数据库数据，写入原始数据表，人工对比需要使用
							firstRowOfRawData = writeSingleListValue(singleValue.get("NAME").toString(),teamLeaderName,singleValue.get("MXLIST"),firstRowOfRawData);
							setOperationSheetByName("理财部绩效提成统计表");

						}
						changeSumFormat(templateTeamRow,sumCols,rowNum);
						changeDepartSumFormat(templateTeamRow,sumCols,departRow);
						templateTeamRow += rowNum + 1;
					}
				}
		   }
		   deleteRow(templateTeamRow);
		   deleteRow(templateTeamRow);
		}
		savaAndClose(generateFileName ,false);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private int writeSingleListValue(String customerLeaderName, String teamLeaderName,Object object,int firstRowOfRawData) {
		 if(object != null){
			 setOperationSheetByName("原始数据");
			 List<Map> datas = (List<Map>)object;
			 for(int index = 0 ; index < datas.size() ; index++){
				 Map data = datas.get(index);
				 setValue("A" + firstRowOfRawData,"Value",data.get("JHTZRQ") != null ? data.get("JHTZRQ").toString() : "");
				 setValue("B" + firstRowOfRawData,"Value",customerLeaderName);
				 setValue("C" + firstRowOfRawData,"Value",teamLeaderName);				 
				 setValue("D" + firstRowOfRawData,"Value",data.get("TZSQBH") != null ? data.get("TZSQBH").toString() : "");
				 setValue("E" + firstRowOfRawData,"Value",data.get("CJRXM") != null ? data.get("CJRXM").toString() : "");
				 setValue("F" + firstRowOfRawData,"Value",data.get("TZCP_MC") != null ? data.get("TZCP_MC").toString() : "");
				 setValue("G" + firstRowOfRawData,"Value",data.get("JHTZJE") != null ? data.get("JHTZJE").toString() : "");
				 setValue("H" + firstRowOfRawData,"Value","1");
				 firstRowOfRawData++;
			 }
		 }
		 
		return firstRowOfRawData;
	}
	
	/**
	 * 取得月任务
	 * @param object 所属的名称，例如团队经理一级，团队经理二级等，必须和excel表中的完全对应
	 * @return
	 */
	private String getMonthTaskCount(Object object) {
		if(basicData == null)
			initBasicData();
		if(object == null)
			return "";
		String positionName = object.toString();
		Map<String, Integer> data = basicData.get(positionName);
		if(data == null)		
			return "";
		return data.get("月任务") != null ? data.get("月任务").toString() : "" ;
	}
	/**
	 * 写入数据，包括年年盈....季度盈等
	 * @param singleValue  包含了数据
	 * @param row          所属行
	 */
	private void writeDataListValue(Map<String, Object> singleValue, int row) {
		Set<String> products = productToColumns.keySet();
		Iterator<String> it = products.iterator();
		while(it.hasNext()){
			String key = it.next();
			if(singleValue.get(key + "COUNT") != null){
				String[] columns = productToColumns.get(key);
				setValue(columns[0] + row,"Value",singleValue.get(key + "SUM").toString());
				setValue(columns[1] + row,"Value",singleValue.get(key + "COUNT").toString());
			}
		}
	}
	
	
  
}
