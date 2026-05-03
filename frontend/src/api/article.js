import request from '@/utils/request.js'
import { useTokenStore } from '../stores/token'

export const articleCategoryListService=()=>{
    return request.get('/category')
}
export const articleCategoryAddService=(categoryData)=>{
    return request.post('/category',categoryData)
}
export const articleCategoryUpdateService=(categoryData)=>{
    return request.put('/category',categoryData)
}
export const articleCategoryDeleteService=(id)=>{
    return request.delete('/category/'+id)
}

//文章列表相关
export const articleListService=(params)=>{
    return request.get('/article',{params})
}



export const articleAddService=(articleData)=>{
    return request.post('/article',articleData)
}
export const articleEditService=(articleData)=>{
    return request.put('/article',articleData)
}
export const articleDeleteService=(articleId)=>{
    return request.delete('/article/'+articleId)
}