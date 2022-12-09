import javax.annotation.processing.Processor;

module io.jstach.apthack.test {
	requires io.jstach.apthack;
	requires static org.eclipse.jdt.annotation;
	exports io.jstach.apthack.test;
	
	uses Processor;
}