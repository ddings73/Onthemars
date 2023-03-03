import axios from 'axios';
import Swal from "sweetalert2";

const baseURL = '주소';

// 일반 로그인 안한 유저
const baseAPI = (options?: any) => {
  return axios.create({ baseURL, ...options });
}

// alert를 통한 에러 핸들링
const commonUserErrorHandle = (error: any) => {
  const { status, message } = error.response.data!;
  if (status === 401) {
    Swal.fire(message, '', 'error')
  }
}

// 기본 api로 헤더에 token 을 넣음
const authAPI = (options?: any) => {
  const token = localStorage.getItem("token");
  const axiosInstance = axios.create({
    baseURL,
    headers: {
      Authorization: token,
    },
    ...options
  });

  axiosInstance.interceptors.response.use(
    function (response) {
      return response;
    },

    function (error) {
      // 에러시 위에 정의한 swal창 띄우기
      commonUserErrorHandle(error);
      return Promise.reject(error);
    }
  )


  return axiosInstance;
}

// form 데이터를 api를 통해 보낼때는 헤더에 Content-Type과 토큰을 넣어서 보냄 
const authFormAPI = (options?: any) => {
  const token = localStorage.getItem("token");

  const axiosInstance = axios.create({
    baseURL,
    headers: {
      'Content-Type': 'multipart/form-data;',
      Authorization: token,
    },
    ...options
  });

  axiosInstance.interceptors.response.use(
    function (response) {
      return response;
    },

    function (error) {
      commonUserErrorHandle(error);
      return Promise.reject(error);
    }
  )


  return axiosInstance
}

export const authInstance = authAPI();
export const authFormInstance = authFormAPI();
export const baseInstance = baseAPI();