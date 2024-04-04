import instance from './index.js'

// 내 정보 가져오는 함수
function getMyInfo(params) {
  return new Promise((resolve, reject) => {
    instance
      .get(`api/members/me`)
      .then((res) => {
        if (res.data.status == 200) {
          const userInfo = res.data.data
          authStore.userName = userInfo.name
          authStore.userImgUrl = userInfo.image_url
          authStore.userEmail = userInfo.email
        }
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
    instance
      .put(`api/members/me`)
      .then((response) => {
        resolve(response.data)
      })
      .catch((error) => {
        reject(error)
      })
  })
}
// 회원탈퇴
function withdrawal(params) {
  return new Promise((resolve, reject) => {
    instance
      .delete(`api/members/me`)
      .then((response) => {
        resolve(response.data)
      })
      .catch((error) => {
        reject(error)
      })
  })
}
// 대시보드 정보 가져오는 함수
function getDashBoardInfo(params) {
  return new Promise((resolve, reject) => {
    instance
      .get(`api/members/me/dashboard`)
      .then((response) => {
        resolve(response.data)
      })
      .catch((error) => {
        reject(error)
      })
  })
}

// 내 주간 일정 가져오는 함수
function getWeeklySchedule(params) {
  return new Promise((resolve, reject) => {
    instance
      .get(`members/me/study-timeline`)
      .then((response) => {
        resolve(response.data)
      })
      .catch((error) => {
        reject(error)
      })
  })
}

// 내 스터디 목록 조회
function getStudyList(params) {
  return new Promise((resolve, reject) => {
    instance
      .get(`/members/me/studies`)
      .then((response) => {
        resolve(response.data)
      })
      .catch((error) => {
        reject(error)
      })
  })
}

// 내 노트 목록 조회
function getNoteList(params) {
  return new Promise((resolve, reject) => {
    instance
      .get(`/members/me/notes`)
      .then((response) => {
        resolve(response.data)
      })
      .catch((error) => {
        reject(error)
      })
  })
}

// 리마인드퀴즈 조회
function getQuizList(params) {
  return new Promise((resolve, reject) => {
    instance
      .get(`/members/me/remind-quiz`)
      .then((response) => {
        resolve(response.data)
      })
      .catch((error) => {
        reject(error)
      })
  })
}

// 내 스터디 미팅 일정
function getMeetingSchedule(params) {
  return new Promise((resolve, reject) => {
    instance
      .get(`/members/me/meetings`)
      .then((response) => {
        resolve(response.data)
      })
      .catch((error) => {
        reject(error)
      })
  })
}

export {
  getDashBoardInfo,
  getWeeklySchedule,
  withdrawal,
  editMyInfo,
  getMyInfo,
  getStudyList,
  getNoteList,
  getQuizList
}
