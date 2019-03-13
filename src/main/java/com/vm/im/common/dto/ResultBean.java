package com.vm.im.common.dto;

import lombok.Data;

@Data
public class ResultBean {

	public Integer status;
	public String result;
	public String msg;

	public ResultBean(Integer status, String result, String msg){
		this.status = status;
		this.result = result;
		this.msg = msg;
	}
}
