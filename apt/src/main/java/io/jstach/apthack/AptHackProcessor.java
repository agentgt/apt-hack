package io.jstach.apthack;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementFilter;
import javax.tools.JavaFileObject;

import org.kohsuke.MetaInfServices;

@MetaInfServices(value = Processor.class)
public class AptHackProcessor extends AbstractProcessor {

	PrintStream out = System.out;

	AtomicInteger round = new AtomicInteger(0);

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

		out.format("""

				-----------------------

				HELLO

				Welcome to APT HACK!

				round: %s

				""", round.incrementAndGet());
		Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(AptHack.class);
		var methods = ElementFilter.methodsIn(elements);

		List<String> typeDeclarations = new ArrayList<>();

		for (var m : methods) {
			out.println("Method: " + m);
			out.println("Method: " + m.asType());
			for (VariableElement param : m.getParameters()) {
				var parameterType = param.asType();
				out.println("Parameter: " + param + " type: " + parameterType);
				String typeString = TypeToStringMirrorVisitor.toCodeSafeString(parameterType);
				out.println("Code Safe Type: " + typeString);
				if (typeString.equals(parameterType.toString())) {
					out.println("[OK] TypeMirror toString matches");
				}
				else {
					out.println("[FAIL] TypeMirror toString does not match.");
				}
				typeDeclarations.add(typeString);
			}
			out.println();
			out.println();
		}

		out.format("""

				-----------------------

				""");
		if (!typeDeclarations.isEmpty()) {
			try {
				JavaFileObject file = processingEnv.getFiler().createSourceFile("HackList");
				int i = 1;
				try (var w = file.openWriter()) {
					w.append("public class HackList<T> {\n");
					for (var td : typeDeclarations) {
						w.append("\t").append(td).append(" field").append("" + i++).append(";\n");
					}
					w.append("}");
				}
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;
	}

	@Override
	public Set<String> getSupportedAnnotationTypes() {
		return Set.of(AptHack.class.getName());
	}

	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.latest();
	}

}
