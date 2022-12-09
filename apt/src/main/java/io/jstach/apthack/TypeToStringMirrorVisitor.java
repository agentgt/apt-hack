package io.jstach.apthack;

import java.io.PrintStream;

import javax.lang.model.element.Element;
import javax.lang.model.element.QualifiedNameable;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ErrorType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.IntersectionType;
import javax.lang.model.type.NoType;
import javax.lang.model.type.NullType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.type.UnionType;
import javax.lang.model.type.WildcardType;
import javax.lang.model.util.AbstractTypeVisitor14;
import javax.lang.model.util.Elements;

public class TypeToStringMirrorVisitor extends AbstractTypeVisitor14<TypeBuilder, TypeBuilder> {

	PrintStream out = System.out;

	private final int depth;

	public TypeToStringMirrorVisitor() {
		this(1);
	}

	public TypeToStringMirrorVisitor(int depth) {
		super();
		this.depth = depth;
	}

	void debug(String message, Object o) {
		out.println(indent() + "#" + message + ". " + o);
	}

	String indent() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < depth; i++) {
			sb.append("\t");
		}
		return sb.toString();
	}

	TypeToStringMirrorVisitor child() {
		return new TypeToStringMirrorVisitor(depth + 1);
	}

	@Override
	public TypeBuilder visitPrimitive(PrimitiveType t, TypeBuilder p) {
		debug("primitive", t);
		return p;
	}

	@Override
	public TypeBuilder visitNull(NullType t, TypeBuilder p) {
		debug("primitive", t);
		return p;
	}

	@Override
	public TypeBuilder visitArray(ArrayType t, TypeBuilder p) {
		debug("array", t);
		return p;
	}

	@Override
	public TypeBuilder visitDeclared(DeclaredType t, TypeBuilder p) {
		debug("declared", t);
		debug("enclosing type", t.getEnclosingType());
		debug("typeUseFQN", typeUseFullyQualfiedName(t));
		p.buffer.append(typeUseFullyQualfiedName(t));
		var tas = t.getTypeArguments();
		if (!tas.isEmpty()) {
			p.buffer.append("<");
			for (var ta : t.getTypeArguments()) {
				ta.accept(child(), p);
			}
			p.buffer.append(">");
		}
		return p;
	}

	static String typeUseFullyQualfiedName(DeclaredType t) {
		TypeElement element = (TypeElement) t.asElement();
		var typeUseAnnotations = t.getAnnotationMirrors();
		if (typeUseAnnotations.isEmpty()) {
			return element.getQualifiedName().toString();
		}
		String enclosedPart;
		Element enclosed = element.getEnclosingElement();
		if (enclosed instanceof QualifiedNameable qn) {
			enclosedPart = qn.getQualifiedName().toString() + ".";
		}
		else {
			enclosedPart = "";
		}
		StringBuilder sb = new StringBuilder();
		sb.append(enclosedPart);
		for (var ta : typeUseAnnotations) {
			sb.append(ta.toString()).append(" ");
		}
		sb.append(element.getSimpleName());
		return sb.toString();
	}

	@Override
	public TypeBuilder visitError(ErrorType t, TypeBuilder p) {
		debug("error", t);
		return p;
	}

	@Override
	public TypeBuilder visitTypeVariable(TypeVariable t, TypeBuilder p) {
		debug("typeVariable", t);
		return p;
	}

	@Override
	public TypeBuilder visitWildcard(WildcardType t, TypeBuilder p) {
		debug("wildcard", t);
		var extendsBound = t.getExtendsBound();
		var superBound = t.getSuperBound();
		for (var ta : t.getAnnotationMirrors()) {
			p.buffer.append(ta.toString()).append(" ");
		}
		if (extendsBound != null) {
			p.buffer.append("? extends ");
			extendsBound.accept(child(), p);
		}
		else if (superBound != null) {
			p.buffer.append("? super ");
			superBound.accept(child(), p);
		}
		return p;
	}

	@Override
	public TypeBuilder visitExecutable(ExecutableType t, TypeBuilder p) {
		debug("executable", t);
		return p;
	}

	@Override
	public TypeBuilder visitNoType(NoType t, TypeBuilder p) {
		debug("noType", t);
		return p;
	}

	@Override
	public TypeBuilder visitIntersection(IntersectionType t, TypeBuilder p) {
		debug("intersection", t);
		return p;
	}

	@Override
	public TypeBuilder visitUnion(UnionType t, TypeBuilder p) {
		debug("union", t);
		return p;

	}

}
