// 查询列表数据
const getActivePage = (params) => {
    return $axios({
        url: '/active/page',
        method: 'get',
        params
    })
}

// 删除数据接口
const deleteActive = (ids) => {
    return $axios({
        url: '/active',
        method: 'delete',
        params: {ids}
    })
}

// 修改数据接口
const editActive = (params) => {
    return $axios({
        url: '/active',
        method: 'put',
        data: {...params}
    })
}

// 新增数据接口
const addActive = (params) => {
    return $axios({
        url: '/active',
        method: 'post',
        data: {...params}
    })
}

// 查询详情接口
const queryActiveById = (id) => {
    return $axios({
        url: `/active/${id}`,
        method: 'get'
    })
}

// 状态更改
const activeStatus = (params) => {
    return $axios({
        url: `/active/status/${params.status}`,
        method: 'post',
        params: {id: params.id}
    })
}
