import { initializeApp } from "firebase/app";
import { getMessaging, getToken, onMessage } from "firebase/messaging";

const config = {
  //프로젝트 설정 > 일반 > 하단의 내앱에서 확인
  apiKey: "AIzaSyBGRu3cl4De4p4rwMi0mStjt3oEpGQBEUY",
  authDomain: "fcm-test-db891.firebaseapp.com",
  projectId: "fcm-test-db891",
  storageBucket: "fcm-test-db891.appspot.com",
  messagingSenderId: "748407900028",
  appId: "1:748407900028:web:d7a3ceda63788bec2ec6f9",
  measurementId: "G-B8Z6R5M34R",
};

const app = initializeApp(config);
const messaging = getMessaging();

//토큰값 얻기
getToken(messaging, {
  vapidKey:
    "BLGuMX0ZNzOPYRGWNNenk3lHkvY525DAHh2Z9A4jD8uAhNgD8wL51iEV1Ip4lBPX8DiluNOMWZu0CPtpLbOYUDw",
})
  .then((currentToken) => {
    if (currentToken) {
      sessionStorage.setItem("fcmToken",currentToken);
    } 
  })

//포그라운드 메시지 수신
// export let received = false;
// onMessage(messaging, (payload) => {
//   received = true;
//   console.log("Message received. ", payload);
//   // ...
// });
