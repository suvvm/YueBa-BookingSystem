import Vue from 'vue'
import App from './App'
import Vant from 'vant';
import '@vant/touch-emulator';
import VueRouter from 'vue-router'
import { Toast } from 'vant';
import VueCookies from 'vue-cookies'
import { Picker } from 'vant';
import BootstrapVue from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import SIdentify from './components/identify/identify'
import lrz from 'lrz'

Vue.component("SIdentify",SIdentify);
Vue.use(BootstrapVue)
Vue.use(Picker);
Vue.use(VueCookies)
Vue.use(Toast);
Vue.use(VueRouter)
Vue.use(Vant);
Vue.config.productionTip = false

App.mpType = 'app'
const app = new Vue({
    ...App
})
app.$mount()
