import request from '@/utils/request.js'
export const userRegisterService=(registerData)=>{
    return request.post('/user/register',registerData);
}