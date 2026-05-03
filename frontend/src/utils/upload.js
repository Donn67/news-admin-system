import request from '@/utils/request';
export const fileUploadService = (file) => {
    const formData = new FormData()
    formData.append('file', file) // 'file' 需与后端接收参数名一致
    return request.post('/upload', formData, {
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    })
}