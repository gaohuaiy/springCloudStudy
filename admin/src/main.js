import Vue from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios'

Vue.config.productionTip = false;
Vue.prototype.$axios = axios;
/*
*axios 拦截器
*
* */
axios.interceptors.request.use(function (config) {
  console.log(config);
  return config;
},error => {})
axios.interceptors.response.use(function (response) {
  console.log("响应结果",response);
  return response;
},error => {})
new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
