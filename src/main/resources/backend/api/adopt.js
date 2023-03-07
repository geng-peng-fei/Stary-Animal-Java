// 查询列表页接口
const getAdoptPage = (params) => {
    return $axios({
        url: '/adopt/page',
        method: 'get',
        params
    })
}

// 同意，拒绝 接口
const editAdoptStatus = (params) => {
    return $axios({
        url: '/adopt',
        method: 'put',
        data: {...params}
    })
}
