import request from '@/utils/request.js'
export const userRegisterService=(registerData)=>{
    return request.post('/user/register',registerData);
}
export const userLoginService=(loginData)=>{
    return request.post('/user/login',loginData);
}