# Project Content

## Utilities

- [Data Types](#Data Types)
  - String
    - StringUtils
    - StringCaseUtils
    - CharacterEncodingUtils
    - PinyinUtils
  - Date and Time
    - DateUtils
    - TimeUtils
  - Number
    - NumberUtils
    - RandomUtils
- [Collections](#Collections)
- [IO and Files](#IO and Files)
  - FileUtils
  - ZipUtils
  - PropertyUtils
  - JsonUtils
  - XmlUtils
  - ImageUtils
  - ExcelUtils
- [Reflection](#Reflection)
  - ClassUtils
- [Security](#Security)
  - EncodeUtils
  - HashUtils
  - EncryptUtils
- [Network](#Network)
  - HttpUtils
  - IpUtils
- [Web](#Web)
  - RequestParamUtils
  - Spring Utils
- [Data Access](#Data Access)
- [Others](#Others)
  - SystemUtils
  - EmailUtils
  - QrCodeUtils
  - I18nUtils
- [Third Party APIs](#Third Party APIs)
  - LocationUtils
  - LogUtils

## Details

### Data Types

- String
  - StringUtils
    - Conversion
      - int str2Int (String str, int default)
      - long str2Long(String str, long default)
      - double str2Double(String str, double default)
      - boolean Str2Boolean(String str, boolean default)
    - Verification
      - boolean isEmpty(String str)
      - boolean isEmail(String email)   
      - boolean isMobile(String mobile)
      - boolean isMatch(String str, String regex)
    - Handling
      - String encodeMobile(String mobile)  
      - String encodeEmail(String email)
      - String encodeBankCard(String bankcard)
      - String jointString (String... strs)
      - String toNotNull (String str)
      - String expandLength(String str, int len, char fillChar)
  - StringCaseUtils
    - Conversion
      - String snakeCaseToCamelCase(String source)
      - String kebabCaseToCamelCase(String source)
      - String convertAnyCaseToCamelCase(String source, String joinChar)
      - String snakeCaseToPascalCase(String source)
      - String kebabCaseToPascalCase(String source)
      - String covertAnyCaseToPascalCase(String source, String joinChar)
      - String firstLetterToUpperCase(String source)
      - String firstLetterToLowerCase(String source)
  - CharacterEncodingUtils
    - Verification
      - boolean isChinese(String str)
      - boolean isEmoji(String str)
    - Conversion
      - String iso2Utf8(String str)
      - String emojiConvert(String str)  
      - String emojiRecovery(String str)
  - PinyinUtils
    - String getPinyin (String str)
    - String getPinyinHeadChar (String str)
- Date and Time
  - DateUtils
    - Conversion
      - String getDateFormatStr(Date date, DateFormat format)
      - Date getDateByStr (String dateStr, DateFormat format)  
      - String convertDateStr(String dateStr, DateFormat fromFormat, DateFormat toFormat)
    - Calculation
      - int getField(Date date, int calendarField)
      - Date add(Date date, int calendarField, int addtion)
      - long getDiffDays(Date firstDate, Date secondDate)
      - List<String> getBetweenDates(Date startDate, Date endDate, DateFormat formatter)
      - List<String> getBetweenMonths(Date startDate, Date endDate, DateFormat formatter)
      - Date getFirstDayOfWeek(Date date)
      - Date getFirstDayOfMonth(Date date) 
      - Date getLastDayOfMonth(Date date)
      - Date getLastDayOfWeek(Date date) 
      - int getWeekOfMonth(Date date)  
      - int getWeekOfYear(Date date)
  - TimeUtils
    - Timestamp getCurrentTime()
    - int getHourDiff (long startTime, long endTime)
    - String getTimeZone()
- Number
  - NumberUtils
    - Integer longToInteger(Object longValue, Integer defaultIntValue) 
  - RandomUtils
    - int getRandomNumber(int bound)
    - String getRandomNumberStr(int length)
    - String getMixedRandomStr(int length)

### Collections

### IO and Files

- FileUtils
- ZipUtils
- PropertyUtils
  - void load(String filePath)
  - String getString (String key)
  - int getInt (String key, int def)
  - long getLong (String key, long def)
- JsonUtils
  - String toJson (Object obj)
  - T fromJson (String str, Type type)
  - T fromJson (String str, Class<T> type)
- XmlUtils
- ExcelUtils
- ImageUtils
  - boolean isImageFile(String fileExt)
  - byte[] resize(byte[] img, int width, int height)

### Reflection

- ClassUtils
  - List<Field> getAllFieldsOfClassAndItsParent(Class cls)
  - List<Method> getAllMethodsOfClassAndItsParent(Class cls)
  - Set<String> getFieldValuesFromList(List list, Field field)
  - Object getFieldValueFromObject(Object obj, String fieldName)
  - void setFieldValueToObject(Object obj, String fieldName, Object fieldValue)

### Security

- EncodeUtils
  - String encryptBASE64 (String source)
  - String decryptBASE64 (String str)
- HashUtils
  - String md5(String source)
- EncryptUtils
  - String generateSalt()

### Network

- HttpUtils
- IpUtils
  - String getIpAddr (HttpServletRequest request)
  - String getRealIpAddr (HttpServletRequest request)

### Web

- RequestParamUtils
- Spring Utils

### Data Access

### Others

- SystemUtils
- EmailUtils
- QrCodeUtils
- I18nUtils
- LogUtils

### Third Party APIs

- LocationUtils