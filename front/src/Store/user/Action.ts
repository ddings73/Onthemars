import { GetUserInfo } from "../../apis/api/User";
import { userActions } from "./Slice";

// 유저 정보 가져오기 redux 사용 예시
export const fetchUserInfo = () => {
  return async (dispatch: (
    arg0: { payload: any; type: "user/loadInfo"; },
  ) => void) => {
    try {
      // api를 호출해서 받아오는 값을 UserInfo에 저장
      const UserInfo = await GetUserInfo();
      // Slice에 초기값으로 정의된 loadInfo에 받아온 값 넣기
      dispatch(userActions.loadInfo(UserInfo));
      return;
    } catch (error) {
      console.log("fetchUserInfo 받아오는데 실패함", error)
    }
  };
};
