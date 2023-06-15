// 分页
function getAdminList (params) {
  return $axios({
    url: '/admin/page',
    method: 'get',
    params
  })
}

// 修改---启用禁用接口
export function enableOrDisableAdmin (params) {
  return $axios({
    url: '/admin',
    method: 'put',
    data: { ...params }
  })
}

// 新增---添加用户
function addAdmin (params) {
  return $axios({
    url: '/admin',
    method: 'post',
    data: { ...params }
  })
}

// 修改---添加用户
export function editAdmin (params) {
  return $axios({
    url: '/admin',
    method: 'put',
    data: { ...params }
  })
}

// 修改页面反查详情接口
function queryAdminById (id) {
  return $axios({
    url: `/admin/${id}`,
    method: 'get'
  })
}