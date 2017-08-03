/**
 * Created by Oswald on 2017/4/27.
 */

var ibcpDate = (function () {
  var current,
      currentDate;//可以获取当前日期,格式为"2017/4/27"

  //标准化日期，小于10的月份或者天，前面补0
  function StandardizationDate(date) {
    var separate = date.match(/\D/);//获取分隔符(第一个非数字的字符)
    var dateArr = date.split(separate);//[2017,4,27]
    var year = dateArr[0],
        month = dateArr[1],
        day = dateArr[2];
    month < 10 ? month = "0" + month : month;//小于10，前面加0.如02
    day < 10 ? day = "0" + day : day;//小于10，前面加0.如04
    return [year, month, day].join("-");//返回 2017-04-27
  }

  //格式化日期
  function FormatterDate(date, formatter) {

  }

  //标准化时间，小于10，前面补0
  function StandardizationTime() {
    var h = current.getHours(),
        m = current.getMinutes(),
        s = current.getSeconds();
    h < 10 ? h = "0" + h : h;
    m < 10 ? m = "0" + m : m;
    s < 10 ? s = "0" + s : s;
    return [h, m, s].join(":");
  }

  //每次进来，重置当前时间
  function ResetCurrentDate() {
    current = new Date();
    currentDate = current.toLocaleDateString();//可以获取当前日期,格式为"2017/4/27"
  }

  //获取当前日期
  function GetCurrentDate() {
    ResetCurrentDate();
    return StandardizationDate(currentDate);
  }

  //获取当前时间
  function GetCurrentTime() {
    ResetCurrentDate();
    return StandardizationTime();
  }

  //获取当前时间之前x天的日期,参数“天”
  function GetDateBeforeCurrent(days) {
    ResetCurrentDate();
    var date = currentDate,
        daysInt = parseInt(days),
        time = " " + StandardizationTime();
    return AddSubtractDays(date, -daysInt) + time;
  }

  //加减天数
  function AddSubtractDays(date, days) {
    var d = new Date(date);
    d.setDate(d.getDate() + days);
    return StandardizationDate(d.toLocaleDateString());
  }

  //指定日期的后几天的日期
  function GetDateTimeAfterDays(dateTime, days) {
    var dateT = dateTime.split(" "),
        date = dateT[0],
        time = dateT[1];
    var newDate = AddSubtractDays(date, parseInt(days));
    return [newDate, time].join(" ");
  }

  //获取当前日期时间
  function GetCurrentDateTime() {
    var date = GetCurrentDate(),
        time = StandardizationTime();
    return date + " " + time;
  }

  return {
    currentDate: GetCurrentDate,
    currentTime: GetCurrentTime,
    currentDateTime: GetCurrentDateTime,
    dateTimeBeforeCurrent: GetDateBeforeCurrent,
    dateTimeAfterDays: GetDateTimeAfterDays
  };
})();