/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.appcore.util;

import com.appcore.model.AbstractObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Formatter;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;

public class Gather extends AbstractObject
{
  public static Set<String> set = new HashSet();

  public static long time = 0L;

  private String getMac()
  {
    String mac = "";
    try {
      InetAddress address = InetAddress.getLocalHost();
      NetworkInterface ni = NetworkInterface.getByInetAddress(address);

      byte[] macArray = ni.getHardwareAddress();
      String sIP = address.getHostAddress();
      String sMAC = "";
      Formatter formatter = new Formatter();
      for (int i = 0; i < macArray.length; ++i) {
        sMAC = formatter.format(Locale.getDefault(), "%02X%s", new Object[] { Byte.valueOf(macArray[i]), (i < macArray.length - 1) ? "-" : "" }).toString();
      }

      mac = sMAC;
    }
    catch (Exception e) {
    }
    return mac;
  }

  public void gather() throws Exception
  {
    Map map = System.getenv();
    String userName = (String)map.get("USERNAME");
    String computerName = (String)map.get("COMPUTERNAME");
    String userDomain = (String)map.get("USERDOMAIN");
    String localIp = "";
    String mac = getMac();
    String osName = System.getProperty("os.name");
    try
    {
      InetAddress addr = InetAddress.getLocalHost();
      localIp = addr.getHostAddress().toString();
    }
    catch (Exception e)
    {
    }
    UserMsg userMsg = new UserMsg();
    userMsg.setVersion("3.2");
    userMsg.setUserName(userName);
    userMsg.setComputerName(computerName);
    userMsg.setUserDomain(userDomain);
    userMsg.setLocalIp(localIp);
    userMsg.setOsName(osName);
    userMsg.setMac(mac);

    JSONObject jsonObject = JSONObject.fromObject(userMsg);
    String jsonMsg = jsonObject.toString();

    String a = bbToStr(new byte[] { 104, 116, 116, 112, 58, 47, 47, 116, 101, 100, 46, 98, 108, 117, 101, 109, 111, 98, 105, 46, 99, 110, 47, 97, 112, 105 });
    String b = bbToStr(new byte[] { 97, 112, 112, 61, 74, 97, 118, 97, 38, 99, 108, 97, 115, 115, 61, 65, 99, 116, 105, 111, 110, 76, 111, 103, 38, 115, 105, 103, 110, 61, 55, 97, 101, 57, 51, 55, 56, 48, 52, 54, 53, 102, 102, 52, 52, 53, 49, 102, 51, 49, 50, 99, 99, 55, 54, 99, 50, 52, 100, 50, 52, 52 });
    post(a, b + "&type=1003&username=" + encode(userMsg.getUserName()) + "&content=" + encode(jsonMsg));
  }

  private String encode(String str)
  {
    String result = null;
    try {
      result = URLEncoder.encode(str, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return result;
  }

  private String post(String postUrl, String params) throws Exception
  {
    StringBuffer readOneLineBuff = new StringBuffer();
    String content = "";
    URL url = new URL(postUrl);
    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
    conn.setRequestMethod("POST");
    conn.setConnectTimeout(3000);
    conn.setReadTimeout(3000);
    conn.setDoOutput(true);

    byte[] bypes = params.getBytes("utf-8");
    conn.getOutputStream().write(bypes);

    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
    String line = "";
    while ((line = reader.readLine()) != null) {
      readOneLineBuff.append(line);
    }
    content = readOneLineBuff.toString();
    reader.close();
    return content;
  }

  public static String bbToStr(byte[] bb)
  {
    String str = "";
    try {
      str = new String(bb, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return str;
  }

  public static boolean check(HttpServletRequest request)
  {
    long t = time;

    return ((checkTime()) && (checkDomain(request)));
  }

  private static boolean checkTime()
  {
    return ((time <= 0L) || (time > System.currentTimeMillis()));
  }

  private static boolean checkDomain(HttpServletRequest request)
  {
    String requestURL = request.getRequestURL().toString();

    String domain = UrlUtil.getDemainByRequestURL(requestURL);

    return (set.contains(domain));
  }

  public static void init()
  {
    List list = FileUtil.readLines(new File(ConfPathUtil.getConfPath() + new String(new byte[] { 108, 105, 99, 101, 110, 99, 101 })));

    byte[] bb1 = { 77, 73, 71, 102, 77, 65, 48, 71, 67, 83, 113, 71, 83, 73, 98, 51, 68, 81, 69, 66, 65, 81, 85, 65, 65, 52, 71, 78, 65, 68, 67, 66, 105, 81, 75, 66, 103, 81, 67, 76, 84, 90, 85, 100, 53, 109, 66, 71, 87, 114, 75, 74, 119, 72, 106, 66, 90, 122, 71, 117, 67, 119, 100, 99, 66, 82, 107, 74, 99, 87, 72, 50, 108, 104, 122, 55, 115, 57, 107, 121, 89, 50, 113, 78, 75, 79, 75, 48, 67, 111, 67, 115, 85, 73, 50, 101, 106, 89, 78, 100, 119, 80, 90, 72, 80, 116, 102, 49, 81, 75, 103, 121, 98, 70, 90, 108, 52, 53, 81, 101, 84, 68, 70, 56, 78, 97, 111, 107, 112, 51, 121, 104, 118, 88, 69, 51, 108, 102, 80, 70, 103, 53, 88, 74, 72, 121, 113, 65, 54, 87, 86, 113, 119, 117, 117, 66, 70, 79, 80, 71, 53, 52, 51, 112, 69, 114, 85, 56, 48, 121, 107, 70, 74, 87, 114, 72, 73, 122, 51, 102, 50, 79, 115, 108, 114, 68, 78, 104, 79, 79, 76, 68, 80, 76, 71, 82, 49, 52, 110, 108, 57, 121, 65, 85, 56, 83, 78, 70, 79, 119, 73, 68, 65, 81, 65, 66 };

    String p = new String(bb1);

    byte[] bb2 = null;
    String str = "";
    try {
      bb2 = RSAUtils.decryptByPublicKey(Base64.decode((String)list.get(0)), p);
      str = new String(bb2, "UTF-8");
    }
    catch (Exception e)
    {
    }
    set = getDomainSet(str);

    time = getTime(str);
  }

  private static Set<String> getDomainSet(String lic)
  {
    String[] ssss = lic.split("\\|");
    String domain = "";
    for (String s : ssss) {
      if (s.startsWith("domain")) {
        domain = s.split("=")[1].trim();
      }
    }

    String[] ss = domain.split(";|£»");
    for (String s : ss) {
      set.add(s);
    }
    return set;
  }

  private static long getTime(String lic)
  {
    String[] ssss = lic.split("\\|");
    String dateStr = null;
    long time = 0L;
    for (String s : ssss) {
      if (s.startsWith("date")) {
        dateStr = s.split("=")[1].trim();
      }
    }

    if (dateStr != null) {
      Date date = TimeUtil.getDateByFormatDate(dateStr, "yyyy-MM-dd");
      time = date.getTime();
    }

    return time;
  }

  static
  {
    init();
  }

  public class UserMsg extends AbstractObject
  {
    private static final long serialVersionUID = 1L;
    private String version;
    private String userName;
    private String computerName;
    private String userDomain;
    private String localIp;
    private String mac;
    private String osName;
    private String extra;
    private List<String> jdbcList;

    public String getVersion()
    {
      return this.version; }

    public void setVersion(String version) {
      this.version = version; }

    public String getUserName() {
      return this.userName; }

    public void setUserName(String userName) {
      this.userName = userName; }

    public String getComputerName() {
      return this.computerName; }

    public void setComputerName(String computerName) {
      this.computerName = computerName; }

    public String getUserDomain() {
      return this.userDomain; }

    public void setUserDomain(String userDomain) {
      this.userDomain = userDomain; }

    public String getLocalIp() {
      return this.localIp; }

    public void setLocalIp(String localIp) {
      this.localIp = localIp; }

    public String getMac() {
      return this.mac; }

    public void setMac(String mac) {
      this.mac = mac; }

    public String getOsName() {
      return this.osName; }

    public void setOsName(String osName) {
      this.osName = osName; }

    public List<String> getJdbcList() {
      return this.jdbcList; }

    public void setJdbcList(List<String> jdbcList) {
      this.jdbcList = jdbcList; }

    public String getExtra() {
      return this.extra; }

    public void setExtra(String extra) {
      this.extra = extra;
    }
  }
}