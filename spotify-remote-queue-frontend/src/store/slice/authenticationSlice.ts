import { createSlice } from '@reduxjs/toolkit';
import type { PayloadAction } from '@reduxjs/toolkit';

export interface AuthenticationState {
	jwt: string;
	isAuthenticated: boolean;
}

const initialState: AuthenticationState = {
	jwt: '',
	isAuthenticated: false,
};

export const authenticationSlice = createSlice({
	name: 'authentication',
	initialState,
	reducers: {
		authenticate: (state) => {
			state.isAuthenticated = true;
		},
		revokeAuthentication: (state) => {
			state.isAuthenticated = false;
		},
		saveJwt: (state, action: PayloadAction<string>) => {
			state.jwt = action.payload;
		},
	},
});

export const { authenticate, revokeAuthentication, saveJwt } =
	authenticationSlice.actions;

export default authenticationSlice.reducer;
