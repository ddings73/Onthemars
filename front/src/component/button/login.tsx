import Swal from "sweetalert2";

export const handleLogin = () => {
  if (!sessionStorage.getItem('address') !== null) {
    Swal.fire('로그인 후 사용해주세요.', '', 'error');
  }
};
