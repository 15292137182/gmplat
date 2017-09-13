/**
 * Created by qijialin on 2016/12/5.
 * 服务器调用的地址前缀
 **/

// 获取 host url
function getBPBasePath() {

  return getBasePath(self.document.URL || self.location.href,
      getConfigFilePath());
}

function getBasePath(docUrl, confUrl) {

  var basePath = confUrl;

  if (/^(\/|\\\\)/.test(confUrl)) {
    basePath = /^.+?\w(\/|\\\\)/.exec(docUrl)[0] + confUrl.replace(/^(\/|\\\\)/,
        '');
  } else if (!/^[a-z]+:/i.test(confUrl)) {
    docUrl = docUrl.split("#")[0].split("?")[0].replace(/[^\\\/]+$/, '');
    basePath = docUrl + "" + confUrl;
  }

  return optimizationPath(basePath);
}

function getConfigFilePath() {

  var configPath = document.getElementsByTagName('script');

  return configPath[configPath.length - 1].src;
}

function optimizationPath(path) {

  var protocol = /^[a-z]+:\/\//.exec(path)[0],
      tmp = null,
      res = [];
  path = path.replace(protocol, "").split("?")[0].split("#")[0];
  path = path.replace(/\\/g, '/').split(/\//);

  return protocol + path[0];
}

//服務地址--头部--调试
//  var serverHead = 'http://192.168.100.50';

// 服務地址--头部--部署
var serverHead = getBPBasePath();

function getRootPath() {
  var curWwwPath = window.document.location.href;
  var pathName = window.document.location.pathname;
  var pos = curWwwPath.indexOf(pathName);
  var localhostPath = curWwwPath.substring(0, pos);
  var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
  return (localhostPath + projectName);
}

//完整路径
var serverPath = getRootPath() + "/gmp/sys/core";
//测试路径
//  var serverPath = serverHead + "/gmplat/gmp/sys/core";