package io.jstach.apthack;

public class TypeBuilder {

	private StringBuilder buffer = new StringBuilder();

	public TypeBuilder append(String s) {
		buffer.append(s);
		return this;
	}

	@Override
	public String toString() {
		return buffer.toString();
	}

}
