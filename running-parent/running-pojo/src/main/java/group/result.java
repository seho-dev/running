package group;

import java.io.Serializable;

/**
 * 返回前端结果集
 * @author LAOSHEN
 *
 */

public class result implements Serializable {
	private int status;
	private String message;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
