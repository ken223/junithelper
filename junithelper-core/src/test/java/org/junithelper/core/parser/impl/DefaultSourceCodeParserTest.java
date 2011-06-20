package org.junithelper.core.parser.impl;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import org.junit.Test;
import org.junithelper.core.config.Configuration;
import org.junithelper.core.exception.JUnitHelperCoreException;
import org.junithelper.core.meta.ClassMeta;
import org.junithelper.core.meta.ConstructorMeta;
import org.junithelper.core.meta.MethodMeta;
import org.junithelper.core.meta.extractor.ClassMetaExtractor;
import org.junithelper.core.util.IOUtil;
import org.junithelper.core.util.UniversalDetectorUtil;

public class DefaultSourceCodeParserTest {

	@Test
	public void type() throws Exception {
		assertNotNull(DefaultSourceCodeParser.class);
	}

	@Test
	public void instantiation() throws Exception {
		Configuration config = null;
		DefaultSourceCodeParser target = new DefaultSourceCodeParser(config);
		assertNotNull(target);
	}

	Configuration config = new Configuration();
	DefaultSourceCodeParser target = new DefaultSourceCodeParser(config);

	@Test
	public void parse_A$InputStream$String() throws Exception {
		String encoding = UniversalDetectorUtil.getDetectedEncoding(IOUtil
				.getResourceAsStream("parser/impl/Sample.txt"));
		InputStream is = IOUtil.getResourceAsStream("parser/impl/Sample.txt");
		ClassMeta actual = target.parse(is, encoding);
		assertTrue(actual.name.equals("Sample"));
		assertTrue(actual.constructors.size() == 1);
		assertTrue(actual.methods.size() == 1);
		assertTrue(actual.methods.get(0).name.equals("doSomething"));
		assertTrue(actual.methods.get(0).argTypes.size() == 1);
		assertTrue(actual.methods.get(0).argTypes.get(0).name.equals("String"));
		assertTrue(actual.methods.get(0).argNames.size() == 1);
		assertTrue(actual.methods.get(0).argNames.get(0).equals("arg"));
		assertTrue(actual.methods.get(0).returnType != null);
		assertTrue(actual.methods.get(0).returnType.name.equals("String"));
		assertTrue(actual.methods.get(0).throwsExceptions.size() == 1);
		assertTrue(actual.methods.get(0).throwsExceptions.get(0).name.equals("Exception"));
	}

	@Test
	public void parse_A$String() throws Exception {
		String sourceCodeString = "package hoge.foo; public class Sample { public Sample() {}\r\n public int doSomething(String str, long longValue) throws Throwable { System.out.println(\"aaaa\") } }";
		ClassMeta actual = target.parse(sourceCodeString);
		assertTrue(actual.name.equals("Sample"));
		assertTrue(actual.constructors.size() == 1);
		assertTrue(actual.methods.size() == 1);
		assertTrue(actual.methods.get(0).name.equals("doSomething"));
		assertTrue(actual.methods.get(0).argTypes.size() == 2);
		assertTrue(actual.methods.get(0).argTypes.get(0).name.equals("String"));
		assertTrue(actual.methods.get(0).argTypes.get(1).name.equals("long"));
		assertTrue(actual.methods.get(0).argNames.size() == 2);
		assertTrue(actual.methods.get(0).argNames.get(0).equals("str"));
		assertTrue(actual.methods.get(0).argNames.get(1).equals("longValue"));
		assertTrue(actual.methods.get(0).returnType != null);
		assertTrue(actual.methods.get(0).returnType.name.equals("int"));
		assertTrue(actual.methods.get(0).throwsExceptions.size() == 1);
		assertTrue(actual.methods.get(0).throwsExceptions.get(0).name.equals("Throwable"));
	}

	@Test
	public void getMethods_A$ClassMeta$String() throws Exception {
		String sourceCodeString = "package hoge.foo; public class Sample { public Sample() {}\r\n public int doSomething(String str, long longValue) throws Throwable { System.out.println(\"aaaa\") } }";
		ClassMeta classMeta = new ClassMetaExtractor(config).extract(sourceCodeString);
		List<MethodMeta> actual = target.getMethods(classMeta, sourceCodeString);
		assertEquals(1, actual.size());
	}

	@Test
	public void getConstructors_A$ClassMeta$String() throws Exception {
		String sourceCodeString = "package hoge.foo; public class Sample { public Sample() {}\r\n public int doSomething(String str, long longValue) throws Throwable { System.out.println(\"aaaa\") } }";
		ClassMeta classMeta = new ClassMetaExtractor(config).extract(sourceCodeString);
		List<ConstructorMeta> actual = target.getConstructors(classMeta, sourceCodeString);
		assertEquals(1, actual.size());
	}

	@Test
	public void parse_A$InputStream$String_StringIsNull() throws Exception {
		DefaultSourceCodeParser target = new DefaultSourceCodeParser(config);
		InputStream is = new ByteArrayInputStream(new byte[] {});
		String encoding = null;
		ClassMeta actual = target.parse(is, encoding);
		assertThat(actual, notNullValue());
	}

	@Test
	public void parse_A$InputStream$String_StringIsEmpty() throws Exception {
		DefaultSourceCodeParser target = new DefaultSourceCodeParser(config);
		InputStream is = new ByteArrayInputStream(new byte[] {});
		String encoding = "";
		try {
			target.parse(is, encoding);
			fail();
		} catch (UnsupportedEncodingException e) {
		}
	}

	@Test
	public void getMethods_A$ClassMeta$String_StringIsNull() throws Exception {
		DefaultSourceCodeParser target = new DefaultSourceCodeParser(config);
		ClassMeta classMeta = null;
		String sourceCodeString = null;
		try {
			target.getMethods(classMeta, sourceCodeString);
			fail();
		} catch (JUnitHelperCoreException e) {
		}
	}

	@Test
	public void getMethods_A$ClassMeta$String_StringIsEmpty() throws Exception {
		DefaultSourceCodeParser target = new DefaultSourceCodeParser(config);
		ClassMeta classMeta = null;
		String sourceCodeString = "";
		List<MethodMeta> actual = target.getMethods(classMeta, sourceCodeString);
		assertThat(actual, notNullValue());
	}

	@Test
	public void getConstructors_A$ClassMeta$String_StringIsNull() throws Exception {
		DefaultSourceCodeParser target = new DefaultSourceCodeParser(config);
		ClassMeta classMeta = null;
		String sourceCodeString = null;
		try {
			target.getConstructors(classMeta, sourceCodeString);
			fail();
		} catch (JUnitHelperCoreException e) {
		}
	}

	@Test
	public void getConstructors_A$ClassMeta$String_StringIsEmpty() throws Exception {
		DefaultSourceCodeParser target = new DefaultSourceCodeParser(config);
		ClassMeta classMeta = null;
		String sourceCodeString = "";
		List<ConstructorMeta> actual = target.getConstructors(classMeta, sourceCodeString);
		assertThat(actual, notNullValue());
	}

	@Test
	public void parse_A$String_StringIsNull() throws Exception {
		DefaultSourceCodeParser target = new DefaultSourceCodeParser(config);
		String sourceCodeString = null;
		try {
			target.parse(sourceCodeString);
			fail();
		} catch (JUnitHelperCoreException e) {
		}
	}

	@Test
	public void parse_A$String_StringIsEmpty() throws Exception {
		DefaultSourceCodeParser target = new DefaultSourceCodeParser(config);
		String sourceCodeString = "";
		ClassMeta actual = target.parse(sourceCodeString);
		assertThat(actual, notNullValue());
	}

}
