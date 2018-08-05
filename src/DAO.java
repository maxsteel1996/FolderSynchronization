

import java.io.Serializable;

public class DAO implements Serializable {
	byte[] content;
	String operation;
	String fileName;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public DAO(byte[] content, String operation, String fileName) {
		super();
		this.content = content;
		this.operation = operation;
		this.fileName = fileName;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

}
