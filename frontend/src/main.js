import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import VueQRCodeComponent from 'vue-qrcode-component'
import Vuetify from 'vuetify'
Vue.component('qr-code', VueQRCodeComponent)

import 'vuetify/dist/vuetify.min.css' // Ensure you are using css-loader

Vue.use(Vuetify)
Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
