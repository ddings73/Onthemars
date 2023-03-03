import { createSlice } from '@reduxjs/toolkit';
import { User } from '../type/User';

interface init {
  // store/type에서 정의한 User type 들고오기
  user: User,
  // 추가되는 함수가 있다면 아래에 정의
  isGettingUser: boolean,
}

// init 에 대한 초기값 정의
const initialState: init = {
  user: {
    userId: 0,
    userName: '',
    userPicture: '',
    isAgree: false,
    userState: 0,
    userTokenId: '',
  },
  isGettingUser: false,
}

const userSlice = createSlice({
  name: 'user',
  initialState,
  reducers: {
    loadInfo(state, action) {
      state.user.userId = action.payload.id;
      state.user.userName = action.payload.nickName;
      state.user.userPicture = action.payload.profileImg;
      state.user.isAgree = action.payload.isAgree;
    },
  },
})

export const userActions = userSlice.actions;

export default userSlice;