(global["webpackJsonp"]=global["webpackJsonp"]||[]).push([["components/identify/identify"],{"1d25":function(t,e,n){"use strict";n.r(e);var o=n("eefe"),i=n("5707");for(var r in i)"default"!==r&&function(t){n.d(e,t,function(){return i[t]})}(r);var a=n("2877"),d=Object(a["a"])(i["default"],o["a"],o["b"],!1,null,null,null);e["default"]=d.exports},5707:function(t,e,n){"use strict";n.r(e);var o=n("6358"),i=n.n(o);for(var r in o)"default"!==r&&function(t){n.d(e,t,function(){return o[t]})}(r);e["default"]=i.a},6358:function(t,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0}),e.default=void 0;var o={name:"SIdentify",props:{identifyCode:{type:String,default:"1234"},fontSizeMin:{type:Number,default:16},fontSizeMax:{type:Number,default:40},backgroundColorMin:{type:Number,default:180},backgroundColorMax:{type:Number,default:240},colorMin:{type:Number,default:50},colorMax:{type:Number,default:160},lineColorMin:{type:Number,default:40},lineColorMax:{type:Number,default:180},dotColorMin:{type:Number,default:0},dotColorMax:{type:Number,default:255},contentWidth:{type:Number,default:112},contentHeight:{type:Number,default:38}},methods:{randomNum:function(t,e){return Math.floor(Math.random()*(e-t)+t)},randomColor:function(t,e){var n=this.randomNum(t,e),o=this.randomNum(t,e),i=this.randomNum(t,e);return"rgb("+n+","+o+","+i+")"},drawPic:function(){var t=document.getElementById("s-canvas"),e=t.firstElementChild.getContext("2d");e.textBaseline="bottom",e.fillStyle=this.randomColor(this.backgroundColorMin,this.backgroundColorMax),e.fillRect(0,0,this.contentWidth,this.contentHeight);for(var n=0;n<this.identifyCode.length;n++)this.drawText(e,this.identifyCode[n],n);this.drawLine(e),this.drawDot(e)},drawText:function(t,e,n){t.fillStyle=this.randomColor(this.colorMin,this.colorMax),t.font=this.randomNum(this.fontSizeMin,this.fontSizeMax)+"px SimHei";var o=(n+1)*(this.contentWidth/(this.identifyCode.length+1)),i=this.randomNum(this.fontSizeMax,this.contentHeight-5),r=this.randomNum(-45,45);t.translate(o,i),t.rotate(r*Math.PI/180),t.fillText(e,0,0),t.rotate(-r*Math.PI/180),t.translate(-o,-i)},drawLine:function(t){for(var e=0;e<8;e++)t.strokeStyle=this.randomColor(this.lineColorMin,this.lineColorMax),t.beginPath(),t.moveTo(this.randomNum(0,this.contentWidth),this.randomNum(0,this.contentHeight)),t.lineTo(this.randomNum(0,this.contentWidth),this.randomNum(0,this.contentHeight)),t.stroke()},drawDot:function(t){for(var e=0;e<100;e++)t.fillStyle=this.randomColor(0,255),t.beginPath(),t.arc(this.randomNum(0,this.contentWidth),this.randomNum(0,this.contentHeight),1,0,2*Math.PI),t.fill()}},watch:{identifyCode:function(){this.drawPic()}},mounted:function(){this.drawPic()}};e.default=o},eefe:function(t,e,n){"use strict";var o=function(){var t=this,e=t.$createElement;t._self._c},i=[];n.d(e,"a",function(){return o}),n.d(e,"b",function(){return i})}},[["1315","common/runtime","common/vendor"]]]);