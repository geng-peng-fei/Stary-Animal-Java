// 查询列表页接口
const getVolunteerPage = (params) => {
    return $axios({
        url: '/volunteer/page',
        method: 'get',
        params
    })
}

// 同意，拒绝 接口
const editVolunteerStatus = (params) => {
    return $axios({
        url: '/volunteer',
        method: 'put',
        data: {...params}
    })
}
