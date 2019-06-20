package com.caetp.digiex.response;

import com.caetp.digiex.dto.TPage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageResponse extends CustomResponse {

	public PageResponse() {
		super();
	}

	public PageResponse(Object data) {
		super(data);
	}

	public static Response success(Object data) {
	    return new PageResponse(data);
	}

	public static Response success(TPage<?> page) {
	    return new PageResponse(page);
	}

}
