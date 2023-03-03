import { configureStore } from "@reduxjs/toolkit";
import { shallowEqual, TypedUseSelectorHook, useDispatch, useSelector } from "react-redux";

import userSlice from "./user/Slice";

export const store = configureStore({
  reducer: {
    user: userSlice.reducer,
  }
});

export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch

export const useAppDispatch: () => AppDispatch = useDispatch
export const useAppSelector: TypedUseSelectorHook<RootState> = useSelector

export default function useSelectorTyped<T>(fn: (state: RootState) => T): T {
  return useSelector(fn, shallowEqual);
}