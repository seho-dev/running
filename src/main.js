import Vue from 'vue'
import App from './App'
//引入reset.css
import 'assets/styles/reset.css'
import 'assets/styles/border.css'
import 'assets/styles/iconfonts/iconfont.css'
import 'animate.css'
Vue.config.productionTip = false

App.mpType = 'app'

const app = new Vue({
  ...App
})
app.$mount()
