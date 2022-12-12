package io.jstach.apthack.test;

import java.util.List;

import org.eclipse.jdt.annotation.Nullable;

import io.jstach.apthack.AptHack;
import io.jstach.apthack.test.Enclosed.A;

public class Methods {

	@AptHack
	public void declared(@Nullable String a) {
	}

	@AptHack
	public void declaredTypeParameter(@SuppressWarnings("exports") List<@Nullable String> list) {

	}

	@AptHack
	public void declaredWildCardExtends(@SuppressWarnings("exports") List<? extends @Nullable CharSequence> list) {

	}

	@AptHack
	public void declaredWildCardSuper(
			@SuppressWarnings("exports") @Nullable List<? super @Nullable CharSequence> list) {
	}

	@AptHack
	public void declaredWildCardNullable(@SuppressWarnings("exports") List<@Nullable ? extends CharSequence> list) {

	}

	@AptHack
	public <T> void typeVariableNullable(List<@Nullable T> stuff) {
	}

	@AptHack
	public void enclosedType(@Nullable A a) {
	}

	@AptHack
	public void array(String @Nullable [] s) {

	}

	@AptHack
	public void arrayComponent(@Nullable String[] s) {

	}

	@AptHack
	public void arrayBoth(@Nullable String @Nullable [] s) {

	}

	@AptHack
	public void arrayNative(@Nullable int @Nullable [] s) {

	}

	@AptHack
	public <T extends java.lang.@Nullable Comparable<T> & CharSequence> void typeIntersection(T t) {

	}

	// @AptHack
	// public void someNative(@Nullable int s) {
	//
	// }

}
