package com.caetp.digiex.exception;

import com.caetp.digiex.response.ResponseEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * GlobalExceptionHandler
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(value = { UserException.class, RuntimeException.class })
	@ResponseBody
	public Object defaultErrorHandlerJson(HttpServletRequest req, Exception ex) throws Exception {
		ResponseEnum msg;
		// 自定义异常处理
		if(ex instanceof TException) {
			msg = ((TException) ex).getResponse();
		} else if(ex instanceof NullPointerException) {
			// 系统异常处理
			msg = ResponseEnum.NULL_POINTER_EXCEPTION;
		} else if(ex instanceof IllegalArgumentException || ex instanceof MissingServletRequestParameterException) {
			// 参数无效
			msg = ResponseEnum.INVALID_PARAMETER;
		}  else if(ex instanceof DataIntegrityViolationException) {
			Throwable rootCause = ((DataIntegrityViolationException) ex).getRootCause();
			// DB插入重复
			logger.error(ex.getMessage());
			msg = ResponseEnum.INSERT_DUPLICATE;
            /*if (rootCause instanceof MySQLIntegrityConstraintViolationException) {
            	logger.error(rootCause.getMessage());
				msg = ResponseEnum.REF_NOT_FOUND.toString();
			}*/

		} else if(ex instanceof BadSqlGrammarException) {
			// 系统异常处理
			logger.error(ex.getMessage());
			msg = ResponseEnum.INVALID_PARAMETER;
		} else if(ex instanceof MethodArgumentTypeMismatchException) {
			// 参数类型错误
			logger.error(ex.getMessage());
			msg = ResponseEnum.INVALID_PARAMETER;
		} else if(ex instanceof MultipartException) {
			logger.info(ex.getMessage());
			msg = ResponseEnum.FILE_TOO_LARGE;
		}else {
			// 记录其他未知异常 Log
			logger.info(ex.getMessage());
			msg = ResponseEnum.SERVER_UNKNOWN_ERROR;
		}

		// return traces
		msg.setStackTrace(Stream.concat(Stream.of(ex.toString()), Arrays.stream(ex.getStackTrace()))
				.map(Object::toString).limit(13).toArray(String[]::new));

		// print trace for debug
		if (!(ex instanceof TException)) {
			Arrays.asList(msg.getStackTrace()).forEach(System.out::println);
		}

		return msg;
	}

}
