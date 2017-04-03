package com.test.model.test.po;

import org.hibernate.validator.constraints.NotBlank;

import com.test.dept.dept.po.Dept;

/**
 * Employee
 * @author Administrator
 * @date 2017-03-28
 */
public class Employee implements java.io.Serializable {

	/** id*/
	
	private String id ;
	
	
	private Dept dept ;
	/** name*/
	
	private String name ;
	/** dept_id*/
	@NotBlank(message="不能为空") 
	private Integer dept_id ;
	
	/**虚拟主键*/
	private String mxVirtualId ;

    /** 无参构造方法 */
    public Employee() {
    }
	
	/** 构造方法 */
    public Employee(Integer dept_id) {
        this.dept_id = dept_id;
    }
 	   
	
	
    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
	
	
    public Dept getDept() {
        return this.dept;
    }
    
    public void setDept(Dept dept) {
        this.dept = dept;
    }
	
	
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
	
	
    public Integer getDept_id() {
        return this.dept_id;
    }
    
    public void setDept_id(Integer dept_id) {
        this.dept_id = dept_id;
    }
	
	
    public String getMxVirtualId() {
        return this.mxVirtualId;
    }
    
    public void setMxVirtualId(String mxVirtualId) {
        this.mxVirtualId = mxVirtualId;
    }
	

     public String toString() {
         StringBuffer buffer = new StringBuffer();

		 buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
		 buffer.append("id").append("='").append(getId()).append("' ");			
		 buffer.append("dept").append("='").append(getDept()).append("' ");			
		 buffer.append("name").append("='").append(getName()).append("' ");			
		 buffer.append("dept_id").append("='").append(getDept_id()).append("' ");			
		 buffer.append("mxVirtualId").append("='").append(getMxVirtualId()).append("' ");			
		 buffer.append("]");
      
         return buffer.toString();
     }

	public boolean equals(Object other) {
        if ( (this == other ) ) return true;
		if ( (other == null ) ) return false;
		if ( !(other instanceof Employee) ) return false;
		Employee castOther = ( Employee ) other; 
         
		return ( (this.getId()==castOther.getId()) || ( this.getId()!=null && castOther.getId()!=null && this.getId().equals(castOther.getId()) ) )
 && ( (this.getDept()==castOther.getDept()) || ( this.getDept()!=null && castOther.getDept()!=null && this.getDept().equals(castOther.getDept()) ) )
 && ( (this.getName()==castOther.getName()) || ( this.getName()!=null && castOther.getName()!=null && this.getName().equals(castOther.getName()) ) )
 && ( (this.getDept_id()==castOther.getDept_id()) || ( this.getDept_id()!=null && castOther.getDept_id()!=null && this.getDept_id().equals(castOther.getDept_id()) ) )
 && ( (this.getMxVirtualId()==castOther.getMxVirtualId()) || ( this.getMxVirtualId()!=null && castOther.getMxVirtualId()!=null && this.getMxVirtualId().equals(castOther.getMxVirtualId()) ) );
   }
   
   public int hashCode() {
       int result = 17;
         
		result = 37 * result + ( getId() == null ? 0 : this.getId().hashCode() );
		result = 37 * result + ( getDept() == null ? 0 : this.getDept().hashCode() );
		result = 37 * result + ( getName() == null ? 0 : this.getName().hashCode() );
		result = 37 * result + ( getDept_id() == null ? 0 : this.getDept_id().hashCode() );
		result = 37 * result + ( getMxVirtualId() == null ? 0 : this.getMxVirtualId().hashCode() );
		return result;
   }   

}
