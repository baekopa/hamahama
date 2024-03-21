import { apiInstance } from './index.js'
const api = apiInstance()

// 내 정보 가져오는 함수
function getMyInfo(params) {
  return new Promise((resolve, reject) => {
    api
      .get(`api/members/me`)
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

// 내 정보 수정하는 함수
function editMyInfo(params) {
  return new Promise((resolve, reject) => {
    api
      .put(`api/members/me`)
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
// 회원탈퇴
function withdrawal(params) {
  return new Promise((resolve, reject) => {
    api
      .delete(`api/members/me`)
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
// 대시보드 정보 가져오는 함수
function getDashBoardInfo(params) {
  return new Promise((resolve, reject) => {
    api
      .get(`api/members/me/dashboard`)
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

// 내 주간 일정 가져오는 함수
function getWeeklySchedule(params) {
  return new Promise((resolve, reject) => {
    api
      .get(`members/me/study-timeline`)
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

// 내 스터디 목록 조회
function getStudyList(params) {
  return new Promise((resolve, reject) => {
    api
      .get(`/members/me/studies`)
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

export { getDashBoardInfo, getWeeklySchedule, withdrawal, editMyInfo, getMyInfo, getStudyList }
