package org.qatide.util;

import java.net.InetAddress;
import java.net.NetworkInterface;

/**
 * @author Omicron
 */
public final class OperatingSystem {
	public static final int MAC = 0;
	public static final int WINDOWS = 1;
	public static final int LINUX = 2;
	public static final int UNKNOWN = 3;

	public static int getCurrentOperatingSystem() {
		String os = System.getProperty("os.name");
		if (os.contains("Mac")) {
			return OperatingSystem.MAC;
		} else if (os.contains("Windows")) {
			return OperatingSystem.WINDOWS;
		} else if (os.contains("Linux")) {
			return OperatingSystem.LINUX;
		} else {
			return OperatingSystem.UNKNOWN;
		}
	}

	public static boolean isUserOSWin7() {
		return System.getProperty("os.name").contains("Windows 7");
	}

	public static String getMacAddress() {
		try {
			InetAddress address = InetAddress.getLocalHost();
			NetworkInterface ni = NetworkInterface.getByInetAddress(address);
			if (ni == null) {
				return "NOT_SPECIFIED";
			}
			byte[] mac = ni.getHardwareAddress();
			if (mac == null) {
				return "NOT_ACCESSABLE";
			}
			StringBuilder sb = new StringBuilder();
			int length = mac.length;
			for (int i = 0; i < length; i++) {
				sb.append(String.format("%02X%s", mac[i], i < mac.length - 1 ? "-" : ""));
			}
			if (sb.length() == 0) {
				return "ZERO_LENGTH";
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "ERROR";
		}
	}
}
