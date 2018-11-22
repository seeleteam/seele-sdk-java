package com.seeletech.util.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * service层统一异常处理类
 * @author weiythi
 *
 */
public class ServiceException extends BaseException {

	private static final long serialVersionUID = -4648879327187605014L;

	private static Map<Integer, String> CODES_MAP = null;

	private static final String DEFAULT_ERROR_CODE_MESSAGE = "系统内部错误";

	private static final String EXCEPTION_MESSAGE_PROPERTIES = "business-exceptions.properties";

	private final static Logger logger = LoggerFactory.getLogger(ServiceException.class);

	/*static {
		ServiceException.init();
	}*/

	/**
	 * 从properties配置文件中初始化CodeMap
	 */
	/*private synchronized static void init() {
		if (null == ServiceException.CODES_MAP) {
			// 初始化CODE_MAP
			ServiceException.CODES_MAP = new HashMap<Integer, String>();
			ServiceException.CODES_MAP.put(0, ServiceException.DEFAULT_ERROR_CODE_MESSAGE);

			PropertiesConfiguration config = null;
			try {
				config = new PropertiesConfiguration();
				config.setEncoding("utf8");
				config.load(ServiceException.EXCEPTION_MESSAGE_PROPERTIES);
			} catch (ConfigurationException e) {
				ServiceException.logger.error("[business exception] get pro file error:", e);
			}
			ServiceException.logger.info("[business exception] init start...");

			if (config != null) {
				Iterator<?> keyIterator = config.getKeys();
				while (keyIterator.hasNext()) {
					String key = (String) keyIterator.next();
					if (NumberUtils.isNumber(key)) {
						ServiceException.CODES_MAP.put(NumberUtils.createInteger(key), config.getString(key));
						ServiceException.logger.info(
								"[business exception] add exception:code=" + key + ",message=" + config.getString(key));
					} else {
						ServiceException.logger.info(
								"[business exception] add exception:code=" + key + ",message=" + config.getString(key));
					}
				}
			}
			ServiceException.logger.info("[business exception] init exception map suc.");
		}
	}*/

	/**
	 * 附加描述信息
	 */
	private String additionMessage = null;

	/**
	 * 本异常错误代码
	 */
	private Integer errorCode = 0;

	public ServiceException(int errCode, String additionMessage) {
		super(additionMessage);
		this.additionMessage = additionMessage;
		this.errorCode = errCode;
	}

	public ServiceException(Integer erroCode) {
		super();
		errorCode = erroCode;
	}

	public ServiceException(Integer erroCode, String additionMessage, Throwable cause) {
		super(cause);
		errorCode = erroCode;
		this.additionMessage = additionMessage;
	}

	public ServiceException(Integer erroCode, Throwable cause) {
		super(cause);
		errorCode = erroCode;
	}

	/**
	 * 获取异常错误代码
	 *
	 * @return
	 */
	public Integer getErrorCode() {
		return errorCode;
	}

	@Override
	public String getMessage() {
		return getLocalizedMessage();
	}

	@Override
	public String getLocalizedMessage() {
		String localMessage = null;
		if (null != this.additionMessage) {
			localMessage = this.additionMessage;
		} else {
			localMessage = ServiceException.CODES_MAP.get(this.errorCode) + "。";
		}
		if (null == this.additionMessage && StringUtils.isEmpty(ServiceException.CODES_MAP.get(this.errorCode))) {
			localMessage = "系统内部错误";
		}
		return localMessage;
	}

	public ServiceException() {
		super();
	}

	public ServiceException(String message, Throwable cause) {
		super(cause);
		this.additionMessage = message;
	}

	public ServiceException(String message) {
		super();
		this.additionMessage = message;
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

}
