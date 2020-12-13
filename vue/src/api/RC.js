const statusMap = {
  0: {
      status: 'default',
      text: '关闭'
  },
  1: {
      status: 'processing',
      text: '正常'
  },
  100: {
      status: 'success',
      text: '正常'
  },
  101: {
      status: 'error',
      text: '禁用'
  },
  201: {
      status: 'success',
      text: '未支付'
  },
  202: {
      status: 'success',
      text: '已支付'
  }
}

const recommendMap = {
    1: {
        status: 'default',
        text: '推荐'
    },
    2: {
        status: 'processing',
        text: '不推荐'
    }
  }

export { statusMap, recommendMap }
