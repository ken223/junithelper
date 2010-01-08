/* 
 * Copyright 2009 junithelper.org. 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language 
 * governing permissions and limitations under the License. 
 */
package org.junithelper.plugin.util;

import java.lang.reflect.Modifier;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.junithelper.plugin.constant.Preference;
import org.junithelper.plugin.constant.STR;

/**
 * JMock2Util<br>
 * <br>
 * 
 * @author Kazuhiro Sera
 * @version 1.0
 */
public final class MockGenUtil {

	private MockGenUtil() {
	}

	/**
	 * Judge mockable class or not
	 * 
	 * @param className
	 * @param importList
	 * @return result
	 */
	public static final boolean isMockableClassName(String className,
			List<String> importList) {

		if (PrimitiveTypeUtil.isPrimitive(className)) {
			return false;
		} else if (className.matches(".+?\\[\\]$")) {
			return false;
		} else {
			try {
				// java.lang class name
				Class<?> clazz = Class.forName("java.lang." + className);
				return (Modifier.isFinal(clazz.getModifiers())) ? false : true;

			} catch (Exception ignore) {
				// imported class name
				for (String importedPackage : importList) {
					importedPackage = importedPackage.replaceAll("//",
							STR.EMPTY);
					if (importedPackage.matches(".+?\\." + className + "$")) {
						return true;
					}
				}
				// full package class name
				if (className.matches(".+?\\..+")) {
					try {
						Class<?> clazz = Class.forName(className);
						return (Modifier.isFinal(clazz.getModifiers())) ? false
								: true;
					} catch (Exception e) {
						return false;
					}
				}
			}
		}
		return false;
	}

	public static final boolean isUsingNone(IPreferenceStore store) {
		String setting = store.getString(Preference.TestMethodGen.USING_MOCK);
		return setting == null
				|| Preference.TestMethodGen.USING_MOCK_NONE.equals(setting);
	}

	public static final boolean isUsingEasyMock(IPreferenceStore store) {
		String setting = store.getString(Preference.TestMethodGen.USING_MOCK);
		return Preference.TestMethodGen.USING_MOCK_EASYMOCK.equals(setting);
	}

	public static final boolean isUsingJMock2(IPreferenceStore store) {
		String setting = store.getString(Preference.TestMethodGen.USING_MOCK);
		return Preference.TestMethodGen.USING_MOCK_JMOCK2.equals(setting);
	}

}