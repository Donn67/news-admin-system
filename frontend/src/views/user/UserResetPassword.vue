<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>パスワード再設定</span>
            </div>
        </template>
        <el-row>
            <el-col :span="12">
                <el-form :model="form" :rules="rules" ref="formRef" label-width="140px">
                    <el-form-item label="旧パスワード" prop="oldPwd">
                        <el-input type="password" v-model="form.oldPwd" show-password></el-input>
                    </el-form-item>
                    <el-form-item label="新パスワード" prop="newPwd">
                        <el-input type="password" v-model="form.newPwd" show-password></el-input>
                    </el-form-item>
                    <el-form-item label="確認用パスワード" prop="rePwd">
                        <el-input type="password" v-model="form.rePwd" show-password></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="submitForm">更新</el-button>
                        <el-button @click="resetForm">リセット</el-button>
                    </el-form-item>
                </el-form>
            </el-col>
        </el-row>
    </el-card>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'
import { userPasswordUpdateService} from '@/api/user'
import { useRouter } from 'vue-router'
import { useTokenStore } from '../../stores/token'
import { useUserInfoStore } from '../../stores/userInfo'

const formRef = ref()
const form = reactive({
    oldPwd: '',
    newPwd: '',
    rePwd: ''
})

const validateConfirm = (rule, value, callback) => {
    if (value !== form.newPwd) {
        callback(new Error('パスワードが一致しません'))
    } else {
        callback()
    }
}

const rules = {
    oldPwd: [
        { required: true, message: '旧パスワードを入力してください', trigger: 'blur' },
        { min: 5, max: 16, message: '5~16文字で入力してください', trigger: 'blur' }
    ],
    newPwd: [
        { required: true, message: '新パスワードを入力してください', trigger: 'blur' },
        { min: 5, max: 16, message: '5~16文字で入力してください', trigger: 'blur' }
    ],
    rePwd: [
        { required: true, message: '確認用パスワードを入力してください', trigger: 'blur' },
        { validator: validateConfirm, trigger: 'blur' }
    ]
}
const router = useRouter()
const tokenStore = useTokenStore()
const userInfoStore = useUserInfoStore()
const logout=() => {
    tokenStore.removeToken();
    userInfoStore.removeUserInfo();
    router.push('/login')
}
const submitForm = async () => {
    await formRef.value.validate()
    try {
        const res = await userPasswordUpdateService(form)
        if (res.code === 5000) {
            ElMessage.success('パスワードを更新しました。再ログインしてください')
            // 退出登录并跳转到登录页
            await logout()
            
        } else {
            ElMessage.error(res.message || '更新に失敗しました')
        }
    } catch (error) {
        ElMessage.error('エラーが発生しました')
    }
}

const resetForm = () => {
    formRef.value.resetFields()
}
</script>