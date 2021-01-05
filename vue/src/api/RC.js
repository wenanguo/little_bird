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
      status: 'default',
      text: '未支付'
  },
  203: {
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

const adLocationMap = {
    1: {
        status: 'default',
        text: '首页广告'
    },
    2: {
        status: 'default',
        text: '启动广告'
    }
  }
const adTypeMap = {
    1: {
        status: 'default',
        text: '外部链接'
    },
    2: {
        status: 'default',
        text: '作者推荐'
    },
    3: {
        status: 'default',
        text: '栏目推荐'
    }
  }

export { statusMap, recommendMap, adLocationMap, adTypeMap }
