// 查询列表接口
const getPetVarietiesPage = (params) => {
    return axios({
        url: '/pet_Varieties/page',
        method: 'get',
        params
    })
}

// 删除接口
const deletePetVarieties = (ids) => {
    return axios({
        url: '/pet_Varieties',
        method: 'delete',
        params: {ids}
    })
}

// 修改接口
const editPetVarieties = (params) => {
    return axios({
        url: '/pet_Varieties',
        method: 'put',
        data: {...params}
    })
}

// 新增接口
const addPetVarieties = (params) => {
    return axios({
        url: '/pet_Varieties',
        method: 'post',
        data: {...params}
    })
}

// 查询详情
const queryPetVarietiesById = (id) => {
    return axios({
        url: `/pet_Varieties/${id}`,
        method: 'get'
    })
}





// 文件down预览
const commonDownload = (params) => {
    return $axios({
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
        },
        url: '/common/download',
        method: 'get',
        params
    })
}

// 修改宠物状态
const petVarietiesStatusByStatus = (params) => {
    return $axios({
        url: '/pet_Varieties/status/${params.status}',
        method: 'post',
        params: {ids: params.id}
    })
}