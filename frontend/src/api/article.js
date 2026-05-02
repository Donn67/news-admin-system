import request from '@/utils/request.js'
import { useTokenStore } from '../stores/token'

export const articleCategoryListService=()=>{
    const tokenStore=useTokenStore()
    return request.get('/category')
}