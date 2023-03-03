import { baseInstance } from "../utils/Instance";

// 유저 정보 받아오기
export async function GetUserInfo() {
  const token = localStorage.getItem('token')
  return baseInstance
    .get(`/auth/users`,
      {
        headers: { Authorization: token }
      }
    )
    .then((res) => {
      return res.data
    })
    .catch((err) => console.error(err, 'GetUserInfo 에러야'));
}
