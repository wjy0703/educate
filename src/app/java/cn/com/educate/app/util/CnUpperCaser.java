package cn.com.educate.app.util;

import java.util.HashMap;

/**
 * * 将10亿以内的阿拉伯数字转成汉字大写形式
 * 
 * 
 * * @author xizhenyin *
 */
public class CnUpperCaser {
	// 整数部分
	private String integerPart;
	// 小数部分
	private String floatPart;
	// 将数字转化为汉字的数组,因为各个实例都要使用所以设为静态
	private static final char[] cnNumbers = { '零', '壹', '贰', '叁', '肆', '伍',
			'陆', '柒', '捌', '玖' };
	// 供分级转化的数组,因为各个实例都要使用所以设为静态
	private static final char[] series = { '元', '拾', '佰', '仟', '万', '拾', '佰',
			'仟', '亿' };
	private static final char[] part = { '分', '角' };

	/**
	 * * 构造函数,通过它将阿拉伯数字形式的字符串传入 * @param original
	 * */
	public CnUpperCaser(String original) {
		// 成员变量初始化
		integerPart = "";
		floatPart = "";
		if (original.contains(".")) {
			// 如果包含小数点
			int dotIndex = original.indexOf(".");
			integerPart = original.substring(0, dotIndex);
			floatPart = original.substring(dotIndex + 1);
			if (floatPart.length() == 1) {
				floatPart = floatPart + "0";
			}
		} else {
			// 不包含小数点
			integerPart = original;
		}
	}

	/**
	 * * 取得大写形式的各位数字 *
	 * 
	 * @return
	 */
	public HashMap getCnMap() {
		HashMap data = new HashMap();
		// 因为是累加所以用StringBuffer
		StringBuffer sb = new StringBuffer();
		// 整数部分处理
		for (int i = 0; i < integerPart.length(); i++) {
			int number = getNumber(integerPart.charAt(i));
			sb.append(cnNumbers[number]);
			char aa = series[integerPart.length() - 1 - i];

			sb.append(series[integerPart.length() - 1 - i]);
			data.put(series[integerPart.length() - 1 - i], cnNumbers[number]);
		}
		// 小数部分处理
		if (floatPart.length() > 0) {
			// sb.append("点");

			for (int i = 0; i < floatPart.length(); i++) {
				int number = getNumber(floatPart.charAt(i));
				sb.append(cnNumbers[number]);
				sb.append(part[floatPart.length() - 1 - i]);
				data.put(part[floatPart.length() - 1 - i], cnNumbers[number]);

			}

		}
		// 返回拼接好的字符串
		return data;
	}
	/**
	 * * 取得大写形式的各位数字 *
	 * 
	 * @return
	 */
	public HashMap getintMap() {
		HashMap data = new HashMap();
		// 整数部分处理
		int number;
		for (int i = 0; i <integerPart.length() ; i++) {
			number = getNumber(integerPart.charAt(i));			
			data.put(integerPart.length()-i, number);
		}
		return data;
	}
	public HashMap getfloatMap() {	
		// 小数部分处理
		HashMap data = new HashMap();
		if (floatPart.length() > 0) {
			// sb.append("点");
			int number;
			for (int i = 0; i < floatPart.length(); i++) {
				number = getNumber(floatPart.charAt(i));
				
				data.put(i+1, number);

			}
			
		}
		// 返回
		return data;
	}
	/**
	 * * 取得大写形式的字符串 *
	 * 
	 * @return
	 */
	public String getCnString() {

		// 因为是累加所以用StringBuffer
		StringBuffer sb = new StringBuffer();
		// 整数部分处理
		for (int i = 0; i < integerPart.length(); i++) {
			int number = getNumber(integerPart.charAt(i));
			sb.append(cnNumbers[number]);
			char aa = series[integerPart.length() - 1 - i];

			sb.append(series[integerPart.length() - 1 - i]);

		}
		// 小数部分处理
		if (floatPart.length() > 0) {
			// sb.append("点");
			if (!floatPart.equals("00") && !floatPart.equals("0")) {
				for (int i = 0; i < floatPart.length(); i++) {
					int number = getNumber(floatPart.charAt(i));
					sb.append(cnNumbers[number]);
					sb.append(part[floatPart.length() - 1 - i]);
				}
			}
		}
		// 返回拼接好的字符串
		return sb.toString();
	}

	/**
	 * * 将字符形式的数字转化为整形数字 * 因为所有实例都要用到所以用静态修饰 * @param c * @return
	 */
	private static int getNumber(char c) {
		String str = String.valueOf(c);
		return Integer.parseInt(str);
	}

	/**
	 * 
	 */
	public CnUpperCaser() {
		super();
		// TODO Auto-generated constructor stub
	}

	/** * @param args */
	public static void main(String[] args) {
//		HashMap data = new CnUpperCaser("9876543.1").getCnMap();
//		System.out.println(data.get('十'));
//		System.out.println(data.get('万'));
//		System.out.println(data.get('仟'));
//		System.out.println(data.get('百'));
//		System.out.println(data.get('拾'));
//		System.out.println(data.get('元'));
//		System.out.println(data.get('角'));
//		System.out.println(data.get('分'));
		
		HashMap data = new CnUpperCaser("9876543.1").getintMap();
		HashMap data1 = new CnUpperCaser("9876543.1").getfloatMap();
		System.out.println(new CnUpperCaser("1193000.2").getCnString());
		// System.out.println(new CnUpperCaser(".123456789").getCnString());
		// System.out.println(new CnUpperCaser("0.1234").getCnString());
		// System.out.println(new CnUpperCaser("1").getCnString());
		// System.out.println(new CnUpperCaser("12").getCnString());
		// System.out.println(new CnUpperCaser("123").getCnString());
		// System.out.println(new CnUpperCaser("1234").getCnString());
		// System.out.println(new CnUpperCaser("12345").getCnString());
		// System.out.println(new CnUpperCaser("123456").getCnString());
		// System.out.println(new CnUpperCaser("1234567").getCnString());
		// System.out.println(new CnUpperCaser("12345678").getCnString());
		// System.out.println(new CnUpperCaser("123456789").getCnString());
	}
}
