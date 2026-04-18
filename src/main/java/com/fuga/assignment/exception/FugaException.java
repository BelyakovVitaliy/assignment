package com.fuga.assignment.exception;

import lombok.Getter;

@Getter
public class FugaException extends RuntimeException {

	private final FugaExceptionCode code;

	public FugaException(FugaExceptionCode code, String message) {
		super(message);
		this.code = code;
	}

	public FugaException(FugaExceptionCode code) {
		super(code.reason);
		this.code = code;
	}

	@Getter
	public enum FugaExceptionCode {
		D001("The artist was not found"),
		D002("The album was not found"),
		D003("Only PUBLISHED album can be deleted"),
		D004("Wrong destination platform");

		private final String reason;

		FugaExceptionCode(String reason) {
			this.reason = reason;
		}
	}
}
