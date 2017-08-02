/**
 * Created by Oswald on 2017/6/1.
 */
//调用软键盘
$(function () {
  $("body").on("click", "input,textarea", function () {
    if ($(this).attr("readonly")) {
      return;
    }
    try {
      JsEvent.showKB();
    }
    catch (e) {

    }
  });
})