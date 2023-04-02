importScripts(
  "https://www.gstatic.com/firebasejs/9.10.0/firebase-app-compat.js"
);
importScripts(
  "https://www.gstatic.com/firebasejs/9.10.0/firebase-messaging-compat.js"
);

const firebaseConfig = {
  apiKey: "AIzaSyBGRu3cl4De4p4rwMi0mStjt3oEpGQBEUY",
  authDomain: "fcm-test-db891.firebaseapp.com",
  projectId: "fcm-test-db891",
  storageBucket: "fcm-test-db891.appspot.com",
  messagingSenderId: "748407900028",
  appId: "1:748407900028:web:d7a3ceda63788bec2ec6f9",
  measurementId: "G-B8Z6R5M34R",
};
firebase.initializeApp(firebaseConfig);

const messaging = firebase.messaging();
messaging.onBackgroundMessage((payload) => {
  console.log(payload.notification.title);
});
