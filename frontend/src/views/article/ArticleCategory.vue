<script setup>
import {
    Edit,
    Delete
} from '@element-plus/icons-vue'
import { ref } from 'vue'
import { articleCategoryListService, articleCategoryAddService,articleCategoryUpdateService,
    articleCategoryDeleteService } from '@/api/article.js'
import { ElMessage,ElMessageBox } from 'element-plus'
const categories = ref([])
const articleCategoryList = async () => {
    let result = await articleCategoryListService();
    categories.value = result.data;
}
articleCategoryList()
//控制添加分类弹窗
const dialogVisible = ref(false)

//添加分类数据模型
const categoryModel = ref({
    categoryName: '',
    categoryAlias: ''
})

//添加分类表单校验
const rules = {
    categoryName: [
        { required: true, message: 'カテゴリ名を入力してください', trigger: 'blur' },
    ],
    categoryAlias: [
        { required: true, message: 'カテゴリ別名を入力してください', trigger: 'blur' },
    ]
}

const addCategory = async() => {
        try {

        await articleCategoryAddService(categoryModel.value);
        
        ElMessage.success('カテゴリの追加に成功しました');
        dialogVisible.value = false;

        await articleCategoryList();
        

        clearData();
        
    } catch (error) {
        ElMessage.error('カテゴリの追加に失敗しました');
    }
}
const clearData = () => {
    categoryModel.value={
        id:null,
        categoryName:'',
        categoryAlias:''
    }
}
//控制标题展示
const title = ref('');
const editView=(row)=>{
    dialogVisible.value=true;
    title.value='カテゴリを編集';
    categoryModel.value.id=row.id;
    categoryModel.value.categoryName=row.categoryName;
    categoryModel.value.categoryAlias=row.categoryAlias;
}

const editCategory = async() => { 
    try {

        await articleCategoryUpdateService(categoryModel.value)
        
        ElMessage.success('カテゴリの編集に成功しました');
        dialogVisible.value = false;

        await articleCategoryList();
        clearData();
        
    } catch (error) {
        ElMessage.error('カテゴリの編集に失敗しました');
    }
}
const open=(row)=>{
    ElMessageBox.confirm('このカテゴリを削除しますか？', '確認', {
        confirmButtonText: 'はい',
        cancelButtonText: 'いいえ',
        type: 'warning'
    }).then(() => {
        deleteCategory(row)
    }).catch(() => {
        ElMessage({
            type: 'info',
            message: 'キャンセルされました'
        })
    })
}
const deleteCategory = async(row) => { 
    if (!row.id) {
        ElMessage.error('カテゴリIDが無効です');
        return;
    }

    try {
        await articleCategoryDeleteService(row.id);
        ElMessage.success('カテゴリの削除に成功しました');
        await articleCategoryList();
        
    } catch (error) {
        ElMessage.error('カテゴリの削除に失敗しました');
    }
}
const handleSubmit = () => {
    if (title.value === 'カテゴリを追加') {
        addCategory()
    } else  {
        editCategory()
    }
}
</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>記事カテゴリ</span>
                <div class="extra">
                    <el-button type="primary" @click="dialogVisible=true;title='カテゴリを追加'">カテゴリ追加</el-button>
                </div>
            </div>
        </template>
        <el-table :data="categories" style="width: 100%">
            <el-table-column label="番号" width="100" type="index"> </el-table-column>
            <el-table-column label="カテゴリ名" prop="categoryName"></el-table-column>
            <el-table-column label="カテゴリ別名" prop="categoryAlias"></el-table-column>
            <el-table-column label="操作" width="100">
                <template #default="{ row }">
                    <el-button :icon="Edit" circle plain type="primary" @click="editView(row)"></el-button>
                    <el-button :icon="Delete" circle plain type="danger" @click="open(row)"></el-button>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="データなし" />
            </template>
        </el-table>
        <!-- 添加分类弹窗 -->
        <el-dialog v-model="dialogVisible" :title="title" width="30%">
            <el-form :model="categoryModel" :rules="rules" label-width="120px" style="padding-right: 30px">
                <el-form-item label="カテゴリ名" prop="categoryName">
                    <el-input v-model="categoryModel.categoryName" minlength="1" maxlength="10"></el-input>
                </el-form-item>
                <el-form-item label="カテゴリ別名" prop="categoryAlias">
                    <el-input v-model="categoryModel.categoryAlias" minlength="1" maxlength="15"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dialogVisible = false;clearData()" >キャンセル</el-button>
                    <el-button type="primary" @click="handleSubmit"> 確定 </el-button>
                </span>
            </template>
        </el-dialog>
    </el-card>
</template>

<style lang="scss" scoped>
.page-container {
    min-height: 100%;
    box-sizing: border-box;

    .header {
        display: flex;
        align-items: center;
        justify-content: space-between;
    }
}
</style>