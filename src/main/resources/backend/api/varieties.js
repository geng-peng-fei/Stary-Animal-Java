// 查询列表接口
const getVarietiesPage = (params) => {
    return $axios({
        url: '/varieties/page',
        method: 'get',
        params
    })
}

// 编辑页面反查详情接口
const queryVarietiesById = (id) => {
    return $axios({
        url: `/varieties/${id}`,
        method: 'get'
    })
}

// 删除当前列的接口
const deleteVarieties = (ids) => {
    return $axios({
        url: '/varieties',
        method: 'delete',
        params: {ids}
    })
}

// 修改接口
const editVarieties = (params) => {
    return $axios({
        url: '/varieties',
        method: 'put',
        data: {...params}
    })
}

// 新增接口
const addVarieties = (params) => {
    return $axios({
        url: '/varieties',
        method: 'post',
        data: {...params}
    })
}

// 获取宠物分类列表
const getVarietiesList = (params) => {
    return axios({
        url: '/varieties/list',
        method: 'get',
        params
    })
}