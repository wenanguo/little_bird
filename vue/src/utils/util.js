export function timeFix () {
  const time = new Date()
  const hour = time.getHours()
  return hour < 9 ? '早上好' : hour <= 11 ? '上午好' : hour <= 13 ? '中午好' : hour < 20 ? '下午好' : '晚上好'
}

export function convertDateFromString (dateString) {
  if (dateString) {
    var st = dateString
    var a = st.split(' ')
    var b = a[0].split('-')
    var c = a[1].split(':')
    var date = new Date(b[0], b[1], b[2], c[0], c[1], c[2])
    return date
  }
}

export function getSocialDateDisplay (dateString) {
  if (dateString === '') return ''
  const time = new Date(dateString)
  const currtime = new Date()
  var cz = parseInt((currtime - time) / (1000 * 60))
    if (cz < 30) {
      return '刚刚'
    } else if (cz < 60) {
        return parseInt(cz) + '分钟前'
    } else if (cz < (60 * 24)) {
        return parseInt(cz / 60) + '小时前'
    } else if (cz > (59 * 24) && cz < (60 * 24 * 2)) {
        return '昨天'
    } else if (cz > (60 * 24 * 2) && cz < (60 * 24 * 365)) {
      return time.getMonth() + '月' + time.getDate() + '日'
    } else if (cz > (60 * 24 * 365) && cz < (60 * 24 * 365 * 2)) {
        return '去年'
    } else if (cz > (60 * 24 * 365 * 2)) {
        return parseInt(cz / (60 * 24 * 365)) + '年前'
    } else {
        return time.getMonth() + '月' + time.getDate() + '日'
    }
}

export function welcome () {
  const arr = ['休息一会儿吧', '准备吃什么呢?', '要不要打一把 DOTA', '我猜你可能累了']
  const index = Math.floor(Math.random() * arr.length)
  return arr[index]
}

export function fontNumber (data, dlength) {
  const length = data.length
  if (length > dlength) {
    var str = ''
    str = data.substring(0, dlength) + '...'
    return str
  } else {
    return data
  }
}

/**
 * 从列表数据获取指定属性的值
 */
export function listGetVal (list, data, kid, kname) {
    for (var i = 0; i < list.length; i++) {
        if (list[i][kid] === data) {
          return list[i][kname]
        }
    }
  // Object.keys(data).forEach(key => {
  //   console.log(key)
  // })
}

export function getFileName (o) {
  var pos = o.lastIndexOf('/')
  return o.substring(pos + 1)
}

/**
 * 触发 window.resize
 */
export function triggerWindowResizeEvent () {
  const event = document.createEvent('HTMLEvents')
  event.initEvent('resize', true, true)
  event.eventType = 'message'
  window.dispatchEvent(event)
}

export function handleScrollHeader (callback) {
  let timer = 0

  let beforeScrollTop = window.pageYOffset
  callback = callback || function () {}
  window.addEventListener(
    'scroll',
    event => {
      clearTimeout(timer)
      timer = setTimeout(() => {
        let direction = 'up'
        const afterScrollTop = window.pageYOffset
        const delta = afterScrollTop - beforeScrollTop
        if (delta === 0) {
          return false
        }
        direction = delta > 0 ? 'down' : 'up'
        callback(direction)
        beforeScrollTop = afterScrollTop
      }, 50)
    },
    false
  )
}

export function isIE () {
  const bw = window.navigator.userAgent
  const compare = (s) => bw.indexOf(s) >= 0
  const ie11 = (() => 'ActiveXObject' in window)()
  return compare('MSIE') || ie11
}

/**
 * Remove loading animate
 * @param id parent element id or class
 * @param timeout
 */
export function removeLoadingAnimate (id = '', timeout = 1500) {
  if (id === '') {
    return
  }
  setTimeout(() => {
    document.body.removeChild(document.getElementById(id))
  }, timeout)
}
