import axios from 'axios';

const baseURL = '주소';


const adminAuthAPI = (options?: any) => {
  const token = localStorage.getItem("adminToken");

  return axios.create({
    baseURL,
    headers: {
      Authorization: token,
    },
    ...options
  });
}

const adminAuthFormAPI = (options?: any) => {
  const token = localStorage.getItem("adminToken");

  return axios.create({
    baseURL,
    headers: {
      'Content-Type': 'multipart/form-data;',
      Authorization: token,
    },
    ...options
  });
}

const adminAuthApplicationFormAPI = (options?: any) => {
  const token = localStorage.getItem("adminToken");

  return axios.create({
    baseURL,
    headers: {
      'Content-Type': 'application/json',
      Authorization: token,
    },
    ...options
  });
}

export const adminAuthInstance = adminAuthAPI();
export const adminAuthFormInstance = adminAuthFormAPI();
export const adminAuthAppFormInstance = adminAuthApplicationFormAPI();
