package br.com.alura.forum.config.validation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidationErrorHandler {
	
	@Autowired
	private MessageSource messageSource;
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<FormErrorDto> handle(MethodArgumentNotValidException expection){
			List<FormErrorDto> dto = new ArrayList<>();
		
		List<FieldError> fieldErrors = expection.getBindingResult().getFieldErrors();
		
		fieldErrors.forEach(e ->{
			String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
			FormErrorDto erro = new FormErrorDto(e.getField(), mensagem);
			
			dto.add(erro);
		});
			
		return dto;
	}

}
