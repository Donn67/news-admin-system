<script setup>
import {
    Edit,
    Delete
} from '@element-plus/icons-vue';

import { ref } from 'vue';
import { articleCategoryListService, articleListService,
    articleAddService,articleEditService,articleDeleteService} from '@/api/article';
import { fileUploadService } from '../../utils/upload';
import { QuillEditor } from '@vueup/vue-quill';
import '@vueup/vue-quill/dist/vue-quill.snow.css';


//文章分类: 后台返回数据之一示例
const categories = ref([
    {
        categoryAlias: "ビジネス",
        categoryName: "経済",
        createTime: "2026-04-29 09:09:39",
        id: -1134989311,
        updateTime: "2026-04-29 09:09:39"
    }
])

//用户搜索时选中的分类id
const categoryId = ref(undefined)

//用户搜索时选中的发布状态
const state = ref(undefined)




//文章列表数据模型
const articles = ref([])

//分页条数据模型
const pageNum = ref(1)//当前页
const total = ref(20)//总条数
const pageSize = ref(5)//每页条数

//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
    pageSize.value = size,
        pageNum.value = 1,
        articleList();
}
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
    pageNum.value = num;
    articleList();
}
//文章列表相关
const getCategoryNameById = (id) => {
    for (let i = 0; i < categories.value.length; i++) {
        if (categories.value[i].id === id) {
            return categories.value[i].categoryName;
        }
    }
}
const articleList = async () => {
    const params = {
        pageNum: pageNum.value,
        pageSize: pageSize.value
    }

    // 2. 只有当分类ID有值时，才加入参数
    if (categoryId.value) {
        params.categoryId = categoryId.value
    }

    // 3. 只有当状态有值时，才加入参数
    if (state.value) {
        params.state = state.value
    }

    let result = await articleListService(params)
    if (result.code === 5000) {
        const list = result.data.items;
        for (let i = 0; i < list.length; i++) {
            list[i].categoryName = getCategoryNameById(list[i].categoryId)
        }
        articles.value = list || [];
        total.value = result.data.total || 0;
    }
}

const resetSearch = () => {
    categoryId.value = undefined;
    state.value = undefined;
    pageNum.value = 1;
    articleList();
}
//文章分类列表
const articleCategoryList = async () => {
    let result = await articleCategoryListService()
    categories.value = result.data
    articleList();
}


articleCategoryList();



import { Plus } from '@element-plus/icons-vue'
import { ElMessage ,ElMessageBox} from 'element-plus'

//控制抽屉是否显示
const visibleDrawer = ref(false)
//编辑or添加
const title = ref('記事追加') 
const isEdit = ref(false) // 标记当前是否为编辑模式
//添加表单数据模型
const articleModel = ref({
    id:null,
    title: '',
    categoryId: null,
    coverImg: '',
    content: '',
    state: ''
})

const uploadCoverImage=async(options)=>{
    const {file}=options
    try{
        const res=await fileUploadService(file)
        if(res.code===5000){
            articleModel.value.coverImg=res.data;
            // console.log(res.data)
            ElMessage.success('画像のアップロードに成功しました')
        }else{
            ElMessage.error('画像のアップロードに失敗しました')
        }
    }catch(error){
        ElMessage.error('画像のアップロード中にエラーが発生しました')
    }
}
//强制刷新quill
const quillKey=ref(0)
const clearData = () => { 
    articleModel.value = {
        id:null,
        title: '',
        categoryId: null,
        coverImg: '',
        content: '',
        state: ''
    }
    quillKey.value++
}
const handleAdd=()=>{
    clearData()
    title.value = '記事追加'
    isEdit.value = false
    visibleDrawer.value = true
}
const handleEdit =  (row) => { 
    clearData()
    title.value = '記事編集'
    isEdit.value = true
    articleModel.value = {
        id: row.id,
        title: row.title,
        categoryId: row.categoryId,
        coverImg: row.coverImg,
        content: row.content||'',
        state: row.state
    }
    visibleDrawer.value = true
}
const submitArticle = async (state) => {

    articleModel.value.state = state
    
    let result
    if (isEdit.value) {
        // 执行编辑
        result = await articleEditService(articleModel.value)
    } else {
        // 执行新增
        result = await articleAddService(articleModel.value)
    }

    if (result.code == 5000) {
        ElMessage.success(isEdit.value ? '記事を編集しました' : '記事を追加しました')
        visibleDrawer.value = false
        clearData()
        articleList() // 刷新列表
    } else {
        ElMessage.error(result.message || '操作失败')
    }
}
//删除
const deleteArticle = async(id) => { 
    if (!id) {
        ElMessage.error('記事IDが無効です')
        return}
        try { 
            const result = await articleDeleteService(id)
            ElMessage.success('記事の削除に成功しました')
            await articleList()
        } catch (error) {
            ElMessage.error('記事の削除に失敗しました')
        }
    
}
const open=(row) => {
    ElMessageBox.confirm('この記事を削除しますか？', '確認', {
        confirmButtonText: 'はい',
        cancelButtonText: 'いいえ',
        type: 'warning'
    }).then(() => {
        deleteArticle(row.id)
    }).catch(() => {
        ElMessage({
            type: 'info',
            message: 'キャンセルされました'
        })
    }) 
}




</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>記事管理</span>
                <div class="extra">
                    <el-button type="primary" @click="visibleDrawer = true;clearData()">記事を追加</el-button>
                </div>
            </div>
        </template>
        <!-- 検索フォーム -->
        <el-form inline>
            <el-form-item label="記事カテゴリ：">
                <el-select placeholder="選択してください" v-model="categoryId">
                    <el-option v-for="c in categories" :key="c.id" :label="c.categoryName" :value="c.id">
                    </el-option>
                </el-select>
            </el-form-item>

            <el-form-item label="公開状態：">
                <el-select placeholder="選択してください" v-model="state">
                    <el-option label="公開" value="公開"></el-option>
                    <el-option label="下書き" value="下書き"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="articleList">検索</el-button>
                <el-button @click="resetSearch">リセット</el-button>
            </el-form-item>
        </el-form>
        <!-- 記事一覧 -->
        <el-table :data="articles" style="width: 100%">
            <el-table-column label="記事タイトル" width="400" prop="title"></el-table-column>
            <el-table-column label="カテゴリ" prop="categoryName"></el-table-column>
            <el-table-column label="投稿日時" prop="createTime"> </el-table-column>
            <el-table-column label="状態" prop="state"></el-table-column>
            <el-table-column label="操作" width="100">
                <template #default="{ row }">
                    <el-button :icon="Edit" circle plain type="primary" @click="handleEdit(row)"></el-button>
                    <el-button :icon="Delete" circle plain type="danger" @click="open(row)"></el-button>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="データがありません" />
            </template>
        </el-table>
        <!-- ページネーション -->
        <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[ 5, 10, 15]"
            layout="jumper, total, sizes, prev, pager, next" background :total="total" @size-change="onSizeChange"
            @current-change="onCurrentChange" style="margin-top: 20px; justify-content: flex-end" />


        <el-drawer v-model="visibleDrawer" :title="title" direction="rtl" size="50%">
            <!-- 記事追加フォーム -->
            <el-form :model="articleModel" label-width="100px">
                <el-form-item label="記事タイトル">
                    <el-input v-model="articleModel.title" 
                    placeholder="タイトルを入力してください"
                    maxlength="120"
                    show-word-limit></el-input>
                </el-form-item>
                <el-form-item label="記事カテゴリ">
                    <el-select placeholder="選択してください" v-model="articleModel.categoryId">
                        <el-option v-for="c in categories" :key="c.id" :label="c.categoryName" :value="c.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="記事表紙">
                    
                    <el-upload class="avatar-uploader" :auto-upload="true" :show-file-list="false"
                    :http-request="uploadCoverImage">
                        <img v-if="articleModel.coverImg" :src="articleModel.coverImg" class="avatar" />
                        <el-icon v-else class="avatar-uploader-icon">
                            <Plus />
                        </el-icon>
                    </el-upload>
                </el-form-item>
                <el-form-item label="本文">

                     <div class="editor">
                        <QuillEditor theme="snow" 
                        v-model:content="articleModel.content" 
                        contentType="html" 
                        :key="quillKey"/>
                     </div>
                    
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="submitArticle('公開')">公開</el-button>
                    <el-button type="info" @click="submitArticle('下書き')">下書き保存</el-button>
                </el-form-item>
            </el-form>
        </el-drawer>


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


/* 抽屉样式 */
.avatar-uploader {
    :deep() {
        .avatar {
            width: 178px;
            height: 178px;
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
            width: 178px;
            height: 178px;
            text-align: center;
        }
    }
}

.editor {
    width: 100%;

    :deep(.ql-editor) {
        min-height: 200px;
    }
}

</style>