import request from '@/utils/request.js'
import { pa } from 'element-plus/es/locales.mjs';
export const userRegisterService=(registerData)=>{
    return request.post('/user/register',registerData);
}
export const userLoginService=(loginData)=>{
    return request.post('/user/login',loginData);
}

export const userInfoService=()=>{
    return request.get('/user/userinfo');
}
export const userUpdateService=(updateData)=>{
    return request.put('/user/update',updateData);
}
export const userAvatarUpdateService=(url)=>{
    const params=new URLSearchParams()
    params.append('avatarUrl',url)
    return request.patch('/user/updateAvatar',params)
}
export const userPasswordUpdateService=(passwordData)=>{
    return request.patch('/user/updatePwd',passwordData)
}