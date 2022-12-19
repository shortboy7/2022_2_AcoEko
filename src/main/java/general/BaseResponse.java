package general;

import java.util.Iterator;

import org.json.simple.JSONObject;

public class BaseResponse<T>{
	public boolean getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getResult() {
		return result;
	}
	public void setResult(T result) {
		this.result = result;
	}
	private boolean isSuccess;
	private String message;
	private T result;
	public BaseResponse() {
		
	}
	public BaseResponse(boolean isSuccess, String message, T result) {
		super();
		this.isSuccess = isSuccess;
		this.message = message;
		this.result = result;
	}
}
