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

/**
 * 格式化日期
 * @param {} number 要格式化的数字
 * @param {*} decimals 保留几位小数
 * @param {*} dec_point 小数点符号
 * @param {*} thousandsSep 千分位符号
 * @param {*} roundtag 舍入参数，默认 "ceil" 向上取,"floor"向下取,"round" 四舍五入
 */
export function numberFormat (number, decimals, decPoint, thousandsSep, roundtag) {
  /*
  * 参数说明：
  * number：要格式化的数字
  * decimals：保留几位小数
  * dec_point：小数点符号
  * thousandsSep：千分位符号
  * roundtag:舍入参数，默认 "ceil" 向上取,"floor"向下取,"round" 四舍五入
  * */
// console.log(numberFormat(2, 2, ".", ","))//"2.00"
// console.log(numberFormat(3.7, 2, ".", ","))//"3.70"
// console.log(numberFormat(3, 0, ".", ",")) //"3"
// console.log(numberFormat(9.0312, 2, ".", ","))//"9.03"
// console.log(numberFormat(9.00, 2, ".", ","))//"9.00"
// console.log(numberFormat(39.715001, 2, ".", ",", "floor")) //"39.71"
// console.log(numberFormat(9.7, 2, ".", ","))//"9.70"
// console.log(numberFormat(39.7, 2, ".", ","))//"39.70"
// console.log(numberFormat(9.70001, 2, ".", ","))//"9.71"
// console.log(numberFormat(39.70001, 2, ".", ","))//"39.71"
// console.log(numberFormat(9996.03, 2, ".", ","))//"9996.03"
// console.log(numberFormat(1.797, 3, ".", ",", "floor"))//"1.797"

  number = (number + '').replace(/[^0-9+-Ee.]/g, '')
  roundtag = roundtag || 'ceil' // "ceil","floor","round"
  var n = !isFinite(+number) ? 0 : +number
  var prec = !isFinite(+decimals) ? 0 : Math.abs(decimals)
  var sep = (typeof thousandsSep === 'undefined') ? ',' : thousandsSep
  var dec = (typeof decPoint === 'undefined') ? '.' : decPoint
  var s = ''
  var toFixedFix = function (n, prec) {
  var k = Math.pow(10, prec)
  console.log()

  return '' + parseFloat(Math[roundtag](parseFloat((n * k).toFixed(prec * 2))).toFixed(prec * 2)) / k
  }
  s = (prec ? toFixedFix(n, prec) : '' + Math.round(n)).split('.')
  var re = /(-?\d+)(\d{3})/
  while (re.test(s[0])) {
  s[0] = s[0].replace(re, '$1' + sep + '$2')
  }

  if ((s[1] || '').length < prec) {
  s[1] = s[1] || ''
  s[1] += new Array(prec - s[1].length + 1).join('0')
  }
  return s.join(dec)
  }

export function getSocialDateDisplay (dateString) {
  if (dateString === '') return ''
  const time = new Date(dateString)
  const currtime = new Date()
  var cz = parseInt((currtime - time) / (1000 * 60))
    if (cz < 0) {
      return time.getMonth() + '月' + time.getDate() + '日'
    } else if (cz < 30) {
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
