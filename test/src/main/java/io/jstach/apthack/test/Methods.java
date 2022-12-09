package io.jstach.apthack.test;

import java.util.List;

import org.eclipse.jdt.annotation.Nullable;

import io.jstach.apthack.AptHack;

public class Methods {

	@AptHack
	public void declared(@Nullable String a) {
	}

	@AptHack
	public void declaredTypeParameter(@SuppressWarnings("exports") List<@Nullable String> list) {

	}

}
