import { configureStore } from '@reduxjs/toolkit';
import authenticationReducer from './slice/authenticationSlice';
import roomInformationReducer from './slice/roomInformationSlice';

export const store = configureStore({
	reducer: {
		authentication: authenticationReducer,
		roomInformation: roomInformationReducer,
	},
});

export type RootState = ReturnType<typeof store.getState>;

export type AppDispatch = typeof store.dispatch;
