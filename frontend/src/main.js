import './assets/main.scss'

import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import App from './App.vue'
import router from '@/router'
import { createPinia } from 'pinia'
import { createPersistedState } from 'pinia-plugin-persistedstate'

const persist=createPersistedState();


const app = createApp(App);
app.use(ElementPlus);
app.use(router);

const pinia=createPinia();
pinia.use(persist);
app.use(pinia);

app.mount('#app');
