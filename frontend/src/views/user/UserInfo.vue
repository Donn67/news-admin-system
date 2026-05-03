<script setup>
import { ref ,onMounted} from 'vue'
import {useUserInfoStore} from '../../stores/userInfo';
import { userUpdateService } from '../../api/user';
import { ElMessage } from 'element-plus'

const userInfoStore = useUserInfoStore();

// 初始化为空对象，结构由 Store 保证
const userInfo = ref({
    username: '',
    nickname: '',
    email: ''
})
const fetchUserInfo = async () => {
    const res = await userInfoService()
    if (res.code === 5000 && res.data) {
        userInfo.value = { ...res.data }
        // 可选：同步到 Store
        userInfoStore.setUserInfo(res.data)
    }
}
//组件挂载时，从 Store 同步最新数据
onMounted(() => {
    // 如果 Store 里有数据，就赋值给本地表单
    if (userInfoStore.userInfo && userInfoStore.userInfo.username) {
        userInfo.value = { ...userInfoStore.userInfo }
    } else {
        // 如果 Store 没数据，可能需要重新请求一次接口（可选）
        fetchUserInfo()
    }
})

const editUserInfo = () => {
    userUpdateService(userInfo.value).then(res => {
        if (res.code === 5000 ) {
            ElMessage.success('ユーザー情報の更新に成功しました')
            // ✅ 关键：更新 Store，这样全局状态（如 layout.vue 的头象和名字）也会更新
            userInfoStore.setUserInfo(userInfo.value)
        } else {
            ElMessage.error(res.message || '更新失敗')
        }
    }).catch(err => {
        ElMessage.error('ネットワークエラー')
    })
}

const rules = {
    nickname: [
        { required: true, message: 'ニックネームを入力してください', trigger: 'blur' },
        {
            pattern: /^\S{1,32}$/,
            message: 'ニックネームは1～32文字の半角・全角非空白文字で入力してください',
            trigger: 'blur'
        }
    ],
    email: [
        { required: true, message: 'メールアドレスを入力してください', trigger: 'blur' },
        { type: 'email', message: 'メールアドレスの形式が正しくありません', trigger: 'blur' }
    ]
}
</script>

<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>基本情報</span>
            </div>
        </template>
        <el-row>
            <el-col :span="12">
                <el-form :model="userInfo" :rules="rules" label-width="140px" size="large">
                    <el-form-item label="ログインID">
                        <el-input v-model="userInfo.username" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="ニックネーム" prop="nickname">
                        <el-input v-model="userInfo.nickname"></el-input>
                    </el-form-item>
                    <el-form-item label="メールアドレス" prop="email">
                        <el-input v-model="userInfo.email"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="editUserInfo">更新</el-button>
                    </el-form-item>
                </el-form>
            </el-col>
        </el-row>
    </el-card>
</template>