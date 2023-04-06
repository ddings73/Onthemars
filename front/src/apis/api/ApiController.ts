import axios, { AxiosInstance } from 'axios';
import { useNavigate } from 'react-router';

export const api: AxiosInstance = axios.create({
  baseURL: 'https://j8e207.p.ssafy.io/api/v1',
});

api.interceptors.response.use(
  function (response) {
    return response;
  },
  async function (err) {
    const navigate = useNavigate();
    const logout = () => {
      const refreshToken = sessionStorage.getItem('refreshToken');
      api.delete('/auth/login',{headers:{refreshToken}}).then(() => {
        sessionStorage.removeItem('address');
        sessionStorage.removeItem('accessToken');
        sessionStorage.removeItem('refreshToken');
        sessionStorage.removeItem('received');
        navigate('/');
      });
    };
    if (err.response && err.response.code === 401) {
      const accessToken = sessionStorage.getItem('accessToken');
      const refreshToken = sessionStorage.getItem('refreshToken');
      api
        .post(
          '/auth/token',
          {},
          {
            headers: { accessToken, refreshToken },
          },
        )
        .then((res: any) => {
          sessionStorage.setItem('accessToken', res.headers.get('accessToken'));
          sessionStorage.setItem('refreshToken', res.headers.get('refreshToken'));
        })
        .catch(logout);
    }
  },
);
