/**
 * Created by liyuanquan on 2017/9/19.
 */

var fun1 = function(){
    var dtd = $.Deferred(); //在函数内部，新建一个Deferred对象

    //TO-DO
    dtd.resolve(); // 在最终返回处，改变Deferred对象的执行状态

    return dtd.promise(); // 返回promise对象
};

var fun2 = function(){
    var dtd = $.Deferred(); //在函数内部，新建一个Deferred对象

    //TO-DO
    dtd.resolve(); // 在最终返回处，改变Deferred对象的执行状态

    return dtd.promise(); // 返回promise对象
};

$.when(fun1, fun2)
    .done(function() {
        if(typeof gmp_onload == "function") {
            gmp_onload();
        }
    })
    .fail(function() {
        alert("页面加载失败");
    });