package com.seeletech.model.common;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 基础的DTO
 * @author
 *
 */
public class BaseDTO {
	/**
	 * 当前操作用户ID
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
