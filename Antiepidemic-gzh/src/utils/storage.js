/**
 * C
 * @param  {键} key
 * @param  {值} value
 */
export const setItem = (key, value) => {
  if (typeof value === 'object') {
    value = JSON.stringify(value)
  }

  window.localStorage.setItem(key, value)
}

/**
 * R
 * @param  { key } 
 */
export const getItem = (key) => {

  const data = window.localStorage.getItem(key)
  try {
    // 如果不是json字符串就会报错，我们就能捕获错误
    return JSON.parse(data)
  } catch (error) {
    return data
  }
}

/**
 * D
 * @param  { key } =>{constdata=window.localStorage.removeItem(key
 */
export const removeItem = (key) => {
  const data = window.localStorage.removeItem(key)
}

