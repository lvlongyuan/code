import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from "./store"//引入vuex
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import './assets/gloable.css'
import request from "./utils/request"
import locale from 'element-ui/lib/locale/lang/zh-CN' // 配置全局按钮，输入框 size

Vue.use(ElementUI, { locale, size: 'small' })

Vue.config.productionTip = false

Vue.use(ElementUI)

Vue.prototype.request=request

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
