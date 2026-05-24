export function listTreeFileType() {
  return Promise.resolve({ code: 200, data: [] })
}

export function countLeafFileType() {
  return Promise.resolve({ code: 200, data: 0 })
}
