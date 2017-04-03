package com.test.dept.dept.vo;

import com.test.dept.dept.po.Dept;

public class DeptTransfer {

	public static Dept toPO(DeptVO vo) {
		Dept dept = new Dept();
		if(vo != null){
	         dept.setId(vo.getId());
	         dept.setName(vo.getName());
	         dept.setPeriodOfValidity(vo.getPeriodOfValidity());
	         dept.setColor(vo.getColor());
	    }
		return dept;
	}

	public static DeptVO toVO(Dept po) {
		DeptVO deptlistVO = new DeptVO();

	    deptlistVO.setId(po.getId());
	    deptlistVO.setName(po.getName());
	    deptlistVO.setPeriodOfValidity(po.getPeriodOfValidity());
	    deptlistVO.setColor(po.getColor());
		return deptlistVO;
	}
}
