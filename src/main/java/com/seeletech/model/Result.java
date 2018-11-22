package com.seeletech.model;

import com.seeletech.model.common.BaseDTO;
import com.seeletech.util.exception.BaseException;
import org.apache.log4j.Logger;


/**
 * 结果类：service、action、server 返回值
 */
public class Result<T extends Object> extends BaseDTO {
	protected static Logger logger = Logger.getLogger(Result.class);
	private boolean success;// 操作 是否成功
	private String message;// 返回消息
	private String code;// 状态码
	private T data;// 返回附加信息 可以是String、List、JSON、Map 类型

	public interface Command<D> {
		D execute() throws BaseException;
	}

	public Result() {
		super();
	}

	public Result(boolean success) {
		this.success = success;
		if (success) {
			this.setMessage("操作成功");// 操作成功
		} else {
			this.setMessage("error");// 操作失败
		}
	}

	public Result(boolean success, String message) {
		super();
		this.success = success;
		this.message = message;
	}
	

	public static <D> Result<D> build(Command<D> cmd) {
		D data = null;
		Result<D> result = new Result<D>();
		try {
			data = cmd.execute();
			result.setSuccess(true);
			result.setMessage("操作成功");
			result.setData(data);
		} catch (BaseException e) {
			logger.error(e.getMessage());
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			e.printStackTrace();
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			result.setSuccess(false);
			result.setMessage("未知异常");
			e.printStackTrace();
		}

		return result;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public void setCode(Integer code) {
		this.code = String.valueOf(code) ;
	}
}
