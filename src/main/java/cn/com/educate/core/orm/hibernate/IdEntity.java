package cn.com.educate.core.orm.hibernate;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * 统一定义id的entity基类.
 * 
 * 基类统一定义id的属性名称、数据类型、列名映射及生成策略.
 * 子类可重载getId()函数重定义id的列名映射和生成策略.
 * 
 * @author jiangxd
 */
//JPA 基类的标识
@MappedSuperclass
public abstract class IdEntity implements Serializable {

	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        IdEntity other = (IdEntity) obj;
        if (id == null) {
             return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    /**
	 * 
	 */
	private static final long serialVersionUID = -4564339011665499427L;
	
	public static final String DEF_NUM1 = "NUMBER(1)";
	public static final String DEF_INT4 = "NUMBER(4)";
	public static final String DEF_INT6 = "NUMBER(6)";
	public static final String DEF_INT8 = "NUMBER(8)";
	public static final String DEF_NUM5_2 = "NUMBER(5,2)";
	public static final String DEF_NUM13_2 = "NUMBER(13,2)";
	public static final String DEF_NUM15_5 = "NUMBER(15,5)";
	public static final String DEF_NUM18 = "NUMBER(18)";
	public static final String DEF_NUM3 = "NUMBER(3)";
	public static final String DEF_NUM5 = "NUMBER(5)";
	public static final String DEF_NUM6 = "NUMBER(6)";
	public static final String DEF_NUM10 = "NUMBER(20)";
	
	public static final String DEF_NUM18_2 = "NUMBER(18,2)";
	public static final String DEF_NUM10_4 = "NUMBER(10,4)";
	public static final String DEF_NUM4_1 = "NUMBER(4,1)";
	public static final String DEF_NUM18_4 = "NUMBER(18,4)";
	
	public static final String DEF_INT3 = "NUMBER(3)"; 
	
	public static final String DEF_NUM2 = "NUMBER(2)";
	public static final String DEF_NUM4 = "NUMBER(4)";
	//********* DEF_NUM4
	public static final String DEF_NUM4_2 = "NUMBER(4,2)"; 
	public static final String DEF_NUM18_3 = "NUMBER(18,3)"; 
	public static final String DEF_NUM18_5 = "NUMBER(18,5)";
	//********* 

	//********* 
	public static final String DEF_NUM10_2 = "NUMBER(10,2)"; 
	//********* 


	//********* 
	public static final String DEF_NUM26_5 = "NUMBER(26,5)"; 
	public static final String DEF_NUM36_25 = "NUMBER(36,25)"; 
	//*********
	
	
	public static final String DEF_STR1 = "CHAR(1)";
	public static final String DEF_STR2 = "VARCHAR2(2)";
	public static final String DEF_STR3 = "VARCHAR2(3)";
	public static final String DEF_STR4 = "VARCHAR2(4)";
	public static final String DEF_STR5 = "VARCHAR2(5)";
	public static final String DEF_STR6 = "VARCHAR2(6)";
	public static final String DEF_STR8 = "VARCHAR2(8)";
	public static final String DEF_STR10 = "VARCHAR2(10)";
	public static final String DEF_STR11 = "VARCHAR2(11)";
	public static final String DEF_STR13 = "VARCHAR2(13)";	
	public static final String DEF_STR16 = "VARCHAR2(16)";
	public static final String DEF_STR20 = "VARCHAR2(20)";
	public static final String DEF_STR32 = "VARCHAR2(32)";
	public static final String DEF_STR40 = "VARCHAR2(40)";
	public static final String DEF_STR50 = "VARCHAR2(50)";
	public static final String DEF_STR30 = "VARCHAR2(30)";
	public static final String DEF_STR64 = "VARCHAR2(64)";
	public static final String DEF_STR100 = "VARCHAR2(100)";
	public static final String DEF_STR128 = "VARCHAR2(128)";
	public static final String DEF_STR200 = "VARCHAR2(200)";
	public static final String DEF_STR255 = "VARCHAR2(255)";
	public static final String DEF_STR256 = "VARCHAR2(256)";
	public static final String DEF_STR512 = "VARCHAR2(512)";
	public static final String DEF_STR1000 = "VARCHAR2(1000)";
	public static final String DEF_STR1024 = "VARCHAR2(1024)";
	public static final String DEF_STR2048 = "VARCHAR2(2048)";
	public static final String DEF_STR4000 = "VARCHAR2(4000)";
	public static final String DEF_STATUS = DEF_STR1;
	public static final String DEF_TEL = DEF_STR13;
	public static final String DEF_MOBILE = DEF_STR11;
	public static final String DEF_SMALL_MONEY =DEF_NUM5_2;
	public static final String DEF_MONEY =DEF_NUM13_2;
	public static final String DEF_ID = DEF_NUM18;

	
	protected Long id;

	@Id
	//@GeneratedValue(strategy = GenerationType.SEQUENCE)
//	@GeneratedValue(generator = "system-uuid")
//	@GenericGenerator(name = "system-uuid", strategy = "uuid")
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="HIBERNATE_SEQUENCE")     
//  @SequenceGenerator(name="HIBERNATE_SEQUENCE",allocationSize=1,initialValue=1, sequenceName="HIBERNATE_SEQUENCE")  
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition= DEF_ID )
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
