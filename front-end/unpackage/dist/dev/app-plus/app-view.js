var __pageFrameStartTime__ = Date.now();
var __webviewId__;
var __wxAppCode__ = {};
var __WXML_GLOBAL__ = {
  entrys: {},
  defines: {},
  modules: {},
  ops: [],
  wxs_nf_init: undefined,
  total_ops: 0
};
var $gwx;


undefined

__wxAppCode__['app.wxss']=undefined;    
__wxAppCode__['app.wxml']=$gwx('./app.wxml');

__wxAppCode__['components/identify/identify.wxss']=undefined;    
__wxAppCode__['components/identify/identify.wxml']=$gwx('./components/identify/identify.wxml');

__wxAppCode__['node-modules/vue-datepicker/vue-datepicker-es6.wxss']=undefined;    
__wxAppCode__['node-modules/vue-datepicker/vue-datepicker-es6.wxml']=$gwx('./node-modules/vue-datepicker/vue-datepicker-es6.wxml');

__wxAppCode__['pages/chooseRole/chooseRole.wxss']=undefined;    
__wxAppCode__['pages/chooseRole/chooseRole.wxml']=$gwx('./pages/chooseRole/chooseRole.wxml');

__wxAppCode__['pages/completeRegister/completeRegister.wxss']=undefined;    
__wxAppCode__['pages/completeRegister/completeRegister.wxml']=$gwx('./pages/completeRegister/completeRegister.wxml');

__wxAppCode__['pages/createShop/createShop.wxss']=undefined;    
__wxAppCode__['pages/createShop/createShop.wxml']=$gwx('./pages/createShop/createShop.wxml');

__wxAppCode__['pages/index/AddDate.wxss']=undefined;    
__wxAppCode__['pages/index/AddDate.wxml']=$gwx('./pages/index/AddDate.wxml');

__wxAppCode__['pages/index/AddPerson.wxss']=undefined;    
__wxAppCode__['pages/index/AddPerson.wxml']=$gwx('./pages/index/AddPerson.wxml');

__wxAppCode__['pages/index/AddRoom.wxss']=undefined;    
__wxAppCode__['pages/index/AddRoom.wxml']=$gwx('./pages/index/AddRoom.wxml');

__wxAppCode__['pages/index/index.wxss']=undefined;    
__wxAppCode__['pages/index/index.wxml']=$gwx('./pages/index/index.wxml');

__wxAppCode__['pages/index/order.wxss']=undefined;    
__wxAppCode__['pages/index/order.wxml']=$gwx('./pages/index/order.wxml');

__wxAppCode__['pages/index/register.wxss']=undefined;    
__wxAppCode__['pages/index/register.wxml']=$gwx('./pages/index/register.wxml');

__wxAppCode__['pages/index/select.wxss']=undefined;    
__wxAppCode__['pages/index/select.wxml']=$gwx('./pages/index/select.wxml');

__wxAppCode__['pages/index/shopManagement/shopManagement.wxss']=undefined;    
__wxAppCode__['pages/index/shopManagement/shopManagement.wxml']=$gwx('./pages/index/shopManagement/shopManagement.wxml');

__wxAppCode__['pages/logout/logout.wxss']=undefined;    
__wxAppCode__['pages/logout/logout.wxml']=$gwx('./pages/logout/logout.wxml');

__wxAppCode__['pages/showOrders/showOrders.wxss']=undefined;    
__wxAppCode__['pages/showOrders/showOrders.wxml']=$gwx('./pages/showOrders/showOrders.wxml');

__wxAppCode__['pages/test/test.wxss']=undefined;    
__wxAppCode__['pages/test/test.wxml']=$gwx('./pages/test/test.wxml');

__wxAppCode__['pages/userWait/userWait.wxss']=undefined;    
__wxAppCode__['pages/userWait/userWait.wxml']=$gwx('./pages/userWait/userWait.wxml');

;var __pageFrameEndTime__ = Date.now();
(function() {
        window.UniLaunchWebviewReady = function(isWebviewReady){
          // !isWebviewReady && console.log('launchWebview fallback ready')
          plus.webview.postMessageToUniNView({type: 'UniWebviewReady-' + plus.webview.currentWebview().id}, '__uniapp__service');
        }
        UniLaunchWebviewReady(true);
})();
