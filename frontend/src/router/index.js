import { createRouter,createWebHistory} from 'vue-router'
import Login from '@/views/login.vue'
import Layout from '@/views/layout.vue';
import UserAvatar from '@/views/user/UserAvatar.vue'
import UserInfo from '@/views/user/UserInfo.vue'
import UserResetPassword from '@/views/user/UserResetPassword.vue'
import ArticleCategory from '@/views/article/ArticleCategory.vue'
import ArticleManage from '@/views/article/ArticleManage.vue'


const routes= [
    {path:'/login',component:Login},
    {path:'/',component:Layout,redirect:'/article/manage',
        children:[
            {path:'/article/category',component:ArticleCategory},
            {path:'/article/manage',component:ArticleManage},
            {path:'/user/avatar',component:UserAvatar},
            {path:'/user/info',component:UserInfo},
            {path:'/user/resetPassword',component:UserResetPassword}
        ]
    }
]
const router=createRouter(
    {
        routes:routes,
        history:createWebHistory()
    }
)
export default router;