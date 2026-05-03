<script setup>
import { Plus, Upload } from '@element-plus/icons-vue'
import { computed, ref } from 'vue'
import avatar from '@/assets/default.png'
import { useUserInfoStore } from '../../stores/userInfo'
import { fileUploadService } from '../../utils/upload'
import { ElMessage } from 'element-plus'
import { userAvatarUpdateService } from '../../api/user'

const userInfoStore = useUserInfoStore()
const uploadRef = ref()

const selectedFile = ref(null)          // 存储选中的文件对象
const localPreviewUrl = ref('')         // 本地预览 URL（blob）

// 当前显示的头像地址：优先用本地预览URL，否则用 store 中的地址
const imgUrl = computed(() => localPreviewUrl.value || userInfoStore.userInfo.userPic)

// 当用户选择文件时触发（仅本地预览，不上传）
const handleFileChange = (file) => {
    if (!file.raw) return
    selectedFile.value = file.raw
    // 生成本地预览 URL
    if (localPreviewUrl.value) {
        URL.revokeObjectURL(localPreviewUrl.value)
    }
    localPreviewUrl.value = URL.createObjectURL(file.raw)
}

// 上传并更新头像
const editAvatar = async () => {
    if (!selectedFile.value) {
        ElMessage.warning('先に画像を選択してください')
        return
    }
    try {
        // 1. 上传到 OSS
        const uploadRes = await fileUploadService(selectedFile.value)
        if (uploadRes.code !== 5000) {
            ElMessage.error('画像のアップロードに失敗しました')
            return
        }
        const uploadedUrl = uploadRes.data
        // 2. 更新数据库中的用户头像
        const updateRes = await userAvatarUpdateService(uploadedUrl)
        if (updateRes.code === 5000) {
            userInfoStore.userInfo.userPic = uploadedUrl
            ElMessage.success('アバターの更新に成功しました')
            // 清空临时文件和预览
            if (localPreviewUrl.value) {
                URL.revokeObjectURL(localPreviewUrl.value)
                localPreviewUrl.value = ''
            }
            selectedFile.value = null
        } else {
            ElMessage.error('アバターの更新に失敗しました')
        }
    } catch (error) {
        ElMessage.error('処理中にエラーが発生しました')
    }
}
</script>

<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>アバター変更</span>
            </div>
        </template>
        <el-row>
            <el-col :span="12">
                <el-upload
                    ref="uploadRef"
                    class="avatar-uploader"
                    :show-file-list="false"
                    :auto-upload="false"
                    :on-change="handleFileChange"
                >
                    <img v-if="imgUrl" :src="imgUrl" class="avatar" />
                    <img v-else :src="avatar" width="278" />
                </el-upload>
                <br />
                <el-button type="primary" :icon="Plus" size="large" @click="uploadRef.$el.querySelector('input').click()">
                    画像を選択
                </el-button>
                <el-button type="success" :icon="Upload" size="large" @click="editAvatar">
                    アバターを更新
                </el-button>
            </el-col>
        </el-row>
    </el-card>
</template>

<style lang="scss" scoped>
.avatar-uploader {
    :deep() {
        .avatar {
            width: 278px;
            height: 278px;
            display: block;
        }

        .el-upload {
            border: 1px dashed var(--el-border-color);
            border-radius: 6px;
            cursor: pointer;
            position: relative;
            overflow: hidden;
            transition: var(--el-transition-duration-fast);
        }

        .el-upload:hover {
            border-color: var(--el-color-primary);
        }

        .el-icon.avatar-uploader-icon {
            font-size: 28px;
            color: #8c939d;
            width: 278px;
            height: 278px;
            text-align: center;
        }
    }
}
</style>