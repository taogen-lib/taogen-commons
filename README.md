# taogen-commons
Java common utility library.

Dependency this library in Maven Project

- Add the [JitPack](https://jitpack.io/#taogen-lib/taogen-commons) repository to your build file

  ```xml
  <repositories>
      <repository>
          <id>jitpack.io</id>
          <url>https://jitpack.io</url>
      </repository>
  </repositories>
  ```

- Add the dependency

  ```xml
  <dependency>
      <groupId>com.github.taogen-lib</groupId>
      <artifactId>taogen-commons</artifactId>
      <version>Tag</version>
  </dependency>
  ```

  or 

  ```xml
  <dependency>
      <groupId>com.github.taogen-lib</groupId>
      <artifactId>taogen-commons</artifactId>
      <version>{short_commit_hash_on_jitpack}</version>
  </dependency>
  ```
  

<h3 id="content">Content</h3>

- Java util class basic
  - Java lib
    - [x] [StringUtils](#stru)
    - [x] [EncodingUtils](#encu)
    - [NumberUtils](#numu)
    - [x] [RandomUtils](#randu)
    - [x] [DateUtils](#dateu)
    - [TimeUtils](#timeu)
    - [EncryptUtils](#encpu)
    - [Md5Utils](#md5u)
    - File I/O
      - [FileUtils](#fileu)
      - [PropertyUtils](#propu)
      - [ImageUtils](#imgu)
      - [ZipUtils](#zipu)
    - [LocationUtils](#locu) (LngAndLatUtils)
  - Javax lib
    - [IpUtils](#ipu)
    - [RequestParamUtils](#reqparamu)
    - [EmailUtils](#emailu)
  - Third lib
    - [JsonUtils](#jsonu)
    - [XmlUtils](#xmlu)
    - [PinyinUtils](#pyu)
    - [HttpUtils](#httpu)
    - [QrCodeUtils](#qru)
    - [SpringUtils](#springu)
  - Others
    - [x] [I18nUtils](#i18nu)
    - [LogUtils](#logu)



### Main

### A Utility Accomplishment Process

Defining methods of utility --> writing fake implement class --> writing test class --> writing real implement class and pass its test.

### Test Case Conventions

- Illegal parameters
- bound value + middle value
- Cover all DD-paths

### Assertions of Junit 5 

```java
assertAll(...)
assertEquals(...)
assertNotEquals(...)
assertArrayEquals(...)
assertTrue(...)
assertFalse(...)
assertNull(...)
assertNotNull(...)
assertSame(...) 
assertNotSame(...)
assertIterableEquals(...)
assertLinesMatch(...)
assertThrows(...)
assertTimeout(...)
assertTimeoutPreemptively(...)
fail(...)
```

<h3 id="stru">StringUtils</h3>

Dependencies: 

```
import java.lang.reflect.InvocationTargetException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
```

Methods

Convert

```java
int str2Int (String str, int default)
long str2Long(String str, long default)
double str2Double(String str, double default)
boolean Str2Boolean(String str, boolean default)
```

Verify

```java
boolean isEmpty(String str)
boolean isEmail(String email)   
boolean isMobile(String mobile)
boolean isMatch(String str, String regex)
```

Handle

```java
String encodeMobile(String mobile)  
String encodeEmail(String email)
String encodeBankCard(String bankcard)
String jointString (String... strs)
String toNotNull (String str)
String expandLength(String str, int len, char fillChar)
```

[`back to content`](#content)

------

<h3 id="encu">EncodingUtils</h3>

Dependencies

```
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
```

Methods

Verify

```java
boolean isChinese(String str)
boolean isEmoji(String str)
```

Convert

```java
String iso2Utf8(String str)
String emojiConvert(String str)  
String emojiRecovery(String str)
```

[`back to content`](#content)

------

<h3 id="numu">NumberUtils</h3>

Dependencies

Methods

[`back to content`](#content)

------

<h3 id="randu">RandomUtils</h3>

Dependencies

```
import java.util.Random;
```

Methods

```java
int getRandomNumber(int bound)
String getRandomNumberStr(int length)
String getMixedRandomStr(int length)
```



[`back to content`](#content)

------

<h3 id="dateu">DateUtils</h3>

Dependencies

```java
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
```

Methods

Convert 

```java
//int <--> String <--> Date
String getDateFormatStr(Date date, DateFormat format)
Date getDateByStr (String dateStr, DateFormat format)  
String convertDateStr(String dateStr, DateFormat fromFormat, DateFormat toFormat)
```

Calculate

```java
int getField(Date date, int calendarField)
Date add(Date date, int calendarField, int addtion)
long getDiffDays(Date firstDate, Date secondDate)
List<String> getBetweenDates(Date startDate, Date endDate, DateFormat formatter)
List<String> getBetweenMonths(Date startDate, Date endDate, DateFormat formatter)
Date getFirstDayOfWeek(Date date)
Date getFirstDayOfMonth(Date date) 
Date getLastDayOfMonth(Date date)
Date getLastDayOfWeek(Date date) 
int getWeekOfMonth(Date date)  
int getWeekOfYear(Date date)
```

[`back to content`](#content)

------

<h3 id="timeu">TimeUtils</h3>

Dependencies

Methods

```java
Timestamp getCurrentTime()
int getHourDiff (long startTime, long endTime)
String getTimeZone()
```

[`back to content`](#content)

------

<h3 id="encpu">EncryptUtils</h3>

Dependencies

```
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
```

Methods

```java
String encryptBASE64 (String source)
String decryptBASE64 (String str)
String generateSalt()
```

[`back to content`](#content)

------

<h3 id="md5u">Md5Utils</h3>

Dependencies

Methods

[`back to content`](#content)

------

<h3 id="propu">PropertyUtils</h3>

Dependencies

```
import java.io.InputStream;
import java.util.Properties;
```

Methods

```java
void load(String filePath)
String getString (String key)
int getInt (String key, int def)
long getLong (String key, long def)
```

[`back to content`](#content)

------

<h3 id="imgu">ImageUtils</h3>

Dependencies

```
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
java.io.*;
```

Methods

```java
boolean isImageFile(String fileExt)
byte[] resize(byte[] img, int width, int height)
```

[`back to content`](#content)

------

<h3 id="locu">LocationUtils</h3>

[`back to content`](#content)

------

<h3 id="fileu">FileUtils</h3>

Dependencies

Methods

[`back to content`](#content)

------

<h3 id="zipu">ZipUtils</h3>

Dependencies

Methods

[`back to content`](#content)

------

<h3 id="i18nu">I18nUtils</h3>

Dependencies

Methods

[`back to content`](#content)

------

### javax

<h3 id="ipu">IpUtils</h3>

Dependencies

```
import javax.servlet.http.HttpServletRequest;
```

Methods

```java
String getIpAddr (HttpServletRequest request)
String getRealIpAddr (HttpServletRequest request)
```

[`back to content`](#content)

------

<h3 id="reqparamu">RequestParamUtils</h3>

[`back to content`](#content)

------

<h3 id="emailu">EmailUtils</h3>

[`back to content`](#content)

------

### Third Library

<h3 id="jsonu">JsonUtils</h3>

Dependencies

```
import java.lang.reflect.Type;
import com.google.gson.Gson;
```

Methods

```java
String toJson (Object obj)
T fromJson (String str, Type type)
T fromJson (String str, Class<T> type)
```

[`back to content`](#content)

------

<h3 id="xmlu">XmlUtils</h3>

Dependencies

Methods

[`back to content`](#content)

------

<h3 id="logu">LogUtils</h3>

Dependencies

```
import org.apache.log4j.Logger;
```

Methods

```java
void debug (String message)
void info (String message)
void warn (String message)
void error (String message)
```



[`back to content`](#content)

------

<h3 id="pyu">PinyinUtils</h3>

Dependencies

```
import net.sourceforge.pinyin4j.PinyinHelper;
```

Methods

```java
String getPinyin (String str)
String getPinyinHeadChar (String str)
```



[`back to content`](#content)

------

<h3 id="httpu">HttpUtils</h3>

Dependencies

Methods

[`back to content`](#content)

------

<h3 id="qru">QrCodeUtils</h3>

Dependencies

Methods

[`back to content`](#content)

------

<h3 id="springu">SpringUtils</h3>

Dependencies

Methods

[`back to content`](#content)

------



--END--
