import Vue from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios'
import filter from './filter/filter'

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
// 全局过滤器
Object.keys(filter).forEach(key => {
  Vue.filter(key, filter[key])
});
new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
console.log("环境：", process.env.NODE_ENV);
