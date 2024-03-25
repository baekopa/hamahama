import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'

// Vuetify
import 'vuetify/styles'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'

// ElementPlus
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

import axios from 'axios'

const vuetify = createVuetify({
  components,
  directives
})

const app = createApp(App)

axios.defaults.baseURL = import.meta.env.VITE_BASE_URL
app.config.globalProperties.axios = axios

app.use(createPinia())
app.use(router)
app.use(vuetify)
app.use(ElementPlus)
Kakao.init('8e0daabccbcb70d0d97a40d41297576d')
console.log(Kakao.isInitialized())

app.mount('#app')
