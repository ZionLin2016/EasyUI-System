package lsd.util;

import java.util.ResourceBundle;

/**
 * 项目参数工具类
 * 
 */
public class ConfigUtil {

	// java.util.ResourceBundle.getBundle(String baseName) 方法获取使用指定的基本名称，
	// 默认的语言环境和调用者的类加载器获取资源包。
	private static final ResourceBundle bundle = java.util.ResourceBundle.getBundle("config");

	/**
	 * 获得sessionInfo名字
	 * 
	 * @return
	 */
	public static final String getSessionInfoName() {
		return bundle.getString("sessionInfoName");
		// 从此资源包或它的某个父包中获取给定键的字符串数组。调用此方法等同于调用 (String[]) getObject(key)。
	}

	/**
	 * 通过键获取值
	 * 
	 * @param key
	 * @return
	 */
	public static final String get(String key) {
		return bundle.getString(key);
	}

}
