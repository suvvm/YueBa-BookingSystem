"use strict";

var _interopRequireDefault = require("@babel/runtime/helpers/interopRequireDefault");

exports.__esModule = true;
exports.default = void 0;

var _utils = require("../../utils");

var _icon = _interopRequireDefault(require("../../icon"));

var _loading = _interopRequireDefault(require("../../loading"));

var _uploader = _interopRequireDefault(require("../../uploader"));

var _createNamespace = (0, _utils.createNamespace)('sku-img-uploader'),
    createComponent = _createNamespace[0],
    bem = _createNamespace[1];

var _default = createComponent({
  props: {
    value: String,
    uploadImg: Function,
    maxSize: {
      type: Number,
      default: 6
    }
  },
  data: function data() {
    return {
      // 正在上传的图片 base64
      paddingImg: '',
      uploadFail: false
    };
  },
  methods: {
    afterReadFile: function afterReadFile(file) {
      var _this = this;

      // 上传文件
      this.paddingImg = file.content;
      this.uploadFail = false;
      this.uploadImg(file.file, file.content).then(function (img) {
        _this.$emit('input', img);

        _this.$nextTick(function () {
          _this.paddingImg = '';
        });
      }).catch(function () {
        _this.uploadFail = true;
      });
    },
    onOversize: function onOversize() {
      this.$toast("\u6700\u5927\u53EF\u4E0A\u4F20\u56FE\u7247\u4E3A" + this.maxSize + "MB\uFF0C\u8BF7\u5C1D\u8BD5\u538B\u7F29\u56FE\u7247\u5C3A\u5BF8");
    },
    renderUploader: function renderUploader(content, disabled) {
      if (disabled === void 0) {
        disabled = false;
      }

      var h = this.$createElement;
      return h(_uploader.default, {
        "class": bem('uploader'),
        "attrs": {
          "disabled": disabled,
          "afterRead": this.afterReadFile,
          "maxSize": this.maxSize * 1024 * 1024
        },
        "on": {
          "oversize": this.onOversize
        }
      }, [h("div", {
        "class": bem('img')
      }, [content])]);
    },
    renderMask: function renderMask() {
      var h = this.$createElement;
      return h("div", {
        "class": bem('mask')
      }, [this.uploadFail ? [h(_icon.default, {
        "attrs": {
          "name": "warning-o",
          "size": "20px"
        }
      }), h("div", {
        "class": bem('warn-text')
      }, ["\u4E0A\u4F20\u5931\u8D25", h("br"), "\u91CD\u65B0\u4E0A\u4F20"])] : h(_loading.default, {
        "attrs": {
          "type": "spinner",
          "size": "20px",
          "color": "white"
        }
      })]);
    }
  },
  render: function render() {
    var _this2 = this;

    var h = arguments[0];
    return h("div", {
      "class": bem()
    }, [this.value && this.renderUploader([h("img", {
      "attrs": {
        "src": this.value
      }
    }), h(_icon.default, {
      "attrs": {
        "name": "clear"
      },
      "class": bem('delete'),
      "on": {
        "click": function click() {
          _this2.$emit('input', '');
        }
      }
    })], true), this.paddingImg && this.renderUploader([h("img", {
      "attrs": {
        "src": this.paddingImg
      }
    }), this.renderMask()], !this.uploadFail), !this.value && !this.paddingImg && this.renderUploader(h("div", {
      "class": bem('trigger')
    }, [h(_icon.default, {
      "attrs": {
        "name": "photograph",
        "size": "22px"
      }
    })]))]);
  }
});

exports.default = _default;