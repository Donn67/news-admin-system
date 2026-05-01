<script setup>
import { User, Lock } from '@element-plus/icons-vue'
import { ref } from 'vue'
import {userRegisterService} from '@/api/user.js'
//控制注册与登录表单的显示， 默认显示注册
const isRegister = ref(false)
const registerData=ref({
    username:'',
    password:'',
    rePassword:''
})

const validateRePassword=(rule,value,callback)=>{
    if(value!==registerData.value.password){
        callback(new Error('パスワードが一致しません'))
    }else{
        callback()
    }
}
const register=async()=>{
    let result=await userRegisterService(registerData.value);
    if(result.code===5000){
        alert(result.message?result.message:'新規登録に成功しました')
    }else(
        alert('新規登録に失敗しました')
    )
}
//校验规则
const rules={
    username:[
       {required:true,message:'ユーザー名を入力してください',trigger:'blur'} ,
       {min:5,max:16,message:'ユーザー名は5文字以上16文字以下で入力してください', trigger: 'blur'}
    ],
    password:[
        {required:true, message: 'パスワードを入力してください', trigger: 'blur'},
        { min: 5, max:16, message: 'パスワードは5文字以上16文字以下で入力してください', trigger: 'blur' }
  ],
  rePassword: [
        { required: true, message: 'パスワードを再入力してください', trigger: 'blur' },
        { min: 5, max: 16, message: 'パスワードは5文字以上16文字以下で入力してください', trigger: 'blur' },
        { validator: validateRePassword, trigger: 'blur' } // 两次密码一致校验
  ]
}

</script>

<template>
    <el-row class="login-page">
        <el-col :span="12" class="bg"></el-col>
        <el-col :span="6" :offset="3" class="form">
            <!-- 注册表单 -->
            <el-form ref="form" size="large" autocomplete="off" v-if="isRegister" :model="registerData" :rules="rules">
                <el-form-item>
                    <h1>新規登録</h1>
                </el-form-item>
                <el-form-item prop="username">
                    <el-input :prefix-icon="User" placeholder="ユーザー名を入力してください" v-model="registerData.username"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input :prefix-icon="Lock" type="password" placeholder="パスワードを入力してください" v-model="registerData.password"></el-input>
                </el-form-item>
                <el-form-item prop="rePassword">
                    <el-input :prefix-icon="Lock" type="password" placeholder="パスワードを再入力してください" v-model="registerData.rePassword"></el-input>
                </el-form-item>
                <!-- 注册按钮 -->
                <el-form-item>
                    <el-button class="button" type="primary" auto-insert-space @click="register">
                        新規登録
                    </el-button>
                </el-form-item>
                <el-form-item class="flex">
                    <el-link type="info" :underline="false" @click="isRegister = false">
                        ← 戻る
                    </el-link>
                </el-form-item>
            </el-form>
            <!-- 登录表单 -->
            <el-form ref="form" size="large" autocomplete="off" v-else>
                <el-form-item>
                    <h1>ログイン</h1>
                </el-form-item>
                <el-form-item>
                    <el-input :prefix-icon="User" placeholder="ユーザー名を入力してください"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-input name="password" :prefix-icon="Lock" type="password" placeholder="パスワードを入力してください"></el-input>
                </el-form-item>
                <el-form-item class="flex">
                    <div class="flex">
                        <el-checkbox>ログイン状態を保持</el-checkbox>
                        <el-link type="primary" :underline="false">パスワードを忘れた方はこちら</el-link>
                    </div>
                </el-form-item>
                <!-- 登录按钮 -->
                <el-form-item>
                    <el-button class="button" type="primary" auto-insert-space>ログイン</el-button>
                </el-form-item>
                <el-form-item class="flex">
                    <el-link type="info" :underline="false" @click="isRegister = true">
                        新規登録 →
                    </el-link>
                </el-form-item>
            </el-form>
        </el-col>
    </el-row>
</template>

<style lang="scss" scoped>
/* 样式 */
.login-page {
    height: 100vh;
    background-color: #fff;

    .bg {
        background: url('@/assets/logo2.png') no-repeat 50% center / 240px auto,
            url('@/assets/login_bg.jpg') no-repeat center / cover;
        border-radius: 0 20px 20px 0;
    }

    .form {
        display: flex;
        flex-direction: column;
        justify-content: center;
        user-select: none;

        .title {
            margin: 0 auto;
        }

        .button {
            width: 100%;
        }

        .flex {
            width: 100%;
            display: flex;
            justify-content: space-between;
        }
    }
}
</style>