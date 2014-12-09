package com.wt.uum2.constants;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.eaio.util.lang.Hex;

/**
 * <pre>
 * 业务名: MACAddressUtils
 * 功能说明:  MACAddressUtils
 * 编写日期:	2011-11-9
 * 作者:	Liuyuxiang
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public final class MACAddressUtils {
	/**
	 * No instances needed.
	 */
	private MACAddressUtils() {
		super();
	}

	/**
	 * The cached MAC address.
	 */
	private static String macAddress = null;

	/**
	 * The cached MAC address.
	 */
	private static String macAddresses = String.valueOf("");

	/**
	 * The cached MAC address.
	 */
	private static List<String> addresses = new LinkedList<String>();

	static {

		try {
			Class.forName("java.net.InterfaceAddress");
			macAddress = Class
					.forName(
							"com.wt.uum2.constants.MACAddressUtils$HardwareAddressLookup")
					.newInstance().toString();
			macAddresses = Class
					.forName(
							"com.wt.uum2.constants.MACAddressUtils$HardwareAddressesLookup")
					.newInstance().toString();
		} catch (ExceptionInInitializerError err) {
			// Ignored.
		} catch (ClassNotFoundException ex) {
			// Ignored.
		} catch (LinkageError err) {
			// Ignored.
		} catch (IllegalAccessException ex) {
			// Ignored.
		} catch (InstantiationException ex) {
			// Ignored.
		} catch (SecurityException ex) {
			// Ignored.
		}

		if (macAddress == null) {

			Process p = null;
			BufferedReader in = null;

			try {
				String osname = System.getProperty("os.name", "");

				if (osname.startsWith("Windows")) {
					p = Runtime.getRuntime().exec(
							new String[] { "ipconfig", "/all" }, null);
				}
				// Solaris code must appear before the generic code
				else if (osname.startsWith("Solaris")
						|| osname.startsWith("SunOS")) {
					String hostName = getFirstLineOfCommand("uname", "-n");
					if (hostName != null) {
						p = Runtime.getRuntime().exec(
								new String[] { "/usr/sbin/arp", hostName },
								null);
					}
				} else if (new File("/usr/sbin/lanscan").exists()) {
					p = Runtime.getRuntime().exec(
							new String[] { "/usr/sbin/lanscan" }, null);
				} else if (new File("/sbin/ifconfig").exists()) {
					p = Runtime.getRuntime().exec(
							new String[] { "/sbin/ifconfig", "-a" }, null);
				}

				if (p != null) {
					in = new BufferedReader(new InputStreamReader(
							p.getInputStream()), 128);
					String l = null;
					while ((l = in.readLine()) != null) {
						String dummy = parse(l);
						if (dummy != null && Hex.parseShort(dummy) != 0xff) {
							if (macAddress == null) {
								macAddress = dummy;
							}
							if (!addresses.contains(dummy)) {
								addresses.add(dummy);
							}
						}
					}
				}
				if (CollectionUtils.isNotEmpty(addresses)) {
					macAddresses = StringUtils.join(addresses, "/");
				}
			} catch (SecurityException ex) {
				// Ignore it.
			} catch (IOException ex) {
				// Ignore it.
			} finally {
				if (p != null) {
					if (in != null) {
						try {
							in.close();
						} catch (IOException ex) {
							// Ignore it.
						}
					}
					try {
						p.getErrorStream().close();
					} catch (IOException ex) {
						// Ignore it.
					}
					try {
						p.getOutputStream().close();
					} catch (IOException ex) {
						// Ignore it.
					}
					p.destroy();
				}
			}

		}

	}

	/**
	 * Returns the MAC address. Not guaranteed to return anything.
	 * 
	 * @return the MAC address, may be <code>null</code>
	 */
	public static String getMACAddress() {
		return macAddress;
	}

	public static String getMACAddresses() {
		return macAddresses;
	}

	/**
	 * Returns the first line of the shell command.
	 * 
	 * @param commands
	 *            the commands to run
	 * @return the first line of the command
	 * @throws IOException
	 */
	static String getFirstLineOfCommand(String... commands) throws IOException {

		Process p = null;
		BufferedReader reader = null;

		String result = String.valueOf("");

		try {
			p = Runtime.getRuntime().exec(commands);
			reader = new BufferedReader(new InputStreamReader(
					p.getInputStream()), 128);

			result = reader.readLine();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException ex) {
					// Ignore it.
				}
			}
			if (p != null) {

				try {
					p.getErrorStream().close();
				} catch (IOException ex) {
					// Ignore it.
				}
				try {
					p.getOutputStream().close();
				} catch (IOException ex) {
					// Ignore it.
				}
				p.destroy();
			}
		}
		return result;

	}

	/**
	 * Scans MAC addresses for good ones.
	 */
	static class HardwareAddressLookup {

		/**
		 * @see java.lang.Object#toString()
		 * @return String
		 */
		@Override
		public String toString() {
			String out = String.valueOf("");
			try {
				Enumeration<NetworkInterface> ifs = NetworkInterface
						.getNetworkInterfaces();
				if (ifs != null) {
					while (ifs.hasMoreElements()) {
						NetworkInterface iface = ifs.nextElement();
						byte[] hardware = iface.getHardwareAddress();
						if (hardware != null && hardware.length == 6
								&& hardware[1] != (byte) 0xff) {
							out = Hex.append(new StringBuilder(36), hardware)
									.toString();
							break;
						}
					}
				}
			} catch (SocketException ex) {
				// Ignore it.
			}
			return dataFormat(out);
		}

	}

	/**
	 * Scans MAC addresses.
	 */
	static class HardwareAddressesLookup {

		/**
		 * @see java.lang.Object#toString()
		 * @return String
		 */
		@Override
		public String toString() {
			List<String> out = new LinkedList<String>();
			try {
				Enumeration<NetworkInterface> ifs = NetworkInterface
						.getNetworkInterfaces();
				if (ifs != null) {
					while (ifs.hasMoreElements()) {
						NetworkInterface iface = ifs.nextElement();
						byte[] hardware = iface.getHardwareAddress();
						if (hardware != null && hardware.length == 6
								&& hardware[1] != (byte) 0xff) {
							String address = Hex.append(new StringBuilder(36),
									hardware).toString();
							if (address.length() == 12) {
								out.add(dataFormat(address));
							}
						}
					}
				}
			} catch (SocketException ex) {
				// Ignore it.
			}
			return StringUtils.join(out, "/");
		}
	}

	/**
	 * 方法说明：
	 * 
	 * @param in
	 *            in
	 * @return String
	 */
	static String dataFormat(String in) {
		String out = in;

		if (in.length() == 12) {
			String[] ins = new String[6];
			for (int i = 0; i < ins.length; i++) {
				ins[i] = in.substring(i * 2, (i * 2) + 2);
			}
			out = StringUtils.join(ins, ":");
		}

		return out.toUpperCase();
	}

	/**
	 * Attempts to find a pattern in the given String.
	 * 
	 * @param in
	 *            the String, may not be <code>null</code>
	 * @return the substring that matches this pattern or <code>null</code>
	 */
	static String parse(String in) {

		String out = in;

		// lanscan

		int hexStart = out.indexOf("0x");
		if (hexStart != -1 && out.indexOf("ETHER") != -1) {
			int hexEnd = out.indexOf(' ', hexStart);
			if (hexEnd > hexStart + 2) {
				out = out.substring(hexStart, hexEnd);
			}
		}

		else {

			int octets = 0;
			int lastIndex, old, end;

			if (out.indexOf('-') > -1) {
				out = out.replace('-', ':');
			}

			lastIndex = out.lastIndexOf(':');

			if (lastIndex > out.length() - 2) {
				out = null;
			} else {

				end = Math.min(out.length(), lastIndex + 3);

				++octets;
				old = lastIndex;
				while (octets != 5 && lastIndex != -1 && lastIndex > 1) {
					lastIndex = out.lastIndexOf(':', --lastIndex);
					if (old - lastIndex == 3 || old - lastIndex == 2) {
						++octets;
						old = lastIndex;
					}
				}

				if (octets == 5 && lastIndex > 1) {
					out = out.substring(lastIndex - 2, end).trim();
				} else {
					out = null;
				}

			}

		}

		if (out != null && out.startsWith("0x")) {
			out = out.substring(2);
		}

		return out;
	}

}
