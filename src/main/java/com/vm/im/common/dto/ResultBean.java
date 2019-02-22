package com.vm.im.common.dto;

import lombok.Data;

@Data
public class ResultBean {

	public String status;
	public String msg;
	public String result;

	public ResultBean(String status, String msg, String result){
		this.status = status;
		this.msg = msg;
		this.result = result;
	}

}
