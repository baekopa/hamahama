import instance from './index.js'
const api = instance()

function kakaoLogin(param) {
  return new Promise((resolve, reject) => {
    api
      .post(`api/auth/kakao?redirect_uri=${param}`)
      .then((response) => {
        // 여기서 필요한 처리를 수행합니다.
        resolve(response.data) // 예시: 성공 시 데이터를 resolve합니다.
      })
      .catch((error) => {
        // 에러 처리
        reject(error) // 에러를 reject합니다.
      })
  })
}

export { kakaoLogin }
