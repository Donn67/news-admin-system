
import { defineStore } from "pinia";
import { ref } from "vue";
export const useUserInfoStore = defineStore('userInfo', () => {
    const defaultState = {
        id: null,           
        username: '',      
        nickname: '',       
        email: '',         
        userPic: '',        
        createTime: '',     //  (可选，如果页面不用可以不加)
        updateTime: ''      // (可选，如果页面不用可以不加)
    }
    const userInfo = ref({...defaultState})
    const setUserInfo = (newUserInfo) => {
        userInfo.value = { ...defaultState, ...newUserInfo }
    }
    const removeUserInfo = () => {
        userInfo.value = { ...defaultState }
    }
    return { userInfo, setUserInfo, removeUserInfo }
}, {
    persist: true
})
