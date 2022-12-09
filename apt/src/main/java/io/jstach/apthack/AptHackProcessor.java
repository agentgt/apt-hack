package io.jstach.apthack;

import java.io.PrintStream;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;

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

		for (var m : methods) {
			out.println(m);
		}

		out.format("""
				
				-----------------------

				""");
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
